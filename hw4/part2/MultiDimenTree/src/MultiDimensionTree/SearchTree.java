package MultiDimensionTree;

public interface SearchTree<E> {

    boolean add(E item);
    boolean contains(E item);
    E find(E item);
    E delete(E item);
    boolean remove(E item);

}
