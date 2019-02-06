import AVLTree.*;

import java.security.InvalidParameterException;

public class AVLTreeTest {

    public static void main(String[] args){
        System.out.println("AVL tree test 1 :");
        AVLTree<Integer> tree1 =new AVLTree<>();
        tree1.add(50);
        tree1.add(25);
        tree1.add(60);
        tree1.add(40);
        tree1.add(70);
        tree1.add(35);
        tree1.add(20);
        System.out.println("Tree 1 :");
        System.out.println(tree1.toString());
        tree1.remove(70);
        System.out.println("Tree 1 after removing 70:");
        System.out.println(tree1.toString());
        tree1.remove(35);
        System.out.println("Tree 1 after removing 35");
        System.out.println(tree1.toString());
        tree1.remove(25);
        System.out.println("Tree 1 after removing 25");
        System.out.println(tree1.toString());

        System.out.println("AVL tree test 2 :");
        AVLTree<Integer> tree2 =new AVLTree<>();
        System.out.println("Adding from 5 to 20 to avl tree respectively");

        for(int i=5;i<=20;++i){
            tree2.add(i);
        }
        System.out.println("Tree 2 :");
        System.out.println(tree2.toString());
        tree2.remove(5);
        tree2.remove(12);
        System.out.println("Tree 2 after removing 5 and 12:");
        System.out.println(tree2.toString());
        tree2.remove(10);
        tree2.remove(14);
        tree2.remove(7);
        System.out.println("Tree 2 after removing 10, 14 and 7 :");
        System.out.println(tree2.toString());
        tree2.remove(18);
        tree2.remove(19);
        tree2.remove(16);
        System.out.println("Tree 2 after removing 18, 19 and 16 :");
        System.out.println(tree2.toString());
        tree2.remove(17);
        System.out.println("Tree 2 result :");
        System.out.println(tree2.toString());

        System.out.println("Test 3 from binary tree to avl tree :");
        BinarySearchTree<Integer> btree =new BinarySearchTree<Integer>();
        System.out.println("Binary tree created from 0 to 10 added respectively");
        for(int i=0;i<=10;++i){
            btree.add(i);
        }
        System.out.println("Binary tree :");
        System.out.println(btree.toString());
        AVLTree<Integer> avl=null;
        try{
            avl =new AVLTree(btree);
        }
        catch (InvalidParameterException ex){
            System.out.println("Invalid parameter exception caught");
        }
        System.out.println("New binary tree created ");
        btree =new BinarySearchTree<Integer>();
        btree.add(25);
        btree.add(44);
        btree.add(18);
        btree.add(32);
        btree.add(65);
        btree.add(20);
        System.out.println("New binary tree :");
        System.out.println(btree.toString());
        try{
            avl =new AVLTree<Integer>(btree);
        }
        catch(InvalidParameterException ex){
            System.out.println("Invalid parameter exception caught");
        }

        System.out.println("AVL tree from binary tree :");
        System.out.println(avl.toString());

    }
}
