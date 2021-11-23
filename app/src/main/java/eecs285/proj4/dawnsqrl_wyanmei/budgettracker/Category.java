package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import java.io.Serializable;

public class Category implements Serializable {
    private String title;
    private Double amount;

    Category(String title, Double amount) {
        this.title = title;
        this.amount = amount;
    }

    String getTitle() {return title;}
    Double getAmount() {return amount;}

    void addAmount(Double amount) {this.amount += amount;}

}
