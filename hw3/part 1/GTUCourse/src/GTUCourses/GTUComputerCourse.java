package GTUCourses;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * GTUComputerCourse holds the courses for computer engineering
 * Helps to make operations getByCode, getByRange, listSemesterCourses etc.
 */
public class GTUComputerCourse implements GTUCourses{

    private LinkedList<Course> courses;
    private File registerFile;


    /**
     * Instantiates a new Gtu computer course.
     * Reads the course file's data
     * @throws IOException the io exception
     */
    public GTUComputerCourse() throws IOException {
        initCourses();
    }

    /**
     *
     * @param code course code
     * @return name of that course corresponding to the code
     */
    public Course getByCode(String code){

        for(int i=0;i<courses.size();++i){

            if(courses.get(i).getCode().equalsIgnoreCase(code)){

                return courses.get(i);
            }
        }

        throw new NoSuchElementException(code);
    }

    /**
     *
     * @param semester must be in range 1-8
     * @return list of that course corresponding to the semester
     */
    public  LinkedList<Course> listSemesterCourses(int semester){

        if(semester < 1|| 8 < semester){

            throw new ArrayIndexOutOfBoundsException(semester);
        }
        int i;
        for(i=0; courses.get(i).getSemester() != semester;++i){
        }

        LinkedList<Course> list =new LinkedList<>();
        for(;i < courses.size() && courses.get(i).getSemester() == semester ;++i)
            list.add(courses.get(i));

        return list;
    }

    /**
     * start must be bigger than 0 and end smaller than size
     * @param start start index
     * @param end end index
     * @return list of the courses corresponding to range [start,end]
     */
    public LinkedList<Course> getByRange(int start, int end){

        if(start < 0 || end <= start || courses.size() < end){

            throw new ArrayIndexOutOfBoundsException(start);
        }

        LinkedList<Course> list =new LinkedList<>();
        for(int i =start; i<=end ;++i)
            list.add(courses.get(i));

        return list;
    }

    /**
     * Add a new course to the list
     * @param newCourse the new course
     * @return true if successful
     */
    public boolean addCourse(Course newCourse) {
        for(int i =0;i <courses.size();++i){
            if(courses.get(i).getSemester() == newCourse.getSemester()) {
                courses.add(i, newCourse);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove course.
     * @param oldCourse the course to be removed
     * @return true if successful
     */
    public boolean removeCourse(Course oldCourse) {

        for(int i=0;i<courses.size();++i){

            if(courses.get(i).equals(oldCourse)){
                courses.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Print courses corresponding to that semester.
     *
     * @param semester the semester
     */
    public void printCourses(int semester){
        if(semester < 1|| 8 < semester){

            throw new ArrayIndexOutOfBoundsException(semester);
        }
        int i;
        for(i=0; courses.get(i).getSemester() != semester; ++i);

        for(;i < courses.size() && courses.get(i).getSemester() == semester;++i){
            System.out.println(courses.get(i).toString());
        }
    }

    private void initCourses() throws IOException {
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
            courses.add(new Course(Integer.parseInt(splitLine[0]),splitLine[1],splitLine[2],Integer.parseInt(splitLine[3]),Integer.parseInt(splitLine[4])));
        }

        reader.close();
    }
}
