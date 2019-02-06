package AVLTree;

public class BinarySearchTreeWithRotate<E extends Comparable<E>>
        extends BinarySearchTree<E> {

    // Methods

    /**
     * Method to perform a right rotation.
     *
     * @param root The root of the binary tree to be rotated
     * @return The new root of the rotated tree
     * root.right.right is raised one level,
     * root.right.left does not change levels,
     * root.left is lowered one level,
     * the new root is returned.
     */
    protected Node<E> rotateRight(Node<E> root) {
        System.out.println("Rotating node "+root.data+" to right");
        Node<E> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }

    protected Node<E> rotateLeft(Node<E> root){
        System.out.println("Rotating node "+root.data+" to left");
        Node<E> temp =root.right;
        root.right =temp.left;
        temp.left =root;
        return temp;
    }
}
