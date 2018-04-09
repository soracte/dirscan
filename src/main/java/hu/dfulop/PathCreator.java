package hu.dfulop;

import static hu.dfulop.Permission.NONE;

public class PathCreator {

    public Path createPath(String path, Permission permission) {
        String pathWithoutRoot = path.substring(1);

        Path root = createRootPath();
        Path current = root;

        if (!pathWithoutRoot.isEmpty()) {
            for (String pathPart : pathWithoutRoot.split("/")) {
                Path subfolder = Path.Builder.aPath()
                        .withName(pathPart)
                        .withPermission(NONE)
                        .build();

                current.setSubfolder(subfolder);
                current = subfolder;
            }
        }

        current.setPermission(permission);
        return root;
    }

    private Path createRootPath() {
        return Path.Builder.aPath()
                .withName("/")
                .withPermission(NONE)
                .build();
    }
}
