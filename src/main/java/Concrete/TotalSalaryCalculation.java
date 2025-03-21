package Concrete;

import Abstract.Calculation;
import Entity.Employee;

public class TotalSalaryCalculation extends BonusAndTaxCalculation {

    protected Calculation raiseSalaryCalculation;

    public TotalSalaryCalculation(BonusCalculation bonusCalculation, TaxCalculation taxCalculation, RaiseSalaryCalculation raiseSalaryCalculation) {
        super(bonusCalculation, taxCalculation);
        this.raiseSalaryCalculation = raiseSalaryCalculation;
    }

    @Override
    public float calculate(Employee employee) {
        return super.calculate(employee) + raiseSalaryCalculation.calculate(employee);
    }
}
