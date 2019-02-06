import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class WorstCasePerformanceTest {

    public static <E> void print(E[] array){
        for(E item :array){
            System.out.print(item+" ");
        }
        System.out.println();
    }

    public static void generateReversedSortedArray(Integer[] arr,int size,int bound){

        Random rand =new Random();
        for(int i=0;i<size;++i){
            arr[i] =new Integer(rand.nextInt(bound));
        }
        Arrays.sort(arr, Collections.reverseOrder());
    }

    public static void generateReversedSortedList(LinkedList<Integer> list, int size, int bound){

        Random rand =new Random();
        Integer[] arr =new Integer[size];
        for(int i=0;i<size;++i){
            arr[i] =new Integer(rand.nextInt(bound));
        }
        Arrays.sort(arr, Collections.reverseOrder());
        for(int i=0;i<size;++i){
            list.add(arr[i]);
        }
    }


    /**
     * Create different reversed sorted random arrays and looks the worst case time for 100 loop
     * Do it with 100, 1000, 5000, 10000 sizes of array
     * @param consumer sort algorithm inside ArrayConsumer
     */
    public static void worstCaseTime(ArrayConsumer consumer){
        Random rand =new Random();
        int arraySize[] ={100,1000,5000,10000}; int size =0;
        long startTime, runTime;
        double sum =0.0; double average;

        for(int i=0;i<4;++i){
            sum =0.0;

            for(int j=0;j<100;++j) {
                size =arraySize[i];
                Integer[] arr =new Integer[size];
                generateReversedSortedArray(arr,size,500);

                startTime =System.nanoTime();
                consumer.method(arr);
                runTime =System.nanoTime()-startTime;

                sum +=runTime;
            }
            average =sum /100.0;
            System.out.printf("Run time for size %d is %10.3f \n", size,average);
        }
    }

    public static void main(String[] args){

        Integer[] arr =new Integer[10];

        generateReversedSortedArray(arr,10,100);
        System.out.println("Array before sorting :");
        print(arr);
        InsertionSort.insertionSort(arr);
        System.out.println("Array after Insertion Sort");
        print(arr);
        System.out.println();

        generateReversedSortedArray(arr,10,100);
        System.out.println("Array before sorting :");
        print(arr);
        MergeSort.mergeSort(arr);
        System.out.println("Array after Merge Sort");
        print(arr);
        System.out.println();

        generateReversedSortedArray(arr,10,100);
        System.out.println("Array before sorting :");
        print(arr);
        HeapSort.heapSort(arr);
        System.out.println("Array after Heap Sort");
        print(arr);
        System.out.println();

        LinkedList<Integer> list =new LinkedList<>();
        generateReversedSortedList(list,10,100);
        System.out.println("Array before sorting :");
        System.out.println(list);
        DoubleMergeSort.doubleMergeSort(list);
        System.out.println("Array after Double Merge Sort");
        System.out.println(list);
        System.out.println();

        generateReversedSortedArray(arr,10,100);
        System.out.println("Array before sorting :");
        print(arr);
        QuickSort.quickSort(arr);
        System.out.println("Array after Quick Sort");
        print(arr);
        System.out.println();

        System.out.println("Run times for insertion sort :");
        worstCaseTime(array -> InsertionSort.insertionSort(array));
        System.out.println();

        System.out.println("Run times for merge sort :");
        worstCaseTime(array -> MergeSort.mergeSort(array));
        System.out.println();

        System.out.println("Run times for heap sort :");
        worstCaseTime(array -> HeapSort.heapSort(array));
        System.out.println();

        System.out.println("Run times for quick sort :");
        worstCaseTime(array -> QuickSort.quickSort(array));
        System.out.println();

        System.out.println("Run times for Double Merge Sort :");
        int arraySize[] ={100,1000,5000,10000}; int size =0;
        long startTime, runTime;
        double sum =0.0; double average;

        for(int i=0;i<4;++i){
            sum =0.0;

            for(int j=0;j<10;++j) {
                size =arraySize[i];
                generateReversedSortedList(list,size,500);

                startTime =System.nanoTime();
                DoubleMergeSort.doubleMergeSort(list);
                runTime =System.nanoTime()-startTime;

                sum +=runTime;
            }
            average =sum /10.0;
            System.out.printf("Run time for size %d is %10.3f \n", size,average);
        }

    }
}
