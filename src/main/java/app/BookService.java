package app;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {
    public static List<Book> expensiveBooks(List<Book> products, double priceMore) {
        List<Book> books = products.stream()
                .filter(p -> "Book".equals(p.getType()) && p.getPrice() > priceMore)
                .collect(Collectors.toList());
        return books;
    }

    public static List<Book> discountedBooks(List<Book> productList, double discount) {
        List<Book> books = productList.stream()
                .filter(p -> p.getType().equals("Book") && p.isDiscountAvailable())
                .map(p -> {
                    double discountedPrice = p.getPrice() - (p.getPrice() * (discount / 100));
                    return new Book(p.getType(), discountedPrice, p.isDiscountAvailable());
                })
                .collect(Collectors.toList());
        return books;
    }

    public static Book findCheapestBook(List<Book> productList) {
        List<Book> books = productList.stream()
                .filter(p -> p.getType().equals("Book"))
                .collect(Collectors.toList());

        if (books.isEmpty()) {
            throw new NoSuchElementException("Product not found");
        }

        Book cheapestBook = books.stream()
                .min(Comparator.comparingDouble(Book::getPrice))
                .orElseThrow(NoSuchElementException::new);

        return cheapestBook;
    }

    public static List<Book> getLastThreeAddedProducts(List<Book> products) {
        return products.stream()
                .sorted(Comparator.comparing(Book::getDateAdded).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}
