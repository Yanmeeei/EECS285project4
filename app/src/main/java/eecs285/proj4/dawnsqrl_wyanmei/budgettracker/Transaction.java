package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import java.io.Serializable;

public class Transaction implements Serializable {
    private String title;
    private String category;
    private Double amount;

    //TODO: 添加time field

    Transaction(String title, String category, Double amount) {
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    String getTitle() {return title;}
    String getCategory() {return category;}
    Double getAmount() {return amount;}

}
