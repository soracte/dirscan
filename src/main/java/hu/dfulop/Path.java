package hu.dfulop;

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
        private Path subfolder;

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

        public Builder withSubfolder(Path subfolder) {
            this.subfolder = subfolder;
            return this;
        }

        public Path build() {
            Path path = new Path();
            path.permission = this.permission;
            path.name = this.name;
            path.subfolder = this.subfolder;
            return path;
        }
    }
}
