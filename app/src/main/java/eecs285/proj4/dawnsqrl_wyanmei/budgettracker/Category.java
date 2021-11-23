package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import java.io.Serializable;

public class Category implements Serializable {

    private String title;
    private Double amount;
    private boolean isPositive;//meaning: is payment; else is income

    Category(String title, Double amount, boolean isIncome) {
        this.title = title;
        this.amount = amount;
        this.isPositive = !isIncome;
    }

    String getTitle() {
        return title;
    }

    Double getAmount() {
        return amount;
    }

    boolean isPositive() {return isPositive;}

    void addAmount(Double amount, boolean isIncome) {
        if (!isIncome) {
            this.amount += amount;
        } else {
            this.amount -= amount;
        }
        this.isPositive = (amount >= 0);
    }
}
