### Amaç

Bu kodun amacı, bir çalışanın maaşını çeşitli unsurlar (vergi, bonus, maaş artışı vb.) dikkate alarak hesaplamaktır. Çalışanın çalışma saatleri, maaşı ve işe başlama yılına göre vergi, bonus, maaş artışı gibi hesaplamalar yapılır. Sonuç olarak, her hesaplama türü bağımsız olarak hesaplanabilir ve nihai maaş bu hesaplamaların birleşiminden elde edilir.

### Kullanılan Tasarım Deseni: **Strategy Pattern**

**Strategy Pattern** (Strateji Deseni), bir algoritmanın farklı versiyonlarını tanımlamak ve bunları birbirinin yerine geçebilecek şekilde kullanmak için kullanılan bir davranışsal tasarım desenidir. Bu deseni kullanarak, bir çalışanın maaşını hesaplamak için çeşitli stratejiler (örneğin vergi hesaplama, bonus hesaplama) tanımlanır ve her biri bağımsız olarak değiştirilebilir.

### Uygulanan Strategy Pattern Adımları:

1. **İnterface Tanımı (`Calculation<T>` Interface)**:
    - Bu arayüz, çalışan üzerinde yapılacak farklı hesaplamaları (bonus, vergi, zam vs.) genelleştirir. `calculate(T t)` adında bir metot içerir. Farklı hesaplamalar bu arayüzü implement eder.

   ```java
   public interface Calculation<T> {
       float calculate(T t);
   }
   ```

2. **Hesaplama Stratejileri**:
    - **Bonus Hesaplama (`BonusCalculation`)**:
      Çalışanın çalışma saatlerinin 40 saati aşması durumunda bonus hesaplanır. Çalışma saati 40'tan fazla olan her saat için 30 birimlik bir bonus eklenir.

      ```java
      public class BonusCalculation implements Calculation<Employee> {
          @Override
          public float calculate(Employee employee) {
              return employee.getWorkHours() > 40 ? (employee.getWorkHours() - 40f) * 30f : 0;
          }
      }
      ```

    - **Vergi Hesaplama (`TaxCalculation`)**:
      Çalışanın maaşı 1000 birimden fazla ise, maaşının %3'ü kadar vergi hesaplanır.

      ```java
      public class TaxCalculation implements Calculation<Employee> {
          @Override
          public float calculate(Employee employee) {
              return employee.getSalary() > 1000 ? employee.getSalary() * 0.03f : 0;
          }
      }
      ```

    - **Maaş Artışı Hesaplama (`RaiseSalaryCalculation`)**:
      Çalışanın işe başlama yılına göre maaş artışı hesaplanır. Eğer çalışan 19 yıldan fazla bir süredir çalışıyorsa maaşı %15 artar, 10 yıldan az çalışıyorsa %5 artar, diğer durumlar için ise %10 artış yapılır.

      ```java
      public class RaiseSalaryCalculation implements Calculation<Employee> {
          @Override
          public float calculate(Employee employee) {
              short workingYear = (short) (Short.parseShort(Employee.CURRENT_YEAR) - employee.getHireYear());
              return workingYear > 19 ? employee.getSalary() * 0.15f : (workingYear < 10 ? employee.getSalary() * 0.05f : employee.getSalary() * 0.1f);
          }
      }
      ```

3. **Kombinasyon Stratejileri**:
    - **Vergi ve Bonus Hesaplama Birleşimi (`BonusAndTaxCalculation`)**:
      Bu sınıf, bonus ve vergi hesaplamalarının birleştirilmesi için kullanılır. Hem bonus hem de vergi hesaplamaları bağımsız stratejiler olarak hesaplanır ve ardından çalışanın maaşına eklenip çıkarılır.

      ```java
      public class BonusAndTaxCalculation implements Calculation<Employee> {
          protected Calculation bonusCalculation;
          protected Calculation taxCalculation;
 
          public BonusAndTaxCalculation(BonusCalculation bonusCalculation, TaxCalculation taxCalculation){
              this.bonusCalculation = bonusCalculation;
              this.taxCalculation = taxCalculation;
          }
 
          @Override
          public float calculate(Employee employee) {
              return employee.getSalary() + this.bonusCalculation.calculate(employee) - this.taxCalculation.calculate(employee);
          }
      }
      ```

    - **Toplam Maaş Hesaplama (`TotalSalaryCalculation`)**:
      Bu sınıf, bonus, vergi ve maaş artışını birleştirerek nihai maaşı hesaplar. Bu sınıf, `BonusAndTaxCalculation` sınıfından türetilmiştir ve maaş artışı hesaplamasını da ekler.

      ```java
      public class TotalSalaryCalculation extends BonusAndTaxCalculation<Employee> {
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
      ```

4. **Ana Program (`Main`)**:
   `Main` sınıfında, farklı hesaplama stratejileri birleştirilir ve çalışanın maaşı hesaplanır. Burada, bir çalışan objesi oluşturulur ve çeşitli hesaplama stratejileriyle (vergi, bonus, maaş artışı) toplam maaş hesaplanır.

   ```java
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
   ```

Bu tasarım, çalışan maaşı hesaplamada farklı stratejilerin kolayca eklenmesine olanak tanır ve her stratejiye ayrı ayrı bakım yapılabilir. Yeni hesaplama stratejileri eklemek için mevcut sınıflara müdahale etmek yerine, yalnızca yeni bir strateji oluşturup `Calculation` arayüzünü implement etmek yeterli olacaktır. Bu da yazılımın esnekliğini ve genişletilebilirliğini artırır.