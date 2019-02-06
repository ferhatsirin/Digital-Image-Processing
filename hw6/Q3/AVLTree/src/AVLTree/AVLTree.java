package AVLTree;

import java.security.InvalidParameterException;

public class AVLTree<E extends Comparable<E>>
        extends BinarySearchTreeWithRotate<E> {


    /** Class to represent an AVL Node. It extends the
     * BinaryTree.Node by adding the balance field.
     */
    private static class AVLNode<E> extends Node<E> {

        /** Constant to indicate left-heavy */
        public static final int LEFT_HEAVY = -1;
        /** Constant to indicate balanced */
        public static final int BALANCED = 0;
        /** Constant to indicate right-heavy */
        public static final int RIGHT_HEAVY = 1;
        /** balance is right subtree height - left subtree height */
        private int balance;

        // Methods
        /**
         * Construct a node with the given item as the data field.
         * @param item The data field
         */
        public AVLNode(E item) {
            super(item);
            balance = BALANCED;
        }

        public AVLNode(E item ,Node<E> left,Node<E> right){
            super(item,left,right);
            balance =0;
        }

        /**
         * Return a string representation of this object.
         * The balance value is appended to the contents.
         * @return String representation of this object
         */
        @Override
        public String toString() {
            return balance + ": " + super.toString();
        }
    }

    // Data Fields
    /** Flag to indicate that height of tree has increased. */
    private boolean increase;
    private boolean isAVL;
    private boolean decrease;

    public AVLTree(BinaryTree<E> tree){
        root =createTree(tree.root);
        findBalance((AVLNode<E>)root);
        isAVL =true;
        isAVL((AVLNode<E>)root);
        if(!isAVL){
            System.out.println("This binary tree is not an avl tree ");
            throw new InvalidParameterException("not avl");
        }
        System.out.println("Avl tree created from binary tree ");
    }

    private void isAVL(AVLNode<E> node){
        if(node !=null) {
            if(node.balance <=-2 || node.balance >=2){
                isAVL =false;
            }
            findBalance((AVLNode<E>)node.right);
            findBalance((AVLNode<E>)node.left);
        }
    }



    private AVLNode<E> createTree(Node<E> node){

        AVLNode<E> left =null;
        AVLNode<E> right =null;
        if(node.left !=null){
            left =createTree(node.left);
        }
        if(node.right !=null){
            right =createTree(node.right);
        }

        return new AVLNode<E>(node.data,left,right);
    }

    public AVLTree(){}

    /**
     * Starter method delete.
     * @param target The object to be deleted
     * @return The object deleted from the tree
     *         or null if the object was not in the tree
     * @throws ClassCastException if target does not implement
     *         Comparable
     */
    public boolean remove(E target) {
        decrease = false;
        System.out.println("Removing node :"+target);
        root = remove((AVLNode<E>) root, target);
        findBalance((AVLNode<E>)root);
        if(root !=null && ((AVLNode<E>) root).balance < AVLNode.LEFT_HEAVY){
            root =rebalanceLeft((AVLNode<E>)root);
        }
        else if(root !=null && ((AVLNode<E>) root).balance > AVLNode.RIGHT_HEAVY){
            root =rebalanceRight((AVLNode<E>)root);
        }
        findBalance((AVLNode<E>)root);
        return deleteReturn != null;
    }

    /**
     * Recursive delete method.
     * @post The item is not in the tree;
     *       deleteReturn is equal to the deleted item
     *       as it was stored in the tree or null
     *       if the item was not found.
     * @param localRoot The root of the current subtree
     * @param item The item to be deleted
     * @return The modified local root that does not contain
     *         the item
     */
    private AVLNode<E> remove(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree.
            deleteReturn = null;
            return localRoot;
        }

        // Search for item to delete.
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data.
            localRoot.left = remove((AVLNode<E>)localRoot.left, item);
            if (decrease) {
                findBalance(localRoot);
                if(localRoot.balance != AVLNode.BALANCED){
                    decrease =false;
                }
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    localRoot =rebalanceRight(localRoot);
                    findBalance(localRoot);
                    return localRoot;
                }
            }
            return localRoot;
        } else if (compResult > 0) {
            // item is larger than localRoot.data.
            localRoot.right = remove((AVLNode<E>)localRoot.right, item);
            if (decrease) {
                findBalance(localRoot);
                if(localRoot.balance != AVLNode.BALANCED){
                    decrease =false;
                }
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    localRoot =rebalanceLeft(localRoot);
                    findBalance(localRoot);
                    return localRoot;
                }
            }

            return localRoot;
        } else {
            // item is at local root.
            decrease =true;
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {
                // If there is no left child, return right child
                // which can also be null.
                return (AVLNode<E>)localRoot.right;
            } else if (localRoot.right == null) {
                // If there is no right child, return left child.
                return (AVLNode<E>)localRoot.left;
            } else {
                // Node being deleted has 2 children, replace the data
                // with inorder predecessor.
                if (localRoot.left.right == null) {
                    // The left child has no right child.
                    // Replace the data with the data in the
                    // left child.
                    localRoot.data = localRoot.left.data;
                    // Replace the left child with its left child.
                    localRoot.left = localRoot.left.left;
                } else {
                    // Search for the inorder predecessor (ip) and
                    // replace deleted node's data with ip.
                    localRoot.data =findLargestChild((AVLNode<E>)localRoot.left);
                }
                findBalance(localRoot);
                if(localRoot.left !=null && ((AVLNode<E>)localRoot.left).balance < AVLNode.LEFT_HEAVY)
                    localRoot.left =rebalanceLeft((AVLNode<E>)localRoot.left);

                return localRoot;
            }
        }
    }

    private E findLargestChild(AVLNode<E> parent) {
        // If the right child has no right child, it is
        // the inorder predecessor.
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        } else {
            return findLargestChild((AVLNode<E>)parent.right);
        }
    }

    private void findBalance(AVLNode<E> node){
        if(node !=null) {
            int right = findHeight((AVLNode<E>) node.right);
            int left = findHeight((AVLNode<E>) node.left);
            node.balance = right - left;
            findBalance((AVLNode<E>)node.right);
            findBalance((AVLNode<E>)node.left);
        }
    }

    private int findHeight(AVLNode<E> node){
        if(node != null) {
            int right = findHeight((AVLNode<E>)node.right);
            int left = findHeight((AVLNode<E>)node.left);
            if (right < left) {
                return left+1;
            } else {
                return right+1;
            }
        }
        return 0;
    }

    // Methods
    /**
     * add starter method.
     * @param item The item being inserted.
     * @return true if the object is inserted; false
     *         if the object already exists in the tree
     * @throws ClassCastException if item is not Comparable
     */
    @Override
    public boolean add(E item) {
        increase = false;
        System.out.println("Adding "+item+" to avl tree ");
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    /**
     * Recursive add method. Inserts the given object into the tree.
     * @post addReturn is set true if the item is inserted,
     *       false if the item is already in the tree.
     * @param localRoot The local root of the subtree
     * @param item The object to be inserted
     * @return The new local root of the subtree with the item
     *         inserted
     */
    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }

        if (item.compareTo(localRoot.data) == 0) {
            // Item is already in the tree.
            increase = false;
            addReturn = false;
            return localRoot;
        }
        else if (item.compareTo(localRoot.data) < 0) {
            // item < data
            localRoot.left = add((AVLNode<E>) localRoot.left, item);

            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot; // Rebalance not needed.
        }
        else { // item > data

            localRoot.right =add((AVLNode<E>) localRoot.right, item);
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot);
                }
            }
            return localRoot; // Rebalance not needed.
        }
    }

    /**
     * Method to rebalance left.
     * @pre localRoot is the root of an AVL subtree that is
     *      critically left-heavy.
     * @post Balance is restored.
     * @param localRoot Root of the AVL subtree
     *        that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
        System.out.println("Rebalancing left part of node "+localRoot.data);
        // Obtain reference to left child.
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        // See whether left-right heavy.
        if (leftChild.balance > AVLNode.BALANCED) {
            // Obtain reference to left-right child.
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            // Adjust the balances to be their new values after
            // the rotations are performed.
            if (leftRightChild.balance < AVLNode.BALANCED) {
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            } else if (leftRightChild.balance > AVLNode.BALANCED) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else {
                leftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform left rotation.
            localRoot.left = rotateLeft(leftChild);
        } else { //Left-Left case
            // In this case the leftChild (the new root)
            // and the root (new right child) will both be balanced
            // after the rotation.
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root right.
        return (AVLNode<E>) rotateRight(localRoot);
    }


    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
        // Obtain reference to right child.
        System.out.println("Rebalancing right part of node "+localRoot.data);
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        // See whether right-left heavy.
        if (rightChild.balance < AVLNode.BALANCED) {
            // Obtain reference to left-right child.
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
            // Adjust the balances to be their new values after
            // the rotations are performed.
            if (rightLeftChild.balance < AVLNode.BALANCED) {
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            } else if (rightLeftChild.balance > AVLNode.BALANCED) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } else {
                rightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform left rotation.
            localRoot.right = rotateRight(rightChild);
        } else { //right-right case
            // In this case the rightChild (the new root)
            // and the root (new left child) will both be balanced
            // after the rotation.
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root left.
        return (AVLNode<E>) rotateLeft(localRoot);
    }


    /**
     * Method to decrement the balance field and to reset the value of
     * increase.
     * @pre The balance field was correct prior to an insertion [or
     *      removal,] and an item is either been added to the left[
     *      or removed from the right].
     * @post The balance is decremented and the increase flags is set
     *       to false if the overall height of this subtree has not
     *       changed.
     * @param node The AVL node whose balance is to be incremented
     */
    private void decrementBalance(AVLNode<E> node) {
        // Decrement the balance.
        node.balance--;
        if (node.balance == AVLNode.BALANCED) {
            // If now balanced, overall height has not increased.
            increase = false;
        }
    }

    private void incrementBalance(AVLNode<E> node){
        node.balance++;
        if(node.balance == AVLNode.BALANCED){
            increase =false;
        }
    }
}
