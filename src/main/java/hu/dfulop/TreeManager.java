package hu.dfulop;

import java.util.Optional;

public class TreeManager {

    public void addPathToTree(TreeItem root, Path addedPath) {
        if (isLeaf(addedPath)) {
            root.setPermission(addedPath.getPermission());
            return;
        }

        TreeItem next;

        Optional<TreeItem> childCandidate = findChildWithName(root, addedPath.getSubfolder().getName());

        if (childCandidate.isPresent()) {
            next = childCandidate.get();
        } else {
            TreeItem child = TreeItem.Builder
                    .aTreeItem()
                    .withName(addedPath.getSubfolder().getName())
                    .withPermission(Permission.NONE)
                    .build();

            root.getChildren().add(child);
            next = child;
        }

        addPathToTree(next, addedPath.getSubfolder());
    }

    public boolean isDirectoryReachableThroughReadableDirectories(TreeItem root, Path path) {
        if (isLeaf(path)) {
            return true;
        }

        if (root.isAtLeastReadable()) {
            Optional<TreeItem> child = findChildWithName(root, path.getSubfolder().getName());
            if (!child.isPresent()) {
                throw new RuntimeException("The subfolder " + path.getSubfolder().getName() + " was not found in the tree.");
            }


            return isDirectoryReachableThroughReadableDirectories(child.get(), path.getSubfolder());
        }

        return false;
    }

    private boolean isLeaf(Path path) {
        return path.getSubfolder() == null;
    }

    public void includePathInResult(TreeItem root, Path path) {
        root.setIncludeInResult(true);

        if (isLeaf(path)) {
            return;
        }

        Optional<TreeItem> child = findChildWithName(root, path.getSubfolder().getName());
        if (!child.isPresent()) {
            throw new RuntimeException("The subfolder " + path.getSubfolder().getName() + " was not found in the tree.");
        }

        includePathInResult(child.get(), path.getSubfolder());
    }

    private Optional<TreeItem> findChildWithName(TreeItem root, String name) {
        return root.getChildren().stream().filter(item -> item.getName().equals(name)).findFirst();
    }
}
