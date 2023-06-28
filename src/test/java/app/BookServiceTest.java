package app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    @Test
    void expensiveBooks() {
        List<Book> products = Arrays.asList(
                new Book("Book", 200),
                new Book("Book", 2200),
                new Book("Toy", 100),
                new Book("Book", 2030)
        );
        List<Book> expensiveBooks = BookService.expensiveBooks(products, 250);

        Assertions.assertEquals(2, expensiveBooks.size());
        Assertions.assertEquals("Book", expensiveBooks.get(0).getType());
        Assertions.assertEquals(2200, expensiveBooks.get(0).getPrice());
        Assertions.assertEquals("Book", expensiveBooks.get(1).getType());
        Assertions.assertEquals(2030, expensiveBooks.get(1).getPrice());
    }

    @Test
    void discountedBooks() {
        List<Book> products = Arrays.asList(
                new Book("Book", 100, true),
                new Book("Toy", 200, false),
                new Book("Book", 150, true),
                new Book("Book", 300, false)
        );

        List<Book> discountedBooks = BookService.discountedBooks(products, 10);

        Assertions.assertEquals(2, discountedBooks.size());
        Assertions.assertEquals("Book", discountedBooks.get(0).getType());
        Assertions.assertEquals(90.0, discountedBooks.get(0).getPrice(), 0.01);
        Assertions.assertTrue(discountedBooks.get(0).isDiscountAvailable());
        Assertions.assertEquals("Book", discountedBooks.get(1).getType());
        Assertions.assertEquals(135.0, discountedBooks.get(1).getPrice(), 0.01);
        Assertions.assertTrue(discountedBooks.get(1).isDiscountAvailable());
    }

    @Test
    void discountedBooksEmpty() {
        List<Book> products = Arrays.asList(
                new Book("Toy", 200, false),
                new Book("Toy", 300, false)
        );

        List<Book> discountedBooks = BookService.discountedBooks(products, 10);

        Assertions.assertTrue(discountedBooks.isEmpty());
    }

    @Test
    void findCheapestBook() throws Exception {
        List<Book> products = Arrays.asList(
                new Book("Book", 200),
                new Book("Book", 300),
                new Book("Toy", 100),
                new Book("Book", 400)
        );

        Book cheapestBook = BookService.findCheapestBook(products);

        Assertions.assertEquals("Book", cheapestBook.getType());
        Assertions.assertEquals(200, cheapestBook.getPrice());
    }

    @Test
    void findCheapestBookNoBookFound() {
        List<Book> products = Arrays.asList(
                new Book("Toy", 100),
                new Book("Toy", 200),
                new Book("Toy", 300)
        );
        Assertions.assertThrows(
                Exception.class,
                () -> BookService.findCheapestBook(products),
                "Expected Exception to be thrown"
        );
    }

    @Test
    void getLastThreeAddedProducts() {
        List<Book> products = Arrays.asList(
                new Book("Book", 200, true, LocalDateTime.parse("2023-05-01T10:00:00")),
                new Book("Toy", 100, false, LocalDateTime.parse("2023-05-02T14:30:00")),
                new Book("Book", 300, true, LocalDateTime.parse("2023-05-03T09:15:00")),
                new Book("Book", 400, true, LocalDateTime.parse("2023-05-04T17:45:00")),
                new Book("Toy", 150, true, LocalDateTime.parse("2023-05-05T11:20:00"))
        );
        List<Book> lastThreeAddedProducts = BookService.getLastThreeAddedProducts(products);

        Assertions.assertEquals(3, lastThreeAddedProducts.size());
        Assertions.assertEquals(products.get(4), lastThreeAddedProducts.get(0));
        Assertions.assertEquals(products.get(3), lastThreeAddedProducts.get(1));
        Assertions.assertEquals(products.get(2), lastThreeAddedProducts.get(2));
    }

    @Test
    void calculateTotalCost() {
        List<Book> products = Arrays.asList(
                new Book("Book", 50, true, LocalDateTime.of(2023, 5, 1, 10, 0)),
                new Book("Book", 100, true, LocalDateTime.of(2023, 7, 15, 14, 30)),
                new Book("Toy", 60, true, LocalDateTime.of(2023, 4, 20, 9, 15)),
                new Book("Book", 80, true, LocalDateTime.of(2022, 12, 10, 17, 45)),
                new Book("Book", 70, true, LocalDateTime.of(2023, 9, 5, 11, 20))
        );

        double totalCost = BookService.calculateTotalCost(products);

        Assertions.assertEquals(120, totalCost);
    }


    @Test
    void groupProductsByType() {
        List<Book> products = Arrays.asList(
                new Book("Book", 100, 1, true, LocalDateTime.of(2022, 1, 1, 0, 0)),
                new Book("Book", 200, 2, false, LocalDateTime.of(2022, 2, 1, 0, 0)),
                new Book("Toy", 150, 3, true, LocalDateTime.of(2022, 1, 15, 0, 0)),
                new Book("Toy", 120, 4, false, LocalDateTime.of(2022, 2, 15, 0, 0)),
                new Book("Book", 180, 5, true, LocalDateTime.of(2022, 1, 20, 0, 0))
        );

        Map<String, List<Product>> groupedProducts = BookService.groupProductsByType(products);

        Assertions.assertEquals(2, groupedProducts.size());

        List<Product> bookProducts = groupedProducts.get("Book");
        Assertions.assertEquals(3, bookProducts.size());

        List<Product> toyProducts = groupedProducts.get("Toy");
        Assertions.assertEquals(2, toyProducts.size());
    }
}