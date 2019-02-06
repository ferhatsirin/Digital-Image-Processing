public class InsertionSort {

    public static <E extends Comparable<E>> void insertionSort(E[] table) {

        for (int nextPos = 1; nextPos < table.length; nextPos++) { // Invariant: table[0 . . . nextPos ‐ 1] is sorted.
            insert(table, nextPos);
        } // End for.
    } // End sort.


    private static <E extends Comparable<E>> void insert(E[] table,int nextPos) {
        E nextVal = table[nextPos]; // Element to insert.
        while (nextPos > 0 && nextVal.compareTo(table[nextPos-1]) < 0) {
            table[nextPos] = table[nextPos-1]; // Shift down.
            --nextPos; // Check next smaller element.
        } // Insert nextVal at nextPos.
        table[nextPos] = nextVal;
    }
}
