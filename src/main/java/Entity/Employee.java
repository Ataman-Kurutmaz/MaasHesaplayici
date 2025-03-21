package Entity;

public class Employee {

    private String name;
    private float salary;
    private byte workHours;
    private short hireYear;
    public static final String CURRENT_YEAR = "2021";

    public Employee(String name, float salary, byte workHours, short hireYear) {
        this.name = name;
        this.salary = salary;
        this.workHours = workHours;
        this.hireYear = hireYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public byte getWorkHours() {
        return workHours;
    }

    public void setWorkHours(byte workHours) {
        this.workHours = workHours;
    }

    public short getHireYear() {
        return hireYear;
    }

    public void setHireYear(short hireYear) {
        this.hireYear = hireYear;
    }
}
