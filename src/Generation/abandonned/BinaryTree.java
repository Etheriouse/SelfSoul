package Generation.abandonned;

public class BinaryTree<T> {

    public static void main(String[] args) {
        BinaryTree<Integer> test = new BinaryTree<>(0,
                new BinaryTree<>(1, new BinaryTree<>(3, null, null), new BinaryTree<>(4, null, null)),
                new BinaryTree<>(2, new BinaryTree<>(5, null, null), new BinaryTree<>(6, null, null)));

        System.out.println(test.getLeave(true, true));

        System.out.println(test);
    }

    public T leave;
    public BinaryTree<T> right;
    public BinaryTree<T> left;

    private int height;

    /**
     *
     */
    public BinaryTree() {
        this.leave = null;
        this.left = null;
        this.right = null;
    }

    /**
     *
     * @param leave
     * @param left
     * @param right
     */
    public BinaryTree(T leave, BinaryTree<T> left, BinaryTree<T> right) {
        this.leave = leave;
        this.left = left;
        this.right = right;
    }

    /**
     *
     * @param leave
     * @param left
     */
    public BinaryTree(T leave, BinaryTree<T> left) {
        this.leave = leave;
        this.left = left;
        this.right = null;
    }

    /**
     *
     * @param leave
     */
    public BinaryTree(T leave) {
        this.leave = leave;
        this.right = null;
        this.left = null;
    }

    /**
     *
     * @param path
     * @return
     */
    public BinaryTree<T> getLeave(boolean... path) {
        if (path.length < 1) {
            return this;
        } else {
            try {
                if (path[path.length-1]) {
                    return this.left.getLeave(smallArray(path));
                } else {
                    return this.right.getLeave(smallArray(path));
                }
            } catch (Exception e) {
                System.out.println("to much instruction given " + e.getMessage());
                return this;
            }
        }
    }

    public String toString() {
        return printTree(this, "", false);
    }

    private String printTree(BinaryTree<T> root, String prefix, boolean isLeft) {
        if (root == null) {
            return "";
        }
        String txt = "";

        txt+=(prefix + (isLeft ? "|-- " : "\\-- ") + root.leave)+"\n";

        // Increase the indentation for the children
        String newPrefix = prefix + (isLeft ? "|   " : "    ");
        return txt+printTree(root.right, newPrefix, true)+printTree(root.left, newPrefix, false);
    }

    private boolean[] smallArray(boolean t[]) {
        boolean tbis[] = new boolean[t.length - 1];
        for (int i = 0; i < tbis.length; i++) {
            tbis[i] = t[i];
        }
        return tbis;
    }

    public int getHeight() {
        return this.height;
    }
}
