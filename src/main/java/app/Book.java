package app;

import java.time.LocalDateTime;

public class Book extends Product{
    private double price;
    private int id;
    private boolean discountAvailable;
    private LocalDateTime dateAdded;

    public Book(String type, double price, int id, boolean discountAvailable, LocalDateTime dateAdded) {
        super(type);
        this.price = price;
        this.id = id;
        this.discountAvailable = discountAvailable;
        this.dateAdded = dateAdded;
    }

    public Book(String type, double price, boolean discountAvailable, LocalDateTime dateAdded) {
        super(type);
        this.price = price;
        this.discountAvailable = discountAvailable;
        this.dateAdded = dateAdded;
    }

    public Book(String type, double price, boolean discountAvailable) {
        super(type);
        this.price = price;
        this.discountAvailable = discountAvailable;
    }

    public Book(String type, double price) {
        super(type);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public boolean isDiscountAvailable() {
        return discountAvailable;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    @Override
    public String toString() {
        return "Book{" +
                "price=" + price +
                ", id=" + id +
                ", discountAvailable=" + discountAvailable +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
