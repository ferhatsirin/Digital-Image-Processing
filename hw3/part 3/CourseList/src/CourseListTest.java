import GTUCourseList.Course;
import GTUCourseList.CourseList;

import java.io.IOException;
import java.util.NoSuchElementException;

public class CourseListTest {

    public static void main(String[] args){

        try {
            CourseList list = new CourseList();

            System.out.println("Printing 1. semester ");
            for (int i = 0; i < list.courseSize(); ++i)
                System.out.println(list.next());
            System.out.println("After list.nextInSemester() printing 2. semester");
            list.nextInSemester();
            for (int i = 0; i < list.courseSize(); ++i)
                System.out.println(list.next());

            System.out.println("Removing CSE 102 Computer Programming course :");
            list.remove(new Course(2, "cse 102", "computer programming", 8, 4));
            System.out.println("Semester 2 :");
            for (int i = 0; i < list.courseSize(); ++i)
                System.out.println(list.next());

            System.out.println("Adding CSE 102 Computer Programming course :");
            list.add(new Course(2, "cse 102", "computer programming", 8, 4));
            System.out.println("Semester 2 :");
            for (int i = 0; i < list.courseSize(); ++i)
                System.out.println(list.next());


            System.out.println("Printing all list elements :");
            for (int i = 0; i < list.size(); ++i) {
                for (int j = 0; j < list.courseSize(); ++j) {
                    System.out.println(list.next());
                }
                list.nextInSemester();
            }
        }
        catch(IOException exp){
            System.out.println("File io exception");
        }
        catch(NoSuchElementException exp){
            System.out.println("Removing no such element exception");
        }

    }
}
