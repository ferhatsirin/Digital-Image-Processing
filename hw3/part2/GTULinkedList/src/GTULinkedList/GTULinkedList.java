package GTULinkedList;

import java.util.LinkedList;

/**
 * The type Gtu linked list.
 *
 * @param <E> the type parameter
 */
public class GTULinkedList<E> extends LinkedList<E> {

    private LinkedList<Node<E>> disabledList;

    /**
     * Instantiates a new Gtu linked list.
     */
    public GTULinkedList(){
        disabledList =new LinkedList<>();
    }

    /**
     * Disable the element
     *
     * @param obj the obj to be disabled
     * @return true if successful
     */
    public boolean disable(E obj){

        int i =indexOf(obj);
        if(i != -1){
            remove(i);
            disabledList.add(new Node(obj,i));
            return true;
        }

        return false;
    }

    /**
     * Enable the disabled element
     *
     * @param obj the obj disabled before
     * @return true if successful
     */
    public boolean enable(E obj){

        for(int i=0;i<disabledList.size();++i) {
            if (disabledList.get(i).data.equals(obj)){
                add(disabledList.get(i).index,obj);
                disabledList.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Print the all disabled elements
     */
    public void showDisabled(){
        for(int i=0;i<disabledList.size();++i){

            System.out.println(disabledList.get(i).data);
        }
    }

    private static class Node<E>{
        E data;
        int index;

        private Node(E obj,int i){
            data =obj;
            index =i;
        }
    }
}
