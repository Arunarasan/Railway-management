package Models;
public class Student {
    private int studentId;             
    private String studentName;      
    private String studentQualification; 
    private String studentProof;      
    private char gender;            

    public Student() {
    }

    public Student(int studentId, String studentName, String studentQualification, String studentProof, char gender) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentQualification = studentQualification;
        this.studentProof = studentProof;
        this.gender = gender;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentQualification() {
        return studentQualification;
    }

    public void setStudentQualification(String studentQualification) {
        this.studentQualification = studentQualification;
    }

    public String getStudentProof() {
        return studentProof;
    }

    public void setStudentProof(String studentProof) {
        this.studentProof = studentProof;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentQualification='" + studentQualification + '\'' +
                ", studentProof='" + studentProof + '\'' +
                ", gender=" + gender +
                '}';
    }
}
