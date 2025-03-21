package Concrete;

import Abstract.Calculation;
import Entity.Employee;

public class BonusAndTaxCalculation implements Calculation<Employee> {

    protected Calculation bonusCalculation;
    protected Calculation taxCalculation;

    public BonusAndTaxCalculation(BonusCalculation bonusCalculation, TaxCalculation taxCalculation){
        this.bonusCalculation = (BonusCalculation) bonusCalculation;
        this.taxCalculation = (TaxCalculation) taxCalculation;
    }

    @Override
    public float calculate(Employee employee) {
        return employee.getSalary() + this.bonusCalculation.calculate(employee) - this.taxCalculation.calculate(employee);
    }
}
