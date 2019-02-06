package DoubleHashMap;

import java.util.*;

public class DoubleHashMap<K extends Comparable,V> implements Map<K,V> {

    private Entry<K,V>[] table;
    private final static int START_CAPACİTY =13;
    private final static double LOAD_FACTOR =0.75;
    private final Entry<K,V> DELETED =new Entry<>(null,null);
    private int numKeys;
    private int numDeletes;


    public DoubleHashMap(){
        table =new Entry[START_CAPACİTY];
        numKeys =0;
        numDeletes =0;
    }


    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return numKeys;
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this map contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    private int hash1(K key){
        int hash =key.hashCode()%table.length;
        if(hash < 0){
            hash +=table.length;
        }

        return hash;
    }

    private int hash2(K key){
        int hash =key.hashCode();
        int prime =getGreatestPrime();
        hash =hash % prime;
        if(hash <= 0){
            hash +=prime;
        }
        return hash;
    }

    private int getGreatestPrime(){
        boolean found;
        for(int i=table.length-1; i>1; --i){
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
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.  More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>v</tt> such that
     * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
     * will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     * specified value
     * @throws ClassCastException   if the value is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified value is null and this
     *                              map does not permit null values
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */

    @Override
    public boolean containsValue(Object value) {

        for(int i=0;i<table.length;++i){
            if(table[i] !=null && table[i] != DELETED){
                if(table[i].value.equals(value)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey
     * containsKey} operation may be used to distinguish these two cases.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */

    @Override
    public V get(Object key) {
        if(isEmpty())
            return null;

        K keyV =(K)key;
        int count =0;
        int hash =(hash1(keyV)+count*hash2(keyV))%table.length;
        if(hash < 0)
            hash +=table.length;

        while(table[hash] !=null){
            if(table[hash] != DELETED && table[hash].key.equals(keyV)){
                return table[hash].value;
            }
            ++count;
            hash =(hash1(keyV)+count*hash2(keyV))%table.length;
            if(hash < 0)
                hash +=table.length;
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * <tt>true</tt>.)
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>,
     * if the implementation supports <tt>null</tt> values.)
     * @throws UnsupportedOperationException if the <tt>put</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the class of the specified key or value
     *                                       prevents it from being stored in this map
     * @throws NullPointerException          if the specified key or value is null
     *                                       and this map does not permit null keys or values
     * @throws IllegalArgumentException      if some property of the specified key
     *                                       or value prevents it from being stored in this map
     */
    @Override
    public V put(K key, V value) {

        int count =0;
        int hash =(hash1(key)+count*hash2(key))%table.length;
        if(hash < 0)
            hash +=table.length;

        V returnVal =null;

        while(table[hash] !=null){
            returnVal =table[hash].value;
            if(table[hash] != DELETED && table[hash].key.equals(key)){
                break;
            }
            ++count;
            hash =(hash1(key)+count*hash2(key))%table.length;
            if(hash < 0)
                hash +=table.length;
        }
        System.out.println(key.toString()+"="+value.toString()+" added at index "+hash);
        table[hash] =new Entry<>(key,value);
        ++numKeys;
        double loadFactor =(double)(numKeys+numDeletes)/(double)table.length;
        if(loadFactor > LOAD_FACTOR){
            rehash();
        }

        return returnVal;
    }

    private void rehash(){
        System.out.println("Rehashing table new size "+(2*table.length+1));
        Entry<K,V>[] oldTable =table;
        table =new Entry[2*table.length+1];
        numKeys =0;
        numDeletes =0;
        for(int i=0;i<oldTable.length;++i){
            if(oldTable[i] != null && oldTable[i] != DELETED){
                put(oldTable[i].key,oldTable[i].value);
            }
        }

    }

    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     * <p>Returns the value to which this map previously associated the key,
     * or <tt>null</tt> if the map contained no mapping for the key.
     * <p>If this map permits null values, then a return value of
     * <tt>null</tt> does not <i>necessarily</i> indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to <tt>null</tt>.
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the key is of an inappropriate type for
     *                                       this map
     *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified key is null and this
     *                                       map does not permit null keys
     *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public V remove(Object key) {
        if(isEmpty())
            return null;

        K keyV =(K)key;
        int count =0;
        int hash =(hash1(keyV)+count*hash2(keyV))%table.length;

        if(hash < 0)
            hash +=table.length;
        while(table[hash] !=null){
            if(table[hash] != DELETED && table[hash].key.equals(keyV)){
                V old =table[hash].value;
                table[hash] =DELETED;
                --numKeys;
                ++numDeletes;
                return old;
            }
            ++count;
            hash =(hash1(keyV)+count*hash2(keyV))%table.length;
            if(hash < 0)
                hash +=table.length;
        }
        return null;
    }

    /**
     * Copies all of the mappings from the specified map to this map
     * (optional operation).  The effect of this call is equivalent to that
     * of calling {@link #put(Object, Object) put(k, v)} on this map once
     * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
     * specified map.  The behavior of this operation is undefined if the
     * specified map is modified while the operation is in progress.
     *
     * @param m mappings to be stored in this map
     * @throws UnsupportedOperationException if the <tt>putAll</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the class of a key or value in the
     *                                       specified map prevents it from being stored in this map
     * @throws NullPointerException          if the specified map is null, or if
     *                                       this map does not permit null keys or values, and the
     *                                       specified map contains null keys or values
     * @throws IllegalArgumentException      if some property of a key or value in
     *                                       the specified map prevents it from being stored in this map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

        Set<? extends Map.Entry<? extends K, ? extends V>> set =m.entrySet();
        Iterator iter =set.iterator();

        while(iter.hasNext()){
            ++numKeys;
            put(((Map.Entry<K,V>)iter.next()).getKey(),((Map.Entry<K,V>)iter.next()).getValue());
        }

    }

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this map
     */
    @Override
    public void clear() {
        numDeletes =0;
        numKeys =0;
        table =new Entry[START_CAPACİTY];
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     *
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<K> keySet() {

        TreeSet<K> set =new TreeSet<>();

        if(!isEmpty()) {
            for (int i = 0; i < table.length; ++i) {
                if (table[i] != null && table[i] != DELETED) {
                    set.add(table[i].key);
                }
            }
        }

        return set;
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a collection view of the values contained in this map
     */
    @Override
    public Collection<V> values() {

        TreeSet<V> set =new TreeSet<>();

        if(!isEmpty()) {
            for (int i = 0; i < table.length; ++i) {
                if (table[i] != null && table[i] != DELETED) {
                    set.add(table[i].value);
                }
            }
        }

        return set;
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {

        TreeSet<Map.Entry<K,V>> set =new TreeSet<>();

        if(!isEmpty()) {
            for (int i = 0; i < table.length; ++i) {
                if (table[i] != null && table[i] != DELETED) {
                    set.add(table[i]);
                }
            }
        }

        return set;
    }

    public String toString(){
        StringBuilder str =new StringBuilder();
        str.append("{");
        for(int i=0;i<table.length;++i){
            if(table[i] != null && table[i ]!= DELETED){
                str.append(table[i].key.toString()+"="+table[i].value+",");
            }
        }
        str.deleteCharAt(str.lastIndexOf(","));
        str.append("}");
        return str.toString();
    }

    public static class Entry<K extends Comparable,V> implements Map.Entry<K,V>, Comparable<Map.Entry<K,V>>{

        private final K key;
        private V value;

        public Entry(K k,V val){
            key =k;
            value =val;
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *                               required to, throw this exception if the entry has been
         *                               removed from the backing map.
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * <tt>remove</tt> operation), the results of this call are undefined.
         *
         * @return the value corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *                               required to, throw this exception if the entry has been
         *                               removed from the backing map.
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value (optional operation).  (Writes through to the map.)  The
         * behavior of this call is undefined if the mapping has already been
         * removed from the map (by the iterator's <tt>remove</tt> operation).
         *
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         * @throws UnsupportedOperationException if the <tt>put</tt> operation
         *                                       is not supported by the backing map
         * @throws ClassCastException            if the class of the specified value
         *                                       prevents it from being stored in the backing map
         * @throws NullPointerException          if the backing map does not permit
         *                                       null values, and the specified value is null
         * @throws IllegalArgumentException      if some property of this value
         *                                       prevents it from being stored in the backing map
         * @throws IllegalStateException         implementations may, but are not
         *                                       required to, throw this exception if the entry has been
         *                                       removed from the backing map.
         */
        @Override
        public V setValue(V value) {
            V old =this.value;
            this.value =value;
            return old;
        }

        @Override
        public boolean equals(Object obj) {
            Entry<K,V> other =(Entry<K,V>)obj;
            return key.equals(other.key) && value.equals(other.value);
        }


        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
         * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
         * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
         * <tt>y.compareTo(x)</tt> throws an exception.)
         * <p>The implementor must also ensure that the relation is transitive:
         * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
         * <tt>x.compareTo(z)&gt;0</tt>.
         * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
         * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
         * all <tt>z</tt>.
         * <p>It is strongly recommended, but <i>not</i> strictly required that
         * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
         * class that implements the <tt>Comparable</tt> interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         * <p>In the foregoing description, the notation
         * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
         * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
         * <tt>0</tt>, or <tt>1</tt> according to whether the value of
         * <i>expression</i> is negative, zero or positive.
         *
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         */
        @Override
        public int compareTo(Map.Entry<K, V> o) {
            return key.compareTo(o.getKey());
        }

        public String toString(){
            return String.format(key.toString() + "="+value.toString());
        }
    }



}
