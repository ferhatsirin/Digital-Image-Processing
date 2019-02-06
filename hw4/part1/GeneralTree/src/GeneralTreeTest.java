import GeneralTree.*;

public class GeneralTreeTest {

    public static void main(String[] args) {

        GeneralTree<Integer> tree1 = new GeneralTree<>();

        System.out.println("Tree 1 testing : ");
        System.out.println("adding 4, null :" + tree1.add(4, null));
        System.out.println("adding 4,16 :" + tree1.add(4, 16));
        System.out.println("adding 16,7 :" + tree1.add(16, 7));
        System.out.println("adding 4,21 :" + tree1.add(4, 21));
        System.out.println("adding 4,32 :" + tree1.add(4, 32));
        System.out.println("adding 45,13 :" + tree1.add(45, 13));
        System.out.println("adding 32,99 :" + tree1.add(32, 99));
        System.out.println("adding 54,16 :" + tree1.add(54, 16));
        System.out.println("adding 32,19 :" + tree1.add(32, 19));
        System.out.println("adding 21,56 :" + tree1.add(21, 56));
        System.out.println("adding 91,23 :" + tree1.add(91, 23));
        System.out.println("adding 4,38 :" + tree1.add(4, 38));
        System.out.println("adding 99,44 :" + tree1.add(99, 44));

        System.out.println("Level order search for 4 at level 1 :" + tree1.levelOrderSearch(4, 1));
        System.out.println("Level order search for 4 at level 2 :" + tree1.levelOrderSearch(4, 2));
        System.out.println("Level order search for 4 at level 3 :" + tree1.levelOrderSearch(4, 3));
        System.out.println("Level order search for 99 at level 3 :" + tree1.levelOrderSearch(99, 3));
        System.out.println("Level order search for 99 at level 2 :" + tree1.levelOrderSearch(99, 2));
        System.out.println("Level order search for 99 at level 4 :" + tree1.levelOrderSearch(99, 4));
        System.out.println("Level order search for 32 at level 2 :"+tree1.levelOrderSearch(32,2));
        System.out.println("Level order search for 32 at level 3 :"+tree1.levelOrderSearch(32,3));
        System.out.println("Level order search for 7 at level 3 :"+tree1.levelOrderSearch(7,3));

        System.out.println("Post order search for 4 :" + tree1.postOrderSearch(4));
        System.out.println("Post order search for 54 :" + tree1.postOrderSearch(54));
        System.out.println("Post order search for 56 :" + tree1.postOrderSearch(56));
        System.out.println("Post order search for 44 :" + tree1.postOrderSearch(44));
        System.out.println("Post order search for 91 :" + tree1.postOrderSearch(91));
        System.out.println("Post order search for 38 :"+tree1.postOrderSearch(38));

        System.out.println("Preorder Traverse for general tree represented in binary tree :");
        tree1.preOrderTraverse();
        System.out.println("Removing 44 :"+tree1.remove(44));
        System.out.println("Removing 99 :"+tree1.remove(99));
        System.out.println("Removing 38 :"+tree1.remove(38));
        System.out.println("Removing 54 :"+tree1.remove(54));
        System.out.println("Removing 7 :"+tree1.remove(7));
        System.out.println("Preorder Traverse after removing :");
        tree1.preOrderTraverse();
        System.out.println("Total item in the tree :" + tree1.numOfTotalItem());

        GeneralTree<String> tree2 =new GeneralTree<String>();
        System.out.println("Tree2 testing :");

        System.out.println("adding gtu,ferhat :" +tree2.add("gtu","ferhat"));
        System.out.println("adding ferhat,heap :" +tree2.add("ferhat","heap"));
        System.out.println("adding gtu,homework :" +tree2.add("gtu","homework"));
        System.out.println("adding homework,project :" +tree2.add("homework","project"));
        System.out.println("adding tree,search :" +tree2.add("tree","search"));
        System.out.println("adding gtu,tree :" +tree2.add("gtu","tree"));
        System.out.println("adding tree,search :" +tree2.add("tree","search"));
        System.out.println("adding search,linear :" +tree2.add("search","linear"));
        System.out.println("adding queue,search :" +tree2.add("queue","search"));
        System.out.println("adding linked,list :" +tree2.add("linked","list"));
        System.out.println("adding project,list :" +tree2.add("project","list"));

        System.out.println("Level order search 'gtu' at level 1 :" + tree2.levelOrderSearch("gtu", 1));
        System.out.println("Level order search 'gtu' at level 2 :" + tree2.levelOrderSearch("gtu", 2));
        System.out.println("Level order search 'gtu' at level 4 :" + tree2.levelOrderSearch("gtu", 4));
        System.out.println("Level order search 'linear' at level 3 :" + tree2.levelOrderSearch("linear", 3));
        System.out.println("Level order search 'linear' at level 4 :" + tree2.levelOrderSearch("linear", 4));
        System.out.println("Level order search 'tree' at level 2 :" + tree2.levelOrderSearch("tree", 2));
        System.out.println("Level order search 'project' at level 2 :" + tree2.levelOrderSearch("project", 2));
        System.out.println("Level order search 'project' at level 3 :" + tree2.levelOrderSearch("project", 3));

        System.out.println("Post order search for 'gtu' :" + tree2.postOrderSearch("gtu"));
        System.out.println("Post order search for 'queue' :" + tree2.postOrderSearch("queue"));
        System.out.println("Post order search for 'list' :" + tree2.postOrderSearch("list"));
        System.out.println("Post order search for 'linked' :" + tree2.postOrderSearch("linked"));
        System.out.println("Post order search for 'search' :" + tree2.postOrderSearch("search"));

        System.out.println("Preorder Traverse for general tree represented in binary tree :");
        tree2.preOrderTraverse();

        System.out.println("Removing 'linear' :"+tree2.remove("linear"));
        System.out.println("Removing 'project' :"+tree2.remove("project"));
        System.out.println("Removing 'list' :"+tree2.remove("list"));
        System.out.println("Removing 'queue' :" +tree2.remove("queue"));
        System.out.println("Removing 'heap' :"+tree2.remove("heap"));
        System.out.println("Preorder Traverse after removing :");
        tree2.preOrderTraverse();
        System.out.println(tree2.numOfTotalItem());
    }
}
