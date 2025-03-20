
// TaxSystem.java
package accounting;

public abstract class TaxSystem {
    public abstract double calculateTax(double debit, double credit);
}

// UsnIncome.java
package accounting;

public class UsnIncome extends TaxSystem {
    private static final double TAX_RATE = 0.06;

    @Override
    public double calculateTax(double debit, double credit) {
        return debit * TAX_RATE;
    }
}

// UsnIncomeMinusExpenses.java
package accounting;

public class UsnIncomeMinusExpenses extends TaxSystem {
    private static final double TAX_RATE = 0.15;

    @Override
    public double calculateTax(double debit, double credit) {
        double profit = debit - credit;
        return Math.max(0, profit * TAX_RATE);
    }
}

// Company.java
import accounting.TaxSystem;

public class Company {
    private String title;
    private double debit;
    private double credit;
    private TaxSystem taxSystem;

    public Company(String title, TaxSystem taxSystem) {
        this.title = title;
        this.taxSystem = taxSystem;
        this.debit = 0;
        this.credit = 0;
    }

    public void shiftMoney(int amount) {
        if (amount > 0) {
            debit += amount;
        } else if (amount < 0) {
            credit += Math.abs(amount);
        }
    }

    public void setTaxSystem(TaxSystem taxSystem) {
        this.taxSystem = taxSystem;
    }

    public void payTaxes() {
        double taxAmount = taxSystem.calculateTax(debit, credit);
        System.out.println("Компания " + title + " уплатила налог в размере: " + taxAmount + " руб.");
        debit = 0;
        credit = 0;
    }

    public static void main(String[] args) {
        // Пример использования
        Company myCompany = new Company("ООО Рога и Копыта", new accounting.UsnIncome());

        myCompany.shiftMoney(100000);
        myCompany.shiftMoney(-30000);

        myCompany.payTaxes(); // Компания ООО Рога и Копыта уплатила налог в размере: 6000.0 руб.

        myCompany.setTaxSystem(new accounting.UsnIncomeMinusExpenses());
        myCompany.shiftMoney(150000);
        myCompany.shiftMoney(-50000);

        myCompany.payTaxes(); // Компания ООО Рога и Копыта уплатила налог в размере: 15000.0 руб.
    }
}
