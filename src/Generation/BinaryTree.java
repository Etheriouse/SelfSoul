package Generation;

public class BinaryTree<T> {

    public static void main(String[] args) {
        BinaryTree<Integer> test = new BinaryTree<>(0, new BinaryTree<>(1, new BinaryTree<>(3, null, null), new BinaryTree<>(4, null, null)), new BinaryTree<>(2, new BinaryTree<>(5, null, null), new BinaryTree<>(6, null, null)));

        System.out.println(test.getLeave(1, 1));
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
    public T getLeave(int ... path) {
        if(path.length < 1) {
            return this.leave;
        } else {
            try {
                if(path[0] > 0) {
                    return this.right.getLeave(smallArray(path));
                } else {
                    return this.left.getLeave(smallArray(path));
                }
            } catch (Exception e) {
                System.out.println("to much instruction given " + e.getMessage());
                return this.leave;
            }
        }
    }

    private int[] smallArray(int t[]) {
        int tbis[] = new int[t.length-1];
        for(int i = 1; i<t.length; i++) {
            tbis[i-1] = t[i];
        }
        return tbis;
    }

    public int getHeight() {
        return this.height;
    }
}
