package eecs285.proj4.dawnsqrl_wyanmei.budgettracker;

import java.io.Serializable;

public class Category implements Serializable {

  private String title;
  private Double amount;

  Category(String title, Double amount, boolean isIncome) {
    this.title = title;
    if (isIncome) {
      this.amount = -amount;
    } else {
      this.amount = amount;
    }
  }

  String getTitle() {
    return title;
  }

  Double getAmount() {
    return amount;
  }

  void addAmount(Double amount, boolean isIncome) {
    if (isIncome) {
      this.amount -= amount;
    } else {
      this.amount += amount;
    }
  }
}
