package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Transaction implements Serializable {

    private String title;
    private String category;
    private Double amount;
    private String timeStamp;

    Transaction(String title, String category, Double amount) {
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.timeStamp = new SimpleDateFormat("yyyy-MM-dd | HH:mm").format(new java.util.Date());
    }

    String getTitle() {
        return title;
    }

    String getCategory() {
        return category;
    }

    Double getAmount() {
        return amount;
    }

    String getTimeStamp() {
        return timeStamp;
    }
}
