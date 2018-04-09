package hu.dfulop;

public class PathCreator {

    private static final Path ROOT_PATH = Path.Builder.aPath()
            .withName("/")
            .withPermission(Permission.NONE)
            .build();

    public Path createPath(String path, Permission permission) {
        Path current = ROOT_PATH;

        for (String pathPart : path.split("/")) {
            Path subfolder = Path.Builder.aPath()
                    .withName(pathPart)
                    .build();

            current.setSubfolder(subfolder);
            current = subfolder;
        }

        current.setPermission(permission);
        return current;
    }
}
