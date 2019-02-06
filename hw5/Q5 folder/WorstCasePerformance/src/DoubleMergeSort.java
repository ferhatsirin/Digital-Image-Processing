import java.util.LinkedList;

public class DoubleMergeSort {

    /**
     * Sort the list according to merge algorithm
     * @param list list to be sorted
     * @param <E> list type
     */
    public static  <E extends Comparable<E>> void doubleMergeSort(LinkedList<E> list) {
        // A table with one element is sorted already.
        if (list.size() > 1) {
            // Split table into halves.
            int halfSize = list.size() / 2;
            LinkedList<E> leftList = new LinkedList<>();
            LinkedList<E> rightList = new LinkedList<>();
            leftList.addAll(list.subList(0, halfSize));
            rightList.addAll(list.subList(halfSize, list.size()));
            // Sort the halves.
            doubleMergeSort(leftList);
            doubleMergeSort(rightList);
            // Merge the halves.
            merge(list, leftList, rightList);
        }
    }


    private static <E extends Comparable<E>> void merge(LinkedList<E> outputList, LinkedList<E> leftList, LinkedList<E> rightList) {
        int i = 0; // Index into the left input list.
        int j = 0; // Index into the right input list.
        int k = 0; // Index into the output list.

        // While there is data in both input sequences
        while (i < leftList.size() && j < rightList.size()) {
            // Find the smaller and insert it into the output list.
            if (leftList.get(i).compareTo(rightList.get(j)) < 0) {
                outputList.set(k,leftList.get(i));
                ++i; ++k;
            } else {
                outputList.set(k,rightList.get(j));
                ++k; ++j;
            }
        }
        // assert: one of the list has more items to copy.
        // Copy remaining input from left list into the output.
        while (i < leftList.size()) {
            outputList.set(k, leftList.get(i));
            ++k; ++i;
        }
        // Copy remaining input from right list into output.
        while (j < rightList.size()) {
            outputList.set(k,rightList.get(j));
            ++k; ++j;
        }
    }
}
