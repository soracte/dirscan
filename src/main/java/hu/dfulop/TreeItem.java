package hu.dfulop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreeItem {
    private String name;
    private Permission permission;
    private List<TreeItem> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<TreeItem> getChildren() {
        return children;
    }

    public void addPath(Path addedPath) {
        if (isLeaf(addedPath)) {
            this.setPermission(addedPath.getPermission());
            return;
        }

        TreeItem next;

        Optional<TreeItem> childCandidate = children.stream()
                .filter(c -> c.getName().equals(addedPath.getName())).findFirst();

        if (childCandidate.isPresent()) {
            next = childCandidate.get();
        } else {
            TreeItem child = Builder
                    .aTreeItem()
                    .withName(addedPath.getName())
                    .build();

            children.add(child);
            next = child;
        }

        next.addPath(addedPath.getSubfolder());
    }

    private boolean isLeaf(Path path) {
        return path.getSubfolder() == null;
    }


    public static final class Builder {
        private String name;
        private Permission permission;
        private List<TreeItem> children = new ArrayList<>();

        private Builder() {
        }

        public static Builder aTreeItem() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPermission(Permission permission) {
            this.permission = permission;
            return this;
        }

        public Builder withChildren(List<TreeItem> children) {
            this.children = children;
            return this;
        }

        public TreeItem build() {
            TreeItem treeItem = new TreeItem();
            treeItem.setName(name);
            treeItem.setPermission(permission);
            treeItem.children = this.children;
            return treeItem;
        }
    }
}
