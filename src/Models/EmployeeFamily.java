package Models;
public class EmployeeFamily {
    private Employee employee; 
    private String relation;
    private String name;
    private char gender;

    public EmployeeFamily(Employee employee, String relation, String name, char gender) {
        this.employee = employee;
        this.relation = relation;
        this.name = name;
        this.gender = gender;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "EmployeeFamily{" +
                "employee=" + employee +
                ", relation='" + relation + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
