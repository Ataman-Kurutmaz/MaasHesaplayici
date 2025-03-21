import Concrete.*;
import Entity.Employee;

public class Main {
    public static void main(String[] args) {
        Employee employee = new Employee("Ataman", 2000f, (byte) 45, (short) 1985);
        System.out.println("Adı: " + employee.getName() + "\n" +
                "Maaşı: " + employee.getSalary() + "\n" +
                "Çalışma Saati: " + employee.getWorkHours() + "\n" +
                "Başlangıç Yılı: " + employee.getHireYear() + "\n" +
                "Vergi: " + new TaxCalculation().calculate(employee) + "\n" +
                "Bonus: " + new BonusCalculation().calculate(employee) + "\n" +
                "Maaş Artışı: " + new RaiseSalaryCalculation().calculate(employee) + "\n" +
                "Vergi ve Bonuslar ile Birlikte Maaş: " + new BonusAndTaxCalculation(new BonusCalculation(), new TaxCalculation()).calculate(employee) + "\n" +
                "Toplam Maaş: " + new TotalSalaryCalculation(new BonusCalculation(), new TaxCalculation(), new RaiseSalaryCalculation()).calculate(employee));
    }
}
