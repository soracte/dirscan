package hu.dfulop;

public class TreePrinter {

    public void printTree(TreeItem root) {
        printTree(root, 0);
    }

    private void printTree(TreeItem root, int level) {
        if (!root.isIncludeInResult()) {
            return;
        }

        for (int i = 0; i <= level * 2; i++) {
            System.out.print(" ");
        }

        String suffix = root.getName().equals("/") ? "" : "/";
        System.out.println(root.getName() + suffix);
        root.getChildren().forEach(child -> printTree(child, level + 1));
    }
}
