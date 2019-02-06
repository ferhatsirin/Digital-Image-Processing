package HashingSet;

import java.util.*;

public class HashingSet<E> implements Set<E> {

    private Node<E>[] theTable;
    private final E DELETED =(E)new Object();
    private final static int START_CAPACİTY =13;
    private final static double LOAD_FACTOR =0.75;
    private int numKeys;

    private static class Node<E>{
        E data;
        Node<E>[] nextTable;
        public Node(E d){
            data =d;
            nextTable =null;
        }
        public void setTable(int size){
            System.out.println("New table created with size "+size);
            nextTable = new Node[size];
        }
    }

    public HashingSet(){
        theTable =new Node[START_CAPACİTY];
        numKeys =0;
    }
    public HashingSet(HashingSet<E> obj){
        theTable =obj.theTable;
        numKeys =obj.numKeys;
    }

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this set (its cardinality)
     */
    @Override
    public int size() {
        return numKeys;
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this set
     * contains an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this set is to be tested
     * @return <tt>true</tt> if this set contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this set
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              set does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        if(isEmpty())
            return false;

        return findRecursive(theTable,o);
    }

    private boolean findRecursive(Node<E>[] nodeTable,Object o){

        while(nodeTable !=null){
            int hash =o.hashCode()%nodeTable.length;
            if(hash < 0){
                hash +=nodeTable.length;
            }
            if(nodeTable[hash] != null && nodeTable[hash].data.equals(o)){
                return true;
            }
            else if(nodeTable[hash]!= null){
                return findRecursive(nodeTable[hash].nextTable,o);
            }
            else{
                return false;
            }
        }
        return false;
    }

    /**
     * Returns an iterator over the elements in this set.  The elements are
     * returned in no particular order (unless this set is an instance of some
     * class that provides a guarantee).
     *
     * @return an iterator over the elements in this set
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index =0;
            Node<E>[] table =theTable;
            Stack<Node<E>[]> backTable =new Stack<>();
            Stack<Integer> backIndex =new Stack<>();
            @Override
            public boolean hasNext() {
                if(table !=null) {
                    while (index < table.length && table[index] == null) {
                        ++index;
                    }
                    if (index < table.length ) {

                        if(table[index].data.equals(DELETED)){

                            backTable.push(table);
                            backIndex.push(index+1);
                            table =table[index].nextTable;
                            index =0;
                            return hasNext();
                        }
                        else {
                            return true;
                        }
                    }
                    else {
                        try {
                            table = backTable.pop();
                            index = backIndex.pop();
                            return hasNext();
                        }
                        catch(EmptyStackException ex){
                            return false;
                        }
                    }
                }
                return false;
            }

            @Override
            public E next() {
                if(hasNext()) {
                    E result = table[index].data;
                    if(table[index].nextTable !=null){
                        backTable.push(table);
                        backIndex.push(index+1);
                        table =table[index].nextTable;
                        index =0;
                    }
                    else{
                        ++index;
                    }
                    return result;
                }
                throw new NoSuchElementException();
            }
        };
    }

    /**
     * Returns an array containing all of the elements in this set.
     * If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the
     * elements in the same order.
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all the elements in this set
     */
    @Override
    public Object[] toArray() {

        Object[] arr =new Object[numKeys];
        Iterator iter =iterator();
        int i=0;
        while(iter.hasNext()){
            arr[i] =(Object)iter.next();
            ++i;
        }

        return arr;
    }

    /**
     * Returns an array containing all of the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     * <p>If this set fits in the specified array with room to spare
     * (i.e., the array has more elements than this set), the element in
     * the array immediately following the end of the set is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * set <i>only</i> if the caller knows that this set does not contain
     * any null elements.)
     * <p>If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements
     * in the same order.
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * <p>Suppose <tt>x</tt> is a set known to contain only strings.
     * The following code can be used to dump the set into a newly allocated
     * array of <tt>String</tt>:
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param arr the array into which the elements of this set are to be
     *          stored, if it is big enough; otherwise, a new array of the same
     *          runtime type is allocated for this purpose.
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in this
     *                              set
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public <T> T[] toArray(T[] arr) {

        Iterator iter =iterator();
        int i=0;
        while(iter.hasNext()){
            arr[i] =(T)iter.next();
            ++i;
        }

        return arr;
    }

    /**
     * Adds the specified element to this set if it is not already present
     * (optional operation).  More formally, adds the specified element
     * <tt>e</tt> to this set if the set contains no element <tt>e2</tt>
     * such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.  In combination with the
     * restriction on constructors, this ensures that sets never contain
     * duplicate elements.
     * <p>The stipulation above does not imply that sets must accept all
     * elements; sets may refuse to add any particular element, including
     * <tt>null</tt>, and throw an exception, as described in the
     * specification for {@link Collection#add Collection.add}.
     * Individual set implementations should clearly document any
     * restrictions on the elements that they may contain.
     *
     * @param item element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this set
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified element
     *                                       prevents it from being added to this set
     */
    @Override
    public boolean add(E item) {

        if(!contains(item)){
            return addRecursive(theTable,item);
        }
        return false;
    }

    public boolean addRecursive(Node<E>[] table,E item){
        int hash =item.hashCode()%table.length;
        if(hash < 0){
            hash +=table.length;
        }
        if(table[hash] == null){
            table[hash] =new Node<>(item);
            ++numKeys;
            System.out.println(item +" adding at index "+hash);
            double load =(double)numKeys/(double)table.length;
            if(load > LOAD_FACTOR ){
                rehash();
            }
            return true;
        }
        else{
            if(table[hash].nextTable == null){
                table[hash].setTable(getGreatestPrime(table.length));
            }
            return addRecursive(table[hash].nextTable,item);
        }

    }

    private void rehash(){
        System.out.println("Rehashing table new size is "+(2*theTable.length+1));
        HashingSet<E> oldTable = new HashingSet<>(this);

        theTable =new Node[2*theTable.length+1];
        numKeys =0;
        Iterator iter = oldTable.iterator();
        while(iter.hasNext()){
            add((E) iter.next());
        }
    }

    private int getGreatestPrime(int size){
        boolean found;
        for(int i=size-1; i>1; --i){
            found =false;
            for(int j =2; j<i;++j){
                if(i%j == 0){
                    found =true;
                    break;
                }
            }
            if(!found){
                return i;
            }
        }
        return 2; // if not found smallest prime number
    }

    /**
     * Removes the specified element from this set if it is present
     * (optional operation).  More formally, removes an element <tt>e</tt>
     * such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this set contains such an element.  Returns <tt>true</tt> if this set
     * contained the element (or equivalently, if this set changed as a
     * result of the call).  (This set will not contain the element once the
     * call returns.)
     *
     * @param o object to be removed from this set, if present
     * @return <tt>true</tt> if this set contained the specified element
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this set
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this set
     */
    @Override
    public boolean remove(Object o) {

        return removeRecursive(theTable,o);
    }

    private boolean removeRecursive(Node<E>[] nodeTable,Object o){
        if(nodeTable != null){
            int hash =o.hashCode()%nodeTable.length;
            if(hash < 0){
                hash +=nodeTable.length;
            }
            if(nodeTable[hash] != null && nodeTable[hash].data.equals(o)){
                if(nodeTable[hash].nextTable != null) {
                    nodeTable[hash].data = DELETED;
                }
                else{
                    nodeTable[hash] =null;
                }
                --numKeys;
                return true;
            }
            else if(nodeTable[hash] != null) {
                return removeRecursive(nodeTable[hash].nextTable, o);
            }
            else{
                return false;
            }

        }
        return false;
    }

    /**
     * Returns <tt>true</tt> if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns <tt>true</tt> if it is a <i>subset</i> of this set.
     *
     * @param c collection to be checked for containment in this set
     * @return <tt>true</tt> if this set contains all of the elements of the
     * specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              set
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this set does not permit null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {

        Iterator iter =c.iterator();
        while(iter.hasNext()){
            if(!contains(iter.next())){
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present (optional operation).  If the specified
     * collection is also a set, the <tt>addAll</tt> operation effectively
     * modifies this set so that its value is the <i>union</i> of the two
     * sets.  The behavior of this operation is undefined if the specified
     * collection is modified while the operation is in progress.
     *
     * @param c collection containing elements to be added to this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of the
     *                                       specified collection prevents it from being added to this set
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this set does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this set
     * @see #add(Object)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {

        Iterator iter =c.iterator();

        while(iter.hasNext()){
            add((E) iter.next());
        }

        return true;
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this set all of its elements that are not contained in the
     * specified collection.  If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * <i>intersection</i> of the two sets.
     *
     * @param c collection containing elements to be retained in this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {

        clear();
        return addAll((Collection<? extends E>) c);
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     *
     * @param c collection containing elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator iter =c.iterator();

        while(iter.hasNext()){
            remove(iter.next());
        }

        return true;
    }

    /**
     * Removes all of the elements from this set (optional operation).
     * The set will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> method
     *                                       is not supported by this set
     */
    @Override
    public void clear() {

        theTable =new Node[START_CAPACİTY];
        numKeys =0;
    }

    public String toString(){
        StringBuilder str =new StringBuilder();
        str.append("[");
        Iterator iter =iterator();
        while(iter.hasNext()){
            str.append(iter.next()+", ");
        }
        int last =str.lastIndexOf(",");
        if(last !=-1) {
            str.deleteCharAt(last);
        }
        str.append("]");
        return str.toString();
    }
}