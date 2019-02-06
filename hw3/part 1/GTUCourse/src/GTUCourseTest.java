import GTUCourses.Course;
import GTUCourses.GTUComputerCourse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class GTUCourseTest {

    public static void printList(List list){

        for(int i=0;i<list.size();++i){
            System.out.println(list.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        try{

            GTUComputerCourse gtuComp =new GTUComputerCourse();

            Course course1 =gtuComp.getByCode("cse 222");
            System.out.println("Code CSE 222 :" +course1);

            course1 =gtuComp.getByCode("cse 331");
            System.out.println("Code CSE 331 :" +course1);

            List list =gtuComp.listSemesterCourses(4);
            System.out.println("Semester 4 list");
            printList(list);

            list =gtuComp.listSemesterCourses(6);
            System.out.println("Semester 6 list");
            printList(list);

            list =gtuComp.getByRange(16,24);
            System.out.println("Range 16,24");
            printList(list);

            list =gtuComp.getByRange(8,14);
            System.out.println("Range 8,14");
            printList(list);

            gtuComp.addCourse(new Course(2,"data 111","data structure",3,3));
            System.out.println("After adding new course Semester 2:");
            list =gtuComp.listSemesterCourses(2);
            printList(list);

            gtuComp.removeCourse(gtuComp.getByCode("cse 222"));
            System.out.println("After removing cse 222 try to reach it and get exception");
            gtuComp.getByCode("cse 222");

        } catch(FileNotFoundException ex){

            System.out.println("File can not be found or opened");
        } catch(IOException ex){

            System.out.println("File io exception");
        } catch(NoSuchElementException ex){

            System.out.println("No such element found :"+ex.getMessage());
        }
    }
}
