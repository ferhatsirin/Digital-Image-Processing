import RedBlackTree.RedBlackTree;

public class RedBlackTreeTest {

    public static void main(String[] args){

        System.out.println("Red Black Tree Test 1 :");
        RedBlackTree<Integer> tree1 =new RedBlackTree<Integer>();
        long runTime =System.nanoTime();
        for(int i=10; i< 25;++i){
            System.out.println("Adding "+i+" to tree ");
            tree1.add(new Integer(i));
        }
        runTime =System.nanoTime()-runTime;
        System.out.println("Time needed to create this tree : "+runTime+ " ns");
        System.out.println("Tree toString method :");
        System.out.println(tree1.toString());

        System.out.println("Red Black Tree Test 2 :");
        RedBlackTree<Integer> tree2 =new RedBlackTree<Integer>();
        runTime =System.nanoTime();
        for(int i=30;16 < i;--i){
            System.out.println("Adding "+i+" to tree ");
            tree2.add(new Integer(i));
        }
        runTime =System.nanoTime()-runTime;
        System.out.println("Time needed to create this tree : "+runTime+" ns");
        System.out.println("Tree toString method :");
        System.out.println(tree2.toString());
    }
}
