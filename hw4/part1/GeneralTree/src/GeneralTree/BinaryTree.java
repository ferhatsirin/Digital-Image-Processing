package GeneralTree;

import java.io.Serializable;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * Binary tree.
 *
 * @param <E> the type parameter
 */
public class BinaryTree<E> implements Serializable{
    /**
     * The Root of tree.
     */
    protected Node<E> root;

    /**
     * Instantiates a new Binary tree.
     */
    public BinaryTree(){
        root =null;
    }

    /**
     * Instantiates a new Binary tree combining left and right tree.
     *
     * @param data      data the root
     * @param leftTree  the left tree
     * @param rightTree the right tree
     */
    public BinaryTree(E data,BinaryTree<E> leftTree,BinaryTree<E> rightTree){

        root =new Node<E>(data);
        if(leftTree != null){
            root.left =leftTree.root;
        }
        if(rightTree != null){
            root.right =rightTree.root;
        }
    }

    /**
     * Instantiates a new Binary tree.
     *
     * @param node the node for root
     */
    protected BinaryTree(Node<E> node){
        root =node;
    }

    /**
     * Return the left subtree.
     *
     * @return The left subtree or null if either the root or the left subtree is null
     */
    public BinaryTree<E> getLeftSubtree(){
        if(root != null && root.left != null){
            return new BinaryTree<>(root.left);
        }
        else
            return null;
    }

    /**
     * Return the right subtree.
     *
     * @return the binary tree or null if either root or right subtree is null
     */
    public BinaryTree<E> getRightSubtree(){
        if(root != null && root.right != null){
            return new BinaryTree<>(root.right);
        }
        else
            return null;
    }

    /**
     * Determine whether this tree is a leaf.
     *
     * @return true if the root has no children
     */
    public boolean isLeaf(){
        return (root.left == null && root.right == null);
    }

    /**
     * Return root data.
     *
     * @return the data of root
     */
    public E getData(){
        return root.data;
    }

    /**
     * Starter method for preorder traversal
     * the BiConsumer interface. Its method implements
     * abstract method apply.
     *
     * @param consumer an object that instantiates
     */
    public void preOrderTraverse(BiConsumer<E,Integer> consumer){
        preOrderTraverse(root,1,consumer);
    }

    private void preOrderTraverse(Node<E> node,int depth,BiConsumer<E,Integer> consumer){
        if(node == null){
            consumer.accept(null,depth);
        }
        else{
            consumer.accept(node.data,depth);
            preOrderTraverse(node.left,depth+1,consumer);
            preOrderTraverse(node.right,depth+1,consumer);
        }
    }

    public String toString(){
        StringBuilder sb =new StringBuilder();
        toString(root,1,sb);
        return sb.toString();
    }

    private void toString(Node<E> node,int depth,StringBuilder sb){
        for(int  i=1;i<depth;++i){
            sb.append(" ");
        }

        if(node == null){
            sb.append("null\n");
        }
        else{
            sb.append(node.toString());
            sb.append("\n");
            toString(node.left,depth+1,sb);
            toString(node.right,depth+1,sb);
        }
    }

    /**
     * Method to read a binary tree.
     * The input consists of a preorder traversal of the binary tree. The line "null" indicates a null tree.
     * @param scan the Scanner attached to the input file.
     * @return The binary tree
     */
    public static BinaryTree<String> readBinaryTree(Scanner scan){

        String data =scan.nextLine().trim();
        if (data.equals("null")) {
            return null;
        }
        else{
            BinaryTree<String> leftTree =readBinaryTree(scan);
            BinaryTree<String> rightTree =readBinaryTree(scan);
            return new BinaryTree<>(data,leftTree,rightTree);
        }

    }

    protected static class Node<E> implements Serializable {

        protected E data;
        protected Node<E> left;
        protected Node<E> right;

        protected Node(E item){
            data =item;
            left =null;
            right =null;
        }

        public String toString(){
            return data.toString();
        }
    }
}
