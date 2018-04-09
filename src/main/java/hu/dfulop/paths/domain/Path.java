package hu.dfulop.paths.domain;

import hu.dfulop.permission.Permission;

public class Path {
    private String name;
    private Permission permission;
    private Path subfolder;

    public String getName() {
        return name;
    }

    public Permission getPermission() {
        return permission;
    }

    public Path getSubfolder() {
        return subfolder;
    }

    public void setSubfolder(Path subfolder) {
        this.subfolder = subfolder;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public static final class Builder {
        private String name;
        private Permission permission;

        private Builder() {
        }

        public static Builder aPath() {
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

        public Path build() {
            Path path = new Path();
            path.permission = this.permission;
            path.name = this.name;
            return path;
        }
    }
}
