package hu.dfulop;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static PathCreator pathCreator = new PathCreator();
    private static TreeManager treeManager = new TreeManager();

    public static void main(String[] args) {
        List<String> readable = Arrays.asList("/var/lib/jenkins", "/var/lib", "/var", "/", "/bin/helper");
        List<String> writable = Arrays.asList("/var/lib/jenkins/log", "/bin/creator/log");

        List<Path> readablePaths = readable.stream().map(r -> pathCreator.createPath(r, Permission.READ)).collect(Collectors.toList());
        List<Path> writablePaths = writable.stream().map(r -> pathCreator.createPath(r, Permission.WRITE)).collect(Collectors.toList());

        TreeItem root = TreeItem.Builder.aTreeItem()
                .withName("/")
                .build();

        readablePaths.forEach(path -> treeManager.addPathToTree(root, path));
        writablePaths.forEach(path -> treeManager.addPathToTree(root, path));

        writablePaths.forEach(path -> {
            boolean reachable = treeManager.isDirectoryReachableThroughReadableDirectories(root, path);
            if (reachable) {
                treeManager.includePathInResult(root, path);
            }
        });
    }


}
