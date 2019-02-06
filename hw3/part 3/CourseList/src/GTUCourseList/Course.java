package GTUCourseList;

public class Course {

    private final String name;
    private final String code;
    private final int semester;
    private final int ECTSCredits;
    private final int GTUCredits;

    public Course(int semester,String code,String name,int ECTS,int GTU){

        this.semester =semester;
        this.code =code;
        this.name =name;
        ECTSCredits =ECTS;
        GTUCredits =GTU;
    }

    int getSemester(){
        return semester;
    }

    String getName(){
        return name;
    }

    String getCode(){
        return code;
    }

    int getECTSCredits(){
        return ECTSCredits;
    }

    int getGTUCredits(){
        return GTUCredits;
    }

    public boolean equals(Object obj){

        if(obj ==null)
            return false;

        if(this == obj){
            return true;
        }

        if(obj instanceof Course){

            Course course =(Course)obj;
            if(course.code.equalsIgnoreCase(code) &&
                    course.name.equalsIgnoreCase(name) &&
                    course.GTUCredits == GTUCredits &&
                    course.ECTSCredits == ECTSCredits &&
                    course.semester == semester)
                return true;
        }

        return false;
    }

    @Override
    public String toString() {

        return String.format("%d %s %s %d %d", semester, code, name, ECTSCredits, GTUCredits);
    }

}
