package hu.dfulop.tree.domain;

import hu.dfulop.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {

    private String name;
    private Permission permission;
    private List<TreeItem> children = new ArrayList<>();
    private boolean includeInResult;

    public String getName() {
        return name;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<TreeItem> getChildren() {
        return children;
    }

    public boolean isIncludeInResult() {
        return includeInResult;
    }

    public void setIncludeInResult(boolean includeInResult) {
        this.includeInResult = includeInResult;
    }

    public boolean isAtLeastReadable() {
        return permission == Permission.READ || permission == Permission.WRITE;
    }

    public static final class Builder {
        private String name;
        private Permission permission;

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

        public TreeItem build() {
            TreeItem treeItem = new TreeItem();
            treeItem.name = name;
            treeItem.setPermission(permission);
            return treeItem;
        }
    }
}
