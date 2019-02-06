package GTUCourses;

import java.util.LinkedList;

public interface GTUCourses {

    Course getByCode(String code);
    LinkedList<Course> listSemesterCourses(int semester);
    LinkedList<Course> getByRange(int start, int end);
}
