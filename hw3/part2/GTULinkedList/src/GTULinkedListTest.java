import GTULinkedList.GTULinkedList;

import java.util.List;

public class GTULinkedListTest {

    public static void printList(List list){
        for(int i=0;i<list.size();++i){
            System.out.println(list.get(i).toString());
        }
    }

    public static void main(String[] args){

        GTULinkedList<String> list =new GTULinkedList<>();

        list.add("ferhat");
        list.add("mehmet");
        list.add("ahmet");

        System.out.println("Printing list :");
        printList(list);

        list.disable("ferhat");
        System.out.println("After disabling 'ferhat' show disabled:");
        list.showDisabled();
        System.out.println("printList :");
        printList(list);

        list.enable("ferhat");
        System.out.println("After enabling printList:");
        printList(list);

        list.disable("ahmet");
        System.out.println("After disabling 'ahmet' show disabled:");
        list.showDisabled();
        System.out.println("printList :");
        printList(list);

        list.enable("ahmet");
        System.out.println("After enabling printList:");
        printList(list);

    }

}
