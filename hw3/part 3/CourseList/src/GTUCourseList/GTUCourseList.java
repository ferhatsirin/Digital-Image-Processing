package GTUCourseList;

public interface GTUCourseList {

    Course add(Course obj);
    Course remove(Course obj) throws NoSuchFieldException;
    Course next();
    Course nextInSemester();
    int size();
}
