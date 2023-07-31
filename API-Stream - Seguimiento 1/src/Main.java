
import domain.models.Client;
import domain.models.Order;
import domain.models.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // List of products
        List<Product> products = Arrays.asList(
                new Product(101, "Omniscent Reader's Viewpoint", "BOOK", 50000.0),
                new Product(202, "Interestellar", "MOVIE", 50000.0),
                new Product(303, "Laptop", "ELECTRONICS", 2500000.0),
                new Product(404, "No Longer Human", "BOOK", 68000.0),
                new Product(505, "Suzume", "MOVIE", 100000.0),
                new Product(606, "Tablet", "ELECTRONICS", 1500000.0),
                new Product(707, "Scorpio City", "BOOK", 45000.0),
                new Product(808, "Barbie", "MOVIE", 150000.0),
                new Product(909, "Smartphone", "ELECTRONICS", 2000000.0),
                new Product(1010, "Three Ways to Survive in a Ruined World 'Limited Edition'",
                        "BOOK", 186300.0)
        );

        // List of clients
        List<Client> clients = Arrays.asList(
                new Client(51, "Kim Dokja", 1),
                new Client(49, "Lee Hakhyun", 2),
                new Client(1863, "Yoo Joonghyuk", 3)
        );

        // List of orders
        List<Order> orders = Arrays.asList(

                // Order for Kim Dokja
                new Order(01, "Ongoing", LocalDate.of(2023, 2, 13), 
                        LocalDate.of(2023, 2, 18),
                        Arrays.asList(products.get(9), products.get(1), products.get(5)), clients.get(0)),

                // Order for Lee Hakhyun
                new Order(02, "Completed", LocalDate.of(2023, 1, 20), 
                        LocalDate.of(2023, 1, 25),
                        Arrays.asList(products.get(0), products.get(4), products.get(6)), clients.get(1)),

                // Order for Yoo Joonghyuk
                new Order(03, "Cancelled", LocalDate.of(2023, 3, 5), 
                        LocalDate.of(2023, 3, 5),
                        Arrays.asList(products.get(3), products.get(2), products.get(7)), clients.get(2))
        );

        //--------------------------------------------------------------------------------------------------------------

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 11) {
            System.out.println("Menu");
            System.out.println("1. Filtered products: Books with price > 100,000");
            System.out.println("2. Filtered orders: Products belonging to category 'MOVIE'");
            System.out.println("3. Filtered products: Category 'ELECTRONICS' with 10% discount");
            System.out.println("4. Cheapest products from category 'BOOK'");
            System.out.println("5. Orders from tier 2 customers between February 1, 2021, and April 1, 2021");
            System.out.println("6. The 3 most recent orders");
            System.out.println("7. Total global sum of all orders made on a specific date");
            System.out.println("8. Average payment of orders made on a specific date");
            System.out.println("9. Produce a map of data with order records grouped by customer");
            System.out.println("10. Most expensive product for each category");
            System.out.println("11. Exit");
            System.out.print("Option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Filtered products: Books with price > 100,000");
                    getFilteredProductsBooks(products);
                    break;

                case 2:
                    System.out.println("Filtered orders: Products belonging to category 'MOVIE'");
                    getFilteredOrdersByCategory(orders, "MOVIE");
                    break;

                case 3:
                    System.out.println("Filtered products: Category 'ELECTRONICS' with 10% discount");
                    getDiscountedProducts(products);
                    break;

                case 4:
                    System.out.println("Cheapest products from category 'BOOK'");
                    getCheapestProductsByCategory(products, "BOOK");
                    break;

                case 5:
                    System.out.println("Orders from tier 2 customers between February 1, 2021, and April 1, 2021");
                    getOrdersByClientTierAndDate(orders, 2, LocalDate.of(2021, 2, 1), 
                            LocalDate.of(2021, 4, 1));
                    break;

                case 6:
                    System.out.println("The 3 most recent orders");
                    getRecentOrders(orders, 3);
                    break;

                case 7:
                    LocalDate dateToCalculateTotalRevenue = LocalDate.of(2022, 3, 1);
                    System.out.println("Total revenue on " + dateToCalculateTotalRevenue + ": " +
                            getTotalRevenueByDate(orders, dateToCalculateTotalRevenue));
                    break;

                case 8:
                    LocalDate dateToCalculateAveragePayment = LocalDate.of(2022, 3, 12);
                    System.out.println("Average payment on " + dateToCalculateAveragePayment + ": " +
                            getAveragePaymentByDate(orders, dateToCalculateAveragePayment));
                    break;

                case 9:
                    System.out.println("Map of data with order records grouped by customer:");
                    produceMapDataByCustomer(orders);
                    break;

                case 10:
                    System.out.println("Most expensive product for each category:");
                    getMostExpensiveProductsByCategory(products);
                    break;

                case 11:
                    System.out.println("Thank you, goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }

        scanner.close();
    }

    //------------------------------------------------------------------------------------------------------------------

    private static List<Product> getFilteredProductsBooks(List<Product> products) {
        String category = "BOOK";
        double minPrice = 100000;

        List<Product> filteredProducts = products.stream()
                .filter(product -> product.getCategory().equals(category) && product.getPrice() > minPrice)
                .collect(Collectors.toList());

        for (Product product : filteredProducts) {
            System.out.println(product);
        }

        return filteredProducts;
    }

    private static List<Order> getFilteredOrdersByCategory(List<Order> orders, String category) {
        List<Order> filteredOrders = orders.stream()
                .filter(order -> order.getProducts().stream().anyMatch(product -> product.getCategory().equals(category)))
                .collect(Collectors.toList());

        for (Order order : filteredOrders) {
            System.out.println(order);
        }

        return filteredOrders;
    }

    private static List<Product> getDiscountedProducts(List<Product> products) {
        String category = "ELECTRONICS";
        double discountPercentage = 0.10;

        List<Product> discountedProducts = products.stream()
                .filter(product -> product.getCategory().equals(category))
                .peek(product -> product.setPrice(product.getPrice() * (1 - discountPercentage)))
                .collect(Collectors.toList());

        for (Product product : discountedProducts) {
            System.out.println(product);
        }

        return discountedProducts;
    }

    private static List<Product> getCheapestProductsByCategory(List<Product> products, String category) {
        List<Product> cheapestProducts = products.stream()
                .filter(product -> product.getCategory().equals(category))
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());

        for (Product product : cheapestProducts) {
            System.out.println(product);
        }

        return cheapestProducts;
    }

    private static List<Order> getOrdersByClientTierAndDate(List<Order> orders, int tier, LocalDate startDate, 
                                                            LocalDate endDate) {
        List<Order> filteredOrders = orders.stream()
                .filter(order -> order.getClient().getTier() == tier)
                .filter(order -> order.getOrderDate().isAfter(startDate) && 
                        order.getOrderDate().isBefore(endDate.plusDays(1))).collect(Collectors.toList());

        for (Order order : filteredOrders) {
            System.out.println(order);
        }

        return filteredOrders;
    }

    private static List<Order> getRecentOrders(List<Order> orders, int count) {
        List<Order> recentOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(count)
                .collect(Collectors.toList());

        for (Order order : recentOrders) {
            System.out.println(order);
        }

        return recentOrders;
    }

    private static double getTotalRevenueByDate(List<Order> orders, LocalDate date) {
        double totalRevenue = orders.stream()
                .filter(order -> order.getOrderDate().equals(date))
                .flatMapToDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice))
                .sum();

        return totalRevenue;
    }

    private static double getAveragePaymentByDate(List<Order> orders, LocalDate date) {
        OptionalDouble averagePayment = orders.stream()
                .filter(order -> order.getOrderDate().equals(date))
                .flatMapToDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice))
                .average();

        return averagePayment.orElse(0.0);
    }

    private static void produceMapDataByCustomer(List<Order> orders) {
        Map<Client, List<Order>> mapDataByCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::getClient));

        mapDataByCustomer.forEach((customer, customerOrders) -> {
            System.out.println("Customer: " + customer.getName());
            for (Order order : customerOrders) {
                System.out.println(order);
            }
            System.out.println("--------------------------");
        });
    }

    private static Map<String, Product> getMostExpensiveProductsByCategory(List<Product> products) {
        Map<String, Product> mostExpensiveProducts = products.stream()
                .collect(Collectors.toMap(
                        Product::getCategory,
                        Function.identity(),
                        (existing, replacement) -> existing.getPrice() > replacement.getPrice() ? existing : replacement
                ));

        mostExpensiveProducts.forEach((category, product) -> {
            System.out.println("Category: " + category);
            System.out.println("Most expensive product: " + product);
            System.out.println("--------------------------");
        });

        return mostExpensiveProducts;
    }

}