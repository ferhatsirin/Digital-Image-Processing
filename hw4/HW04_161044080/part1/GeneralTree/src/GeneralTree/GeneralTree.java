package GeneralTree;

/**
 * General tree represented by BinaryTree.
 *
 * @param <E> the type parameter
 */
public class GeneralTree<E> extends BinaryTree<E>{

    private Node<E> parentNode;
    private int totalNodes;

    /**
     * Instantiates a new General tree.
     */
    public GeneralTree(){
        root =null;
        parentNode =null;
        totalNodes =0;
    }

    /**
     * Add a new element to tree.
     * Add child item to its parent node if parent does not exist then fails.
     * For the first adding operation child can be null
     * @param parent the parent
     * @param child  the child
     * @return true if successful otherwise false
     */
    public boolean add(E parent,E child){

        if(root == null){
            root =new Node<E>(parent);
            ++totalNodes;
            if(child != null){
                return add(parent,child);
            }
            else
                return true;
        }
        else if(parent == null || child == null) {
            return false;
        }
        parentNode = null;
        returnParentNode(root,parent);
        if(parentNode != null) {
            if(parentNode.left ==null) {
                parentNode.left = new Node<E>(child);
                ++totalNodes;
                return true;
            }
            else{
                return addChild(parentNode.left,child);
            }
        }

        return false;

    }

    private void returnParentNode(Node<E> localRoot,E parent){
        if(localRoot != null) {

            if (localRoot.data.equals(parent)) {
                parentNode =localRoot;
            }
            else {
                if (localRoot.right != null) {
                    returnParentNode(localRoot.right, parent);
                }
                if (localRoot.left != null) {
                    returnParentNode(localRoot.left, parent);
                }
            }
        }
    }

    private boolean addChild(Node<E> childNode,E child){
        if(childNode.right == null){
            childNode.right =new Node<E>(child);
            ++totalNodes;
            return true;
        }
        else{
            return addChild(childNode.right,child);
        }
    }

    /**
     * Remove item from the tree.
     * item should be leaf to be removed.
     * @param item the item
     * @return true if successful otherwise false
     */
    public boolean remove(E item){

        if(root.data.equals(item)){
            if(root.right == null && root.left == null){
                root =null;
                --totalNodes;
                return true;
            }
            else
                return false;
        }
        else{
            parentNode = null;
            removeNode(root,item);
            if(parentNode !=null)
                return true;
            else
                return false;
        }
    }

    private void removeNode(Node<E> node,E item){

        if(node != null){
            if(node.left != null) {
                if (node.left.data.equals(item)){
                    if(node.left.left == null && node.left.right == null){
                        parentNode =node;
                        node.left =null;
                        --totalNodes;
                    }
                }
            }
            if(node.right != null) {
                if (node.right.data.equals(item)) {
                    if(node.right.left == null && node.right.right == null){
                        parentNode =node;
                        node.right = null;
                        --totalNodes;
                    }
                }
            }
            if(parentNode == null) {
                removeNode(node.right, item);
                removeNode(node.left, item);
            }
        }
    }

    /**
     * Number of total item in the tree.
     *
     * @return the number of items in the tree
     */
    public int numOfTotalItem(){
        return totalNodes;
    }


    /**
     * Post order search for the general tree.
     * First looks the left node and then right node and visit the root
     *
     * @param item the item to be looked
     * @return the item if found else null
     */
    public E postOrderSearch(E item){
        parentNode =null;
        System.out.printf("postOrder search path : ");
        postOrder(root,item);
        System.out.println("");
        if(parentNode != null)
            return parentNode.data;
        else
            return null;
    }
    private void postOrder(Node<E> node,E item){
        if(node != null && parentNode ==null){
            postOrder(node.left,item);
            if(parentNode ==null) {
                System.out.printf("%s ",node.data);
            }
            postOrder(node.right,item);

            if(node.data.equals(item)) {
                parentNode = node;
            }
        }
    }

    /**
     * Level order search for general tree.
     * Search item only at that level
     * if item does not exist or another level return null
     * @param item  the item
     * @param level the level
     * @return return item but if item does not exist or in another level return null
     */
    public E levelOrderSearch(E item,int level){

        if(item == null || level < 1){
            return null;
        }

        parentNode =null;
        searchLevel(root,item,1,level);
        if(parentNode != null)
            return parentNode.data;
        else
            return null;
    }

    private void searchLevel(Node<E> node,E item,int count,int level){

        if(count == level){
            searchRightNodes(node,item);
        }
        else if (count < level) {
            if (node.right != null) {
                searchLevel(node.right, item, count, level);
            }
            if (node.left != null) {
                searchLevel(node.left, item, count + 1, level);
            }
        }
    }

    private void searchRightNodes(Node<E> node,E item){
        if(node.data.equals(item)){
            parentNode =node;
        }
        else if(node.right != null){
            searchRightNodes(node.right,item);
        }
    }


    /**
     * Pre order traverse for the general tree.
     * Visit the root and then left and right nodes respectively
     * print the result in String representation
     */
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
}
