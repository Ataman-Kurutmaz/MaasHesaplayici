package Concrete;

import Abstract.Calculation;
import Entity.Employee;

public class BonusCalculation implements Calculation<Employee> {
    @Override
    public float calculate(Employee employee) {
        return employee.getWorkHours() > 40 ? (employee.getWorkHours() - 40f) * 30f : 0;
    }
}
