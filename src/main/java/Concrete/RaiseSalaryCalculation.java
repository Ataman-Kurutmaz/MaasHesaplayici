package Concrete;

import Abstract.Calculation;
import Entity.Employee;

public class RaiseSalaryCalculation implements Calculation<Employee> {
    @Override
    public float calculate(Employee employee) {
        short workingYear = (short) (Short.parseShort(Employee.CURRENT_YEAR) - employee.getHireYear());
        return workingYear > 19 ? employee.getSalary() * 0.15f : (workingYear < 10 ? employee.getSalary() * 0.05f : employee.getSalary() * 0.1f);
    }
}
