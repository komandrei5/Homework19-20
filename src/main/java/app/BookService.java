package app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
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

    public static double calculateTotalCost(List<Book> products) {
        LocalDateTime currentYear = LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY, 1, 0, 0);

        double totalCost = products.stream()
                .filter(p -> p.getDateAdded().isAfter(currentYear))
                .filter(p -> p.getType().equals("Book"))
                .filter(p -> p.getPrice() <= 75)
                .mapToDouble(Book::getPrice)
                .sum();
        return totalCost;
    }

    public static Map<String, List<Product>> groupProductsByType(List<Book> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getType));
    }
}
