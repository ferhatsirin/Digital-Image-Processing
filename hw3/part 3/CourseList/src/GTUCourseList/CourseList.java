package GTUCourseList;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Course circular list which has circular list inside
 */
public class CourseList implements GTUCourseList {

    private NodeSemester head;
    private NodeSemester tail;
    private int size;
    private int courseSize;
    private NodeSemester indexSemester;
    private NodeSemester.NodeCourse indexCourse;
    private static LinkedList<Course> courses;
    private static File registerFile;

    /**
     * Instantiates a new Course list.
     *
     * @throws IOException the io exception
     */
    public CourseList() throws IOException {
        head =null;
        tail =null;
        size =0;
        indexSemester =null;
        indexCourse =null;
        initCourses();
    }

    /**
     * Add the obj to the list according to the its semester
     * @param obj to be added
     * @return Course obj that is added
     */
    @Override
    public Course add(Course obj) {
        if(head ==null){
            head =new NodeSemester(obj);
            tail =head;
            indexSemester =head;
            indexCourse =indexSemester.courseHead;
            courseSize =indexSemester.courseSize;
            ++size;
            return obj;
        }

        NodeSemester semester =head;
        for(int i=0;i< size;++i){
            if(semester.courseHead.data.getSemester() == obj.getSemester()) {
                NodeSemester.NodeCourse newCourse =new NodeSemester.NodeCourse(obj);
                semester.courseTail.next =newCourse;
                newCourse.next =semester.courseHead;
                semester.courseTail =newCourse;
                ++semester.courseSize;
                return obj;
            }
            semester =semester.nextSemester;
        }

        semester =new NodeSemester(obj);
        tail.nextSemester =semester;
        semester.nextSemester =head;
        tail =semester;
        ++size;
        return obj;
    }

    /**
     * Remove the obj from the list
     * If element does not exist throw exception
     * @param obj To be removed
     * @return Course object that is removed
     * @throws NoSuchElementException if element does not exist
     */

    @Override
    public Course remove(Course obj) throws NoSuchElementException {
        if(head != null){
            NodeSemester temp =head;
            for(int i=0;i<size;++i){
                if(temp.courseHead.data.getSemester() == obj.getSemester()){

                    if(temp.courseHead.data.equals(obj)){
                        if(indexCourse == temp.courseHead)
                            indexCourse =indexCourse.next;
                        temp.courseHead =temp.courseHead.next;
                        temp.courseTail.next =temp.courseHead;
                        --temp.courseSize;
                        return obj;
                    }
                    NodeSemester.NodeCourse course =temp.courseHead;
                    for(int j=0; j< temp.courseSize-1;++j) {
                        if(course.next.data.equals(obj)){

                            if(course.next == temp.courseTail){
                                temp.courseTail =course;
                            }
                            course.next =course.next.next;
                            --temp.courseSize;
                            return obj;
                        }
                        course =course.next;
                    }
                }
                temp =temp.nextSemester;
            }
        }
        throw new NoSuchElementException(obj.toString());
    }

    /**
     *
     * @return the next element in the circular list at that semester
     */
    @Override
    public Course next() {
        NodeSemester.NodeCourse old =indexCourse;
        indexCourse =indexCourse.next;
        return old.data;
    }

    /**
     *
     * @return the head element of next semester
     */
    @Override
    public Course nextInSemester() {
        NodeSemester old =indexSemester;
        indexSemester =indexSemester.nextSemester;
        indexCourse =indexSemester.courseHead;
        courseSize =indexSemester.courseSize;
        return old.courseHead.data;
    }

    /**
     *
     * @return the size that is semester size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Course size int.
     *
     * @return the size that is course size of that semester
     */
    public int courseSize(){
        courseSize =indexSemester.courseSize;
        return courseSize;
    }

    private void initCourses() throws NoSuchFileException, FileNotFoundException, IOException {
        courses =new LinkedList<>();
        registerFile =new File("courses.csv");

        if(!registerFile.exists()){
            throw new NoSuchFileException(registerFile.getName());
        }

        BufferedReader reader =new BufferedReader(new FileReader(registerFile));
        String line; String[] splitLine;
        line =reader.readLine();
        while((line =reader.readLine()) != null){
            splitLine =line.split(";");
            add(new Course(Integer.parseInt(splitLine[0]),splitLine[1],splitLine[2],Integer.parseInt(splitLine[3]),Integer.parseInt(splitLine[4])));
        }

        reader.close();
    }

    private static class NodeSemester{

        NodeCourse courseHead;
        NodeCourse courseTail;
        int courseSize;
        NodeSemester nextSemester;

        private NodeSemester(Course obj){
            courseHead =new NodeCourse(obj);
            courseTail =courseHead;
            nextSemester =null;
            courseSize =1;
        }

        private static class NodeCourse{

            Course data;
            NodeCourse next;

            private NodeCourse(Course obj){
                data =obj;
                next =null;
            }
        }
    }
}
