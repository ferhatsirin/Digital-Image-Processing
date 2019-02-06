import java.util.LinkedList;
import java.util.Random;

public class DoubleMergeSortTest {

    public static void generateRandList(LinkedList<Integer> list,int size){
        Random rand =new Random();
        for(int i=0;i<size;++i){
            list.add(rand.nextInt(100));
        }
    }

    public static void main(String[] args){

        System.out.println("Double Merge Sort Test 1 :");
        LinkedList<Integer> list1 =new LinkedList<>();
        generateRandList(list1,10);
        System.out.println("List before sorting :");
        System.out.println(list1);

        DoubleMergeSort.doubleMergeSort(list1);
        System.out.println("After sorting :");
        System.out.println(list1);
        System.out.println();

        System.out.println("Double Merge Sort Test 2 :");
        LinkedList<Integer> list2 =new LinkedList<>();
        generateRandList(list2,15);
        System.out.println("List before sorting :");
        System.out.println(list2);

        DoubleMergeSort.doubleMergeSort(list2);
        System.out.println("After sorting :");
        System.out.println(list2);
        System.out.println();

        System.out.println("Double Merge Sort Test 3 :");
        LinkedList<Integer> list3 =new LinkedList<>();
        generateRandList(list3,20);
        System.out.println("List before sorting :");
        System.out.println(list3);

        DoubleMergeSort.doubleMergeSort(list3);
        System.out.println("After sorting :");
        System.out.println(list3);
    }
}
