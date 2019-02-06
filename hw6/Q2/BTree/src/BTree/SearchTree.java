package BTree;

public interface SearchTree<E> {

    boolean add(E item);
    E find(E target);
    boolean contains(E item);
    E delete(E target);
    boolean remove(E target);
}