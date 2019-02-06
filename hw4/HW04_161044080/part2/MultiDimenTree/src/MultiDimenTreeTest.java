import MultiDimensionTree.*;

import java.util.Arrays;
import java.util.Vector;

public class MultiDimenTreeTest {

    public static void main(String[] args){

        CompareHelper<Integer> comp =new CompareHelper<>();
        MultiDimenTree<Vector<Integer>> tree1 = new MultiDimenTree<>(comp);
        Vector<Integer>[] arrList =new Vector[15];
        arrList[0] =new Vector<>(Arrays.asList(new Integer[]{40,45,50}));
        arrList[1] =new Vector<>(Arrays.asList(new Integer[]{20,70,60}));
        arrList[2] =new Vector<>(Arrays.asList(new Integer[]{45,25,19}));
        arrList[3] =new Vector<>(Arrays.asList(new Integer[]{45,25,19}));
        arrList[4] =new Vector<>(Arrays.asList(new Integer[]{10,60,30}));
        arrList[5] =new Vector<>(Arrays.asList(new Integer[]{25,80,50}));
        arrList[6] =new Vector<>(Arrays.asList(new Integer[]{50,20,5}));
        arrList[7] =new Vector<>(Arrays.asList(new Integer[]{60,54,12}));
        arrList[8] =new Vector<>(Arrays.asList(new Integer[]{60,54,12}));
        arrList[9] =new Vector<>(Arrays.asList(new Integer[]{58,29,15}));
        arrList[10] =new Vector<>(Arrays.asList(new Integer[]{34,65,15}));
        arrList[11] =new Vector<>(Arrays.asList(new Integer[]{12,24,35}));
        arrList[12] =new Vector<>(Arrays.asList(new Integer[]{9,32,43}));
        arrList[13] =new Vector<>(Arrays.asList(new Integer[]{77,89,15}));
        arrList[14] =new Vector<>(Arrays.asList(new Integer[]{77,89,15}));

        System.out.println("Testing 3 dimension tree1 :");
        for(int i=0;i<15;++i)
            System.out.println("adding "+arrList[i].toString()+" : " + tree1.add(arrList[i]));

        System.out.println("Preorder Traverse :");
        tree1.preOrderTraverse();

        System.out.println("tree contains "+arrList[0] + " : "+tree1.contains(arrList[0]));
        System.out.println("tree contains "+arrList[4] + " : "+tree1.contains(arrList[4]));
        System.out.println("tree contains [40, 45, 51]" + " : "+tree1.contains(new Vector<>(Arrays.asList(new Integer[]{40,45,51}))));
        System.out.println("tree contains "+arrList[6] + " : "+tree1.contains(arrList[6]));
        System.out.println("tree contains "+arrList[2] + " : "+tree1.contains(arrList[2]));
        System.out.println("tree contains [50, 20, 6]" + " : "+tree1.contains(new Vector<>(Arrays.asList(new Integer[]{50,20,6}))));
        System.out.println("tree contains "+arrList[13] + " : "+tree1.contains(arrList[13]));

        System.out.println("tree find "+arrList[0] + " : "+tree1.find(arrList[0]));
        System.out.println("tree find "+arrList[5] + " : "+tree1.find(arrList[5]));
        System.out.println("tree find [40, 45, 51]" + " : "+tree1.find(new Vector<>(Arrays.asList(new Integer[]{40,45,51}))));
        System.out.println("tree find "+arrList[7] + " : "+tree1.find(arrList[7]));
        System.out.println("tree find "+arrList[10] + " : "+tree1.find(arrList[10]));
        System.out.println("tree find [50, 20, 6]" + " : "+tree1.find(new Vector<>(Arrays.asList(new Integer[]{50,20,6}))));
        System.out.println("tree find "+arrList[13] + " : "+tree1.find(arrList[13]));

        System.out.println("remove "+arrList[0] + " : "+tree1.remove(arrList[0]));
        System.out.println("remove "+arrList[4] + " : "+tree1.remove(arrList[4]));
        System.out.println("remove "+arrList[0] + " : "+tree1.remove(arrList[0]));
        System.out.println("remove "+arrList[7] + " : "+tree1.remove(arrList[7]));
        System.out.println("remove "+arrList[7] + " : "+tree1.remove(arrList[7]));
        System.out.println("remove "+arrList[10] + " : "+tree1.remove(arrList[10]));

        System.out.println("delete "+arrList[12] + " : "+tree1.delete(arrList[12]));
        System.out.println("delete "+arrList[12] + " : "+tree1.delete(arrList[12]));
        System.out.println("delete "+arrList[6] + " : "+tree1.delete(arrList[6]));
        System.out.println("delete "+arrList[1] + " : "+tree1.delete(arrList[1]));

        System.out.println("Preorder Traverse :");
        tree1.preOrderTraverse();

        MultiDimenTree<Vector<Integer>> tree2 = new MultiDimenTree<>(comp);
        arrList[0] =new Vector<>(Arrays.asList(new Integer[]{40,45}));
        arrList[1] =new Vector<>(Arrays.asList(new Integer[]{20,70}));
        arrList[2] =new Vector<>(Arrays.asList(new Integer[]{45,25}));
        arrList[3] =new Vector<>(Arrays.asList(new Integer[]{45,25}));
        arrList[4] =new Vector<>(Arrays.asList(new Integer[]{10,60}));
        arrList[5] =new Vector<>(Arrays.asList(new Integer[]{25,80}));
        arrList[6] =new Vector<>(Arrays.asList(new Integer[]{50,20}));
        arrList[7] =new Vector<>(Arrays.asList(new Integer[]{60,54}));
        arrList[8] =new Vector<>(Arrays.asList(new Integer[]{60,54}));
        arrList[9] =new Vector<>(Arrays.asList(new Integer[]{58,29}));
        arrList[10] =new Vector<>(Arrays.asList(new Integer[]{34,65}));
        arrList[11] =new Vector<>(Arrays.asList(new Integer[]{12,24}));
        arrList[12] =new Vector<>(Arrays.asList(new Integer[]{9,32}));
        arrList[13] =new Vector<>(Arrays.asList(new Integer[]{77,89}));
        arrList[14] =new Vector<>(Arrays.asList(new Integer[]{77,89}));

        System.out.println("Testing 2 dimension tree2 :");
        for(int i=0;i<15;++i)
            System.out.println("adding "+arrList[i].toString()+" : " + tree2.add(arrList[i]));

        System.out.println("Preorder Traverse :");
        tree2.preOrderTraverse();

        System.out.println("tree contains "+arrList[0] + " : "+tree2.contains(arrList[0]));
        System.out.println("tree contains "+arrList[4] + " : "+tree2.contains(arrList[4]));
        System.out.println("tree contains [40, 44]" + " : "+tree2.contains(new Vector<>(Arrays.asList(new Integer[]{40,44}))));
        System.out.println("tree contains "+arrList[6] + " : "+tree2.contains(arrList[6]));
        System.out.println("tree contains "+arrList[2] + " : "+tree2.contains(arrList[2]));
        System.out.println("tree contains [10, 20]" + " : "+tree2.contains(new Vector<>(Arrays.asList(new Integer[]{10,20}))));
        System.out.println("tree contains "+arrList[13] + " : "+tree2.contains(arrList[13]));

        System.out.println("tree find "+arrList[0] + " : "+tree2.find(arrList[0]));
        System.out.println("tree find "+arrList[5] + " : "+tree2.find(arrList[5]));
        System.out.println("tree find [40, 44]" + " : "+tree2.find(new Vector<>(Arrays.asList(new Integer[]{40,44}))));
        System.out.println("tree find "+arrList[7] + " : "+tree2.find(arrList[7]));
        System.out.println("tree find "+arrList[10] + " : "+tree2.find(arrList[10]));
        System.out.println("tree find [10, 20]" + " : "+tree2.find(new Vector<>(Arrays.asList(new Integer[]{10,20}))));
        System.out.println("tree find "+arrList[13] + " : "+tree2.find(arrList[13]));

        System.out.println("remove "+arrList[0] + " : "+tree2.remove(arrList[0]));
        System.out.println("remove "+arrList[4] + " : "+tree2.remove(arrList[4]));
        System.out.println("remove "+arrList[0] + " : "+tree2.remove(arrList[0]));
        System.out.println("remove "+arrList[7] + " : "+tree2.remove(arrList[7]));
        System.out.println("remove "+arrList[7] + " : "+tree2.remove(arrList[7]));
        System.out.println("remove "+arrList[10] + " : "+tree2.remove(arrList[10]));

        System.out.println("delete "+arrList[12] + " : "+tree2.delete(arrList[12]));
        System.out.println("delete "+arrList[6] + " : "+tree2.delete(arrList[6]));
        System.out.println("delete "+arrList[6] + " : "+tree2.delete(arrList[6]));
        System.out.println("delete "+arrList[1] + " : "+tree2.delete(arrList[1]));


        System.out.println("Preorder Traverse :");
        tree2.preOrderTraverse();

    }
}
