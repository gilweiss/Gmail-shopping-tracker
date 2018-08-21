package main.java;

public class Purchase {

    private String item;
    private String from;
    private String price;
    private String date;

    @Override
    public String toString() {
        return "Purchase: " + '\n' +
                "Item: " + item + '\n' +
                "From: " + from + '\n' +
                "Price: " + price + '\n' +
                "Date: " + date + '\n';

    }

    public Purchase(String item, String from, String price, String date) {
        this.item = item;
        this.from = from;
        this.price = price;
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isValid() {
        return (this.date != null &&
                this.from !=null &&
                this.item !=null &&
                this.price !=null);
    }
}
