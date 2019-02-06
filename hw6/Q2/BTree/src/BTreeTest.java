import BTree.BTree;

public class BTreeTest {

    public static void main(String[] args){

        System.out.println("B Tree Order 5 Test 1 : ");
        BTree<Integer> tree1 =new BTree<>(5);

        tree1.add(16);
        tree1.add(12);
        tree1.add(22);
        tree1.add(37);
        tree1.add(2);

        for(int i=5;i<11;++i){
            tree1.add(new Integer(i));
        }

        tree1.add(-3);
        tree1.add(55);
        tree1.add(48);
        tree1.add(59);
        tree1.add(62);
        tree1.add(66);
        tree1.add(128);
        tree1.add(-16);
        tree1.add(-25);
        System.out.println("B Tree print :");
        System.out.println(tree1.toString());

        System.out.println("B Tree Order 3 Test 2 : ");
        BTree<String> tree2 =new BTree<>(3);

        tree2.add("B");
        tree2.add("Tree");
        tree2.add("A");
        tree2.add("C");
        tree2.add("F");
        tree2.add("Order");
        tree2.add("Sorted");
        tree2.add("Search");
        tree2.add("Linear");
        tree2.add("Binary");
        tree2.add("Red");
        tree2.add("Black");
        System.out.println("B Tree print : ");
        System.out.println(tree2.toString());

    }
}
