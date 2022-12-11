package by.tc.task.entity;

public class Student {
    private String name;
    private String surname;
    private int groupNumber;
    private int studentId;
    public Student(String name, String surname, int groupNumber, int studentId)
    {
        this.name=name;
        this.surname=surname;
        this.groupNumber=groupNumber;
        this.studentId =studentId;
    }

    public int getStudentId() {
        return studentId;
    }
    public int getGroupNumber() {
        return groupNumber;
    }
    public String getSurname(){
        return surname;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student { " +
                "id = " + this.getStudentId() +
                ", group = " + this.getGroupNumber() +
                ", surname = " + this.getSurname() +
                ", name = " + this.getName() +
                " }";
    }
}
