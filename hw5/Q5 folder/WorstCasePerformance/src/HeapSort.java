public class HeapSort {

    public static <E extends Comparable<E>> void heapSort(E[] table) {
        buildHeap(table);
        shrinkHeap(table);
    }

    private static <E extends Comparable<E>> void swap(E[] table,int i, int j) {
        E temp = table[i];
        table[i] = table[j];
        table[j] = temp;
    }

    private static <E extends Comparable<E>> void shrinkHeap(E[] table) {
        int n = table.length; // Invariant: table[0 . . . n ‐ 1] forms a heap.
        while (n > 0) {
            n--;
            swap(table, 0, n); // table[1 . . . n ‐ 1] form a heap.
            int parent = 0;
            while (true) {
                int leftChild = 2 * parent + 1;
                if (leftChild >= n) {
                    break; // No more children.
                }
                int rightChild = leftChild + 1; // Find the larger of the two children.
                int maxChild = leftChild;
                if (rightChild < n && table[leftChild].compareTo(table[rightChild]) < 0){
                    maxChild = rightChild;
                } // If the parent is smaller than the larger child,
                if (table[parent].compareTo(table[maxChild]) < 0) { // Swap the parent and child.
                    swap(table, parent, maxChild);  // Continue at the child level.
                    parent = maxChild;
                } else {  // Heap property is restored.
                    break;  // Exit the loop.
                }
            }
        }
    }

    private static <E extends Comparable<E>> void buildHeap(E[] table) {
        int n = 1; // Invariant: table[0 . . . n ‐ 1] is a heap.
        while (n < table.length) {
            n++; // Add a new item to the heap and reheap.
            int child = n-1;
            int parent = (child-1) / 2; // Find parent.
            while (parent >= 0 && table[parent].compareTo(table[child]) < 0) {
                swap(table, parent, child);
                child = parent;
                parent = (child-1) / 2;
            }
        }
    }

}
