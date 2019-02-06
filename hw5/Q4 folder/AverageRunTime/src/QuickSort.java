public class QuickSort {

    public static <T extends Comparable<T>> void quickSort(T[] table) { // Sort the whole table.
        sort(table, 0, table.length -1);
    }

    private static <T extends Comparable<T>> void sort(T[] table,int first, int last) {
        if (first < last) { // There is data to be sorted.
            int pivIndex = partition(table, first, last);
            sort(table, first, pivIndex-1);
            sort(table, pivIndex + 1, last);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] table,int first, int last) { // Select the first item as the pivot value.
        T pivot = table[first];
        int up = first;
        int down = last;
        do {
            while ((up < last) && (pivot.compareTo(table[up]) >= 0)) {
                up++;
            } // assert: up equals last or table[up] > pivot.
            while (pivot.compareTo(table[down]) < 0) {
                down--;
            } // assert: down equals first or table[down] <= pivot.
            if (up < down) { // if up is to the left of down.
                swap(table, up, down);
            }
        } while (up < down); // Repeat while up is left of down.

        swap(table, first, down);
        return down;
    }

    private static <E extends Comparable<E>> void swap(E[] table,int i, int j) {
        E temp = table[i];
        table[i] = table[j];
        table[j] = temp;
    }

}
