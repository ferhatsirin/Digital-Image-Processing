import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Random;

public class AverageRunTimeTest {


    public static <E> void print(E[] array){
        for(E item :array){
            System.out.print(item+" ");
        }
        System.out.println();
    }

    public static void generateRandArray(Integer[] arr,int size,int bound){

        Random rand =new Random();
        for(int i=0;i<size;++i){
            arr[i] =new Integer(rand.nextInt(bound));
        }
    }

    public static void generateRandList(LinkedList<Integer> list, int size,int bound){
        Random rand =new Random();
        for(int i=0;i<size;++i){
            list.add(rand.nextInt(bound));
        }
    }


    /**
     * Create different random arrays and looks the average time for 100 loop
     * Do it with 10 different size of array from 100 to 1000
     * @param consumer sorting algorithm
     * @return the average time for 10 different size of array with 100 loop calculation
     */
    public static double averageTime(ArrayConsumer consumer){
        Random rand =new Random();
        int startSize =10; int size =0;
        long startTime, runTime;
        double sum =0.0; double averageForSize =0.0;
        double averageForAlg =0.0;

        for(int i=0;i<10;++i){
            sum =0.0;

            for(int j=0;j<100;++j) {
                size =startSize*(10*(i+1));
                Integer[] arr =new Integer[size];
                generateRandArray(arr,size,500);

                startTime =System.nanoTime();
                consumer.method(arr);
                runTime =System.nanoTime()-startTime;

                sum +=runTime;
            }
            averageForSize +=sum /100.0;
            System.out.printf("Run time for size %d is %10.3f \n", size, (sum/100.0));
        }
        averageForAlg =averageForSize/10.0;
        return averageForAlg;
    }

    public static void main(String[] args) {

        Integer[] arr = new Integer[10];
        LinkedList<Integer> list = new LinkedList<>();

        generateRandArray(arr, 10, 100);
        System.out.println("Array before sorting :");
        print(arr);
        InsertionSort.insertionSort(arr);
        System.out.println("Array after Insertion Sort");
        print(arr);
        System.out.println();

        generateRandArray(arr, 10, 100);
        System.out.println("Array before sorting :");
        print(arr);
        MergeSort.mergeSort(arr);
        System.out.println("Array after Merge Sort");
        print(arr);
        System.out.println();

        generateRandList(list, 10, 100);
        System.out.println("List before sorting : ");
        System.out.println(list);
        DoubleMergeSort.doubleMergeSort(list);
        System.out.println("List after Double Merge Sort :");
        System.out.println(list);
        System.out.println();

        generateRandArray(arr, 10, 100);
        System.out.println("Array before sorting :");
        print(arr);
        HeapSort.heapSort(arr);
        System.out.println("Array after Heap Sort");
        print(arr);
        System.out.println();

        generateRandArray(arr, 10, 100);
        System.out.println("Array before sorting :");
        print(arr);
        QuickSort.quickSort(arr);
        System.out.println("Array after Quick Sort");
        print(arr);
        System.out.println();

        double time;

        System.out.println("Run times for insertion sort :");
        time = averageTime(array -> InsertionSort.insertionSort(array));
        System.out.printf("Average time for insertion sort : %10.3f \n", time);
        System.out.println();

        System.out.println("Run times for merge sort :");
        time = averageTime(array -> MergeSort.mergeSort(array));
        System.out.printf("Average time for merge sort : %10.3f \n", time);
        System.out.println();

        System.out.println("Run times for heap sort :");
        time = averageTime(array -> HeapSort.heapSort(array));
        System.out.printf("Average time for heap sort : %10.3f \n", time);
        System.out.println();

        System.out.println("Run times for quick sort :");
        time = averageTime(array -> QuickSort.quickSort(array));
        System.out.printf("Average time for quick sort : %10.3f \n", time);
        System.out.println();

        // list test
        System.out.println("Run times for Double Merge Sort :");
        double sum;
        int size = 0;
        int startSize = 10;
        long startTime, runTime;
        double averageForSize = 0.0;
        double average;
        for (int i = 0; i < 10; ++i) {
            sum = 0.0;
            list.clear();
            for (int j = 0; j < 10; ++j) {
                size = startSize * (10 * (i + 1));
                generateRandList(list, size, 500);

                startTime = System.nanoTime();
                DoubleMergeSort.doubleMergeSort(list);
                runTime = System.nanoTime() - startTime;

                sum += runTime;
            }
            averageForSize += sum / 10.0;
            System.out.printf("Run time for size %d is %10.3f \n", size, (sum / 10.0));
        }
        average = averageForSize / 10.0;
        System.out.printf("Average time for Double Merge Sort : %10.3f \n", average);
    }
}
