package hu.dfulop.tree.control;

import hu.dfulop.paths.domain.Path;
import hu.dfulop.permission.Permission;
import hu.dfulop.tree.domain.TreeItem;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TreeManagerTest {

    private TreeManager treeManager = new TreeManager();

    @Test
    public void testAddPathToTree() {
        // prepare
        TreeItem root = TreeItem.Builder.aTreeItem()
                .withName("/")
                .withPermission(Permission.NONE)
                .build();

        Path rootpath1 = Path.Builder.aPath()
                .withName("/")
                .build();

        Path varpath = Path.Builder.aPath()
                .withName("var")
                .build();

        Path libpath = Path.Builder.aPath()
                .withName("lib")
                .withPermission(Permission.WRITE)
                .build();

        Path rootpath2 = Path.Builder.aPath()
                .withName("/")
                .build();

        Path logpath = Path.Builder.aPath()
                .withName("log")
                .withPermission(Permission.READ)
                .build();

        rootpath1.setSubfolder(varpath);
        varpath.setSubfolder(libpath);

        rootpath2.setSubfolder(logpath);


        // call
        treeManager.addPathToTree(root, rootpath1);
        treeManager.addPathToTree(root, rootpath2);

        // verify
        verifyTreeBuildingResult(root);
    }


    @Test
    public void testIsDirectoryReachableThroughReadableDirectories_negative() {
        // prepare
        TreeItem root = TreeItem.Builder.aTreeItem()
                .withName("/")
                .withPermission(Permission.NONE)
                .build();

        Path rootpath = Path.Builder.aPath()
                .withName("/")
                .build();

        Path subpath = Path.Builder.aPath()
                .withName("sub")
                .build();

        Path varpath = Path.Builder.aPath()
                .withName("var")
                .build();

        Path libpath = Path.Builder.aPath()
                .withName("lib")
                .withPermission(Permission.WRITE)
                .build();

        rootpath.setSubfolder(subpath);
        subpath.setSubfolder(varpath);
        varpath.setSubfolder(libpath);

        treeManager.addPathToTree(root, rootpath);

        // call
        boolean actual = treeManager.isDirectoryReachableThroughReadableDirectories(root, rootpath);

        // verify
        assertThat(actual, equalTo(false));
    }

    @Test
    public void testIsDirectoryReachableThroughReadableDirectories_positive() {
        // prepare
        TreeItem root = TreeItem.Builder.aTreeItem()
                .withName("/")
                .withPermission(Permission.NONE)
                .build();

        Path rootpath = Path.Builder.aPath()
                .withPermission(Permission.READ)
                .withName("/")
                .build();

        treeManager.addPathToTree(root, rootpath);

        Path subpath = Path.Builder.aPath()
                .withName("sub")
                .withPermission(Permission.WRITE)
                .build();

        rootpath.setSubfolder(subpath);

        treeManager.addPathToTree(root, rootpath);

        // call
        boolean actual = treeManager.isDirectoryReachableThroughReadableDirectories(root, rootpath);

        // verify
        assertThat(actual, equalTo(true));
    }

    @Test
    public void testIncludePathInResult() {
        // prepare
        TreeItem root = TreeItem.Builder.aTreeItem()
                .withName("/")
                .withPermission(Permission.NONE)
                .build();

        Path rootpath1 = Path.Builder.aPath()
                .withName("/")
                .build();

        Path varpath = Path.Builder.aPath()
                .withName("var")
                .build();

        Path libpath = Path.Builder.aPath()
                .withName("lib")
                .withPermission(Permission.WRITE)
                .build();

        Path rootpath2 = Path.Builder.aPath()
                .withName("/")
                .build();

        Path logpath = Path.Builder.aPath()
                .withName("log")
                .withPermission(Permission.READ)
                .build();

        rootpath1.setSubfolder(varpath);
        varpath.setSubfolder(libpath);

        rootpath2.setSubfolder(logpath);

        treeManager.addPathToTree(root, rootpath1);
        treeManager.addPathToTree(root, rootpath2);

        // call
        treeManager.includePathInResult(root, rootpath1);

        // verify
        assertThat(root.isIncludeInResult(), equalTo(true));

        TreeItem varchild = getChildByName(root, "var");
        assertThat(varchild.isIncludeInResult(), equalTo(true));

        TreeItem libchild = getChildByName(varchild, "lib");
        assertThat(libchild.isIncludeInResult(), equalTo(true));

        TreeItem logchild = getChildByName(root, "log");
        assertThat(logchild.isIncludeInResult(), equalTo(false));
    }

    private void verifyTreeBuildingResult(TreeItem root) {
        assertThat(root.isAtLeastReadable(), equalTo(false));
        assertThat(root.getChildren().size(), equalTo(2));

        TreeItem varitem = getChildByName(root, "var");
        assertThat(varitem.isAtLeastReadable(), equalTo(false));
        assertThat(varitem.getChildren().size(),  equalTo(1));

        TreeItem libitem = getChildByName(varitem, "lib");
        assertThat(libitem.isAtLeastReadable(), equalTo(true));
        assertThat(libitem.getChildren().isEmpty(), equalTo(true));

        TreeItem logitem = getChildByName(root, "log");
        assertThat(logitem.isAtLeastReadable(), equalTo(true));
        assertThat(logitem.getChildren().isEmpty(), equalTo(true));
    }

    private TreeItem getChildByName(TreeItem root, String name) {
        return root.getChildren().stream().filter(child -> child.getName().equals(name)).findFirst().get();
    }

}