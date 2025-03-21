package Concrete;

import Abstract.Calculation;
import Entity.Employee;

public class TaxCalculation implements Calculation<Employee> {
    @Override
    public float calculate(Employee employee) {
        return employee.getSalary() > 1000 ? employee.getSalary() * 0.03f : 0;
    }
}
