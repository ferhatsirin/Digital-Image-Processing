import HashingSet.HashingSet;

import java.util.Iterator;
import java.util.LinkedList;

public class HashingSetTest {

    public static void main(String[] args){

        System.out.println("Hash Set test 1 start table length 13");
        HashingSet<Integer> set1 =new HashingSet<>();
        set1.add(5);
        set1.add(6);
        set1.add(7);
        set1.add(18);
        set1.add(31);
        set1.add(20);
        set1.add(26);
        set1.add(55);
        set1.add(38);
        set1.add(32);

        System.out.println();
        System.out.println("set size is : "+set1.size());
        System.out.println("removing 38 "+set1.remove(38));
        System.out.println("removing 38 "+set1.remove(38));
        System.out.println("removing 5 "+set1.remove(5));
        System.out.println("removing 32 "+set1.remove(32));

        System.out.println();

        System.out.println("contains 38 "+set1.contains(38));
        System.out.println("contains 5 "+set1.contains(5));
        System.out.println("contains 20 "+set1.contains(20));
        System.out.println("contains 26 "+set1.contains(26));
        System.out.println("contains 7 "+set1.contains(7));
        System.out.println();
        LinkedList<Integer> list1 =new LinkedList<>();
        list1.add(77);
        list1.add(88);
        list1.add(99);

        System.out.println("addAll 77,88,99 ");
        set1.addAll(list1);
        System.out.println("removeAll 77,88,99 "+set1.removeAll(list1));
        System.out.println();
        System.out.println("contains 77 "+set1.contains(77));
        System.out.println("contains 88 "+set1.contains(88));
        System.out.println();
        System.out.println("Iterator for set1 :");

        Iterator iter =set1.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
        System.out.println();
        System.out.println("Hash Set test 2 start table length 13");
        HashingSet<String> set2 =new HashingSet<>();
        set2.add("ferhat");
        set2.add("gtu");
        set2.add("java");
        set2.add("sign");
        set2.add("sing");
        set2.add("double");
        set2.add("set");
        set2.add("tes");
        set2.add("map");
        set2.add("list");

        System.out.println();
        System.out.println("set size is : "+set2.size());
        System.out.println("removing gtu "+set2.remove("gtu"));
        System.out.println("removing gtu "+set2.remove("gtu"));
        System.out.println("removing tes "+set2.remove("tes"));
        System.out.println("removing list "+set2.remove("list"));

        System.out.println();

        System.out.println("contains gtu "+set2.contains("gtu"));
        System.out.println("contains list "+set2.contains("list"));
        System.out.println("contains java "+set2.contains("java"));
        System.out.println("contains set "+set2.contains("set"));
        System.out.println();
        LinkedList<String> list2 =new LinkedList<>();
        list2.add("sort");
        list2.add("merge");
        list2.add("quick");

        System.out.println("addAll sort,merge,quick ");
        set2.addAll(list2);
        System.out.println("removeAll sort,merge,quick "+set2.removeAll(list2));
        System.out.println();
        System.out.println("contains sort "+set2.contains("sort"));
        System.out.println("contains merge "+set2.contains("merge"));
        System.out.println();
        System.out.println("Iterator for set2 :");

        Iterator iter2 =set2.iterator();
        while(iter2.hasNext()){
            System.out.println(iter2.next());
        }

    }
}