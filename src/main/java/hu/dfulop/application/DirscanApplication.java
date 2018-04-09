package hu.dfulop.application;

import hu.dfulop.paths.domain.Path;
import hu.dfulop.paths.control.PathCreator;
import hu.dfulop.permission.Permission;
import hu.dfulop.tree.domain.TreeItem;
import hu.dfulop.tree.control.TreeManager;
import hu.dfulop.tree.control.TreePrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirscanApplication {

    private PathCreator pathCreator = new PathCreator();
    private TreeManager treeManager = new TreeManager();
    private TreePrinter treePrinter = new TreePrinter();

    public void execute(String[] args) throws IOException {
        List<String> readable = new ArrayList<>();
        List<String> writable = new ArrayList<>();

        fillListsWithDirectoriesFromInputFiles(args, readable, writable);

        List<Path> readablePaths = readable.stream().map(r -> pathCreator.createPath(r, Permission.READ)).collect(Collectors.toList());
        List<Path> writablePaths = writable.stream().map(r -> pathCreator.createPath(r, Permission.WRITE)).collect(Collectors.toList());

        TreeItem root = buildTree(readablePaths, writablePaths);

        markPathsToIncludeInResult(writablePaths, root);

        treePrinter.printTree(root);
    }

    private void markPathsToIncludeInResult(List<Path> writablePaths, TreeItem root) {
        writablePaths.forEach(path -> {
            boolean reachable = treeManager.isDirectoryReachableThroughReadableDirectories(root, path);
            if (reachable) {
                treeManager.includePathInResult(root, path);
            }
        });
    }

    private TreeItem buildTree(List<Path> readablePaths, List<Path> writablePaths) {
        TreeItem root = TreeItem.Builder.aTreeItem()
                .withName("/")
                .build();

        readablePaths.forEach(path -> treeManager.addPathToTree(root, path));
        writablePaths.forEach(path -> treeManager.addPathToTree(root, path));
        return root;
    }

    private void fillListsWithDirectoriesFromInputFiles(String[] args, List<String> readable, List<String> writable) throws IOException {
        String readableDirectoriesFilename = args[0];
        try (Stream<String> stream = Files.lines(Paths.get(readableDirectoriesFilename))) {
            stream.forEach(readable::add);
        }

        String writableDirectoriesFilename = args[1];
        try (Stream<String> stream = Files.lines(Paths.get(writableDirectoriesFilename))) {
            stream.forEach(writable::add);
        }
    }
}
