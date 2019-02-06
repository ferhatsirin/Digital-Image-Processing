package MultiDimensionTree;

import java.util.Comparator;
import java.util.List;

/**
 * MultiDimensional tree.
 *
 * @param <E> the type parameter
 */
public class MultiDimenTree<E extends List> extends BinaryTree<E> implements SearchTree<E>{

    private Comparator comparator;
    private Node<E> returnNode;

    /**
     * Instantiates a new MultiDimensional tree.
     *
     * @param comp the comparator for compare objects
     */
    public MultiDimenTree(Comparator comp){
        comparator =comp;
    }

    /**
     * Add a new element to tree.
     * Add item to its parent node according to dimensional comparison
     * If item already exists then it can not be added at the second time
     * @param item to be added
     * @return true if successful otherwise false
     */
    @Override
    public boolean add(E item) {
        if(item == null){
            return false;
        }

        if(root == null){
            root =new Node<E>(item);
            return true;
        }
        else{
            if(add(root,item,0) !=null)
                return true;
            else
                return false;
        }
    }

    private Node<E> add(Node<E> node,E item,int dimension){
        if(node != null){
            dimension %=item.size();
            int comp =comparator.compare(item.get(dimension),node.data.get(dimension));
            if(comp < 0){
                if(node.left == null){
                    node.left =new Node<E>(item);
                    return node.left;
                }
                else{
                    return add(node.left,item,dimension+1);
                }
            }
            else if(0 < comp){
                if(node.right == null){
                    node.right =new Node<E>(item);
                    return node.right;
                }
                else {
                    return add(node.right, item,dimension+1);
                }
            }
            else {
                return null;
            }
        }
        else{
            return null;
        }
    }

    /**
     * Print.
     */
    public void print(){
        print(root);
    }

    private void print(Node<E> node){
        if(node != null){
            for(Object data:node.data){
                System.out.printf("%s ",data.toString());
            }
            System.out.println();
            if(node.left !=null) {
                System.out.printf("this is left : ");
                print(node.left);
            }
            if(node.right != null) {
                System.out.printf("this is right : ");
                print(node.right);
            }
        }
    }
    public void preOrderTraverse(){
        StringBuffer temp =new StringBuffer();
        preOrderTraverse(root,temp);
        System.out.println(temp.toString());
    }
    private void preOrderTraverse(Node<E> node,StringBuffer str){
        if(node != null){
            str.append(node.data.toString()+" ");
            preOrderTraverse(node.left,str);
            preOrderTraverse(node.right,str);
        }
    }

    /**
     * search multidimensional tree to look if the tree contains that item
     * @param item to be looked
     * @return true if target if found otherwise false
     */

    @Override
    public boolean contains(E item) {
        returnNode =null;
        contains(root,item,0);
        if(returnNode != null)
            return true;
        else
            return false;
    }

    private void contains(Node<E> node,E item,int dimension){
        if(node != null){
            if(node.data.equals(item)){
                returnNode =node;
            }
            dimension %=item.size();
            int comp =comparator.compare(item.get(dimension),node.data.get(dimension));
            if(comp< 0){
                contains(node.left,item,dimension+1);
            }
            else{
                contains(node.right,item,dimension+1);
            }
        }
    }

    /**
     * search multidimensional tree to look if the tree contains that item
     * @param item to be looked
     * @return item object if target found otherwise null
     */
    @Override
    public E find(E item) {
        returnNode =null;
        contains(root,item,0);
        if(returnNode != null)
            return returnNode.data;
        else
            return null;
    }

    /**
     * Delete item from multidimensional tree if item is in the tree
     * @param item to be deleted
     * @return item object if found and deleted otherwise null
     */

    @Override
    public E delete(E item) {
        returnNode =null;
        if(remove(item))
            return returnNode.data;

        return null;
    }

    /**
     * Remove item from multidimensional tree if item is in the tree
     * @param item to be removed
     * @return true if found and removed otherwise null
     */

    public boolean remove(E item) {
        if(root !=null && root.data.equals(item)){
            returnNode =root;
            root =null;
            addRecursive(returnNode.left);
            addRecursive(returnNode.right);
            return true;
        }
        else {
            return removeNode(root, item, 0);
        }
    }

    private boolean removeNode(Node<E> node,E item,int dimension) {
        if (node != null) {
            dimension %= item.size();
            int comp = comparator.compare(item.get(dimension), node.data.get(dimension));
            if (comp < 0) {
                returnNode =node;
                return removeNode(node.left, item, dimension + 1);
            } else if (comp > 0) {
                returnNode =node;
                return removeNode(node.right, item, dimension + 1);
            } else {
                if(returnNode.left == null){
                    returnNode.right =null;
                }
                else if(returnNode.right == null){
                    returnNode.left =null;
                }
                else if(node.data.equals(returnNode.left.data)){
                    returnNode.left =null;
                }
                else{
                    returnNode.right =null;
                }
                returnNode =node;
                addRecursive(node.left);
                addRecursive(node.right);
                return true;
            }
        } else {
            return false;
        }
    }

    private void addRecursive(Node<E> node){
        if(node != null){
            add(node.data);
            addRecursive(node.left);
            addRecursive(node.right);
        }
    }

    private E findLargestChild(Node<E> node){

        if(node.right.right == null){
            E ret =node.right.data;
            node.right =node.right.left;
            return ret;
        }
        else{
            return findLargestChild(node.right);
        }
    }
}
