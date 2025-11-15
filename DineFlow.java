// ==================== DineFlow ====================
// Developed By: Aryan Mehta
// All imports at the top
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// ==================== MenuEntry.java (Abstract Base Class) ====================
/**
 * Abstract base class demonstrating Abstraction and Information Hiding
 * Encapsulates common properties of all menu items
 */
abstract class MenuEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Private fields demonstrating Encapsulation
    private String itemName;
    private double basePrice;
    private String category;
    private boolean isAvailable;
    
    // Constructor
    public MenuEntry(String itemName, double basePrice, String category) {
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.category = category;
        this.isAvailable = true;
    }
    
    // Getters and Setters demonstrating Data Protection
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(double basePrice) {
        if (basePrice >= 0) {
            this.basePrice = basePrice;
        }
    }
    
    public String getCategory() {
        return category;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
    // Abstract method to be overridden by subclasses
    public abstract String getItemDetails();
    
    // Method that can be overridden
    public double calculatePrice() {
        return basePrice;
    }
    
    @Override
    public String toString() {
        return itemName + " - ₹" + String.format("%.2f", basePrice);
    }
}

// ==================== FoodEntry.java (Inheritance) ====================
/**
 * Demonstrates Inheritance and Polymorphism
 * Extends MenuEntry with food-specific attributes
 */
class FoodEntry extends MenuEntry {
    private static final long serialVersionUID = 1L;
    
    private String dietaryType; // VEG, NON-VEG, VEGAN
    private String cuisine; // INDIAN, CHINESE, CONTINENTAL
    private int preparationTime; // in minutes
    
    public FoodEntry(String itemName, double basePrice, String dietaryType, 
                     String cuisine, int preparationTime) {
        super(itemName, basePrice, "Food");
        this.dietaryType = dietaryType;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }
    
    public String getDietaryType() {
        return dietaryType;
    }
    
    public void setDietaryType(String dietaryType) {
        this.dietaryType = dietaryType;
    }
    
    public String getCuisine() {
        return cuisine;
    }
    
    public int getPreparationTime() {
        return preparationTime;
    }
    
    // Method Overriding
    @Override
    public String getItemDetails() {
        return String.format("%s | %s | %s Cuisine | Prep: %d mins | ₹%.2f",
                getItemName(), dietaryType, cuisine, preparationTime, getBasePrice());
    }
    
    // Polymorphic behavior - can add special pricing logic
    @Override
    public double calculatePrice() {
        // Example: Premium cuisine has 10% markup
        if (cuisine.equalsIgnoreCase("CONTINENTAL")) {
            return getBasePrice() * 1.10;
        }
        return getBasePrice();
    }
}

// ==================== BeverageEntry.java (Inheritance) ====================
/**
 * Demonstrates Inheritance and Polymorphism
 * Extends MenuEntry with beverage-specific attributes
 */
class BeverageEntry extends MenuEntry {
    private static final long serialVersionUID = 1L;
    
    private String servingSize; // SMALL, MEDIUM, LARGE
    private boolean isAlcoholic;
    private String temperature; // HOT, COLD, ROOM
    
    public BeverageEntry(String itemName, double basePrice, String servingSize,
                        boolean isAlcoholic, String temperature) {
        super(itemName, basePrice, "Beverage");
        this.servingSize = servingSize;
        this.isAlcoholic = isAlcoholic;
        this.temperature = temperature;
    }
    
    public String getServingSize() {
        return servingSize;
    }
    
    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }
    
    public boolean isAlcoholic() {
        return isAlcoholic;
    }
    
    public String getTemperature() {
        return temperature;
    }
    
    // Method Overriding
    @Override
    public String getItemDetails() {
        String type = isAlcoholic ? "Alcoholic" : "Non-Alcoholic";
        return String.format("%s | %s | %s | %s | ₹%.2f",
                getItemName(), servingSize, type, temperature, calculatePrice());
    }
    
    // Polymorphic behavior - size-based pricing
    @Override
    public double calculatePrice() {
        double price = getBasePrice();
        switch (servingSize.toUpperCase()) {
            case "MEDIUM":
                return price * 1.25;
            case "LARGE":
                return price * 1.50;
            default:
                return price;
        }
    }
}

// ==================== OrderTicket.java (Encapsulation) ====================
/**
 * Demonstrates Encapsulation and Data Protection
 * Manages order data and operations
 */
class OrderTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int orderCounter = 1000;
    
    private int orderId;
    private int tableNumber;
    private ArrayList<MenuEntry> items;
    private Date orderTime;
    private String status; // PENDING, PREPARING, SERVED, BILLED
    private String customerName;
    
    public OrderTicket(int tableNumber, String customerName) {
        this.orderId = ++orderCounter;
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.orderTime = new Date();
        this.status = "PENDING";
    }
    
    // Encapsulated method to add items
    public void addEntry(MenuEntry item) {
        if (item != null && item.isAvailable()) {
            items.add(item);
            System.out.println("✓ Added: " + item.getItemName());
        } else {
            System.out.println("✗ Item not available or invalid");
        }
    }
    
    // Encapsulated method to remove items
    public boolean removeEntry(String itemName) {
        for (MenuEntry item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }
    
    // Polymorphism in action - calculates total for all item types
    public double computeTotal() {
        double total = 0.0;
        for (MenuEntry item : items) {
            total += item.calculatePrice(); // Polymorphic call
        }
        return total;
    }
    
    public double computeTotalWithTax() {
        double subtotal = computeTotal();
        double cgst = subtotal * 0.025; // 2.5% CGST
        double sgst = subtotal * 0.025; // 2.5% SGST
        return subtotal + cgst + sgst;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public int getTableNumber() {
        return tableNumber;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public ArrayList<MenuEntry> getItems() {
        return new ArrayList<>(items); // Return copy for protection
    }
    
    public void displayOrder() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ORDER ID: #" + orderId + " | TABLE: " + tableNumber);
        System.out.println("Customer: " + customerName + " | Status: " + status);
        System.out.println("Time: " + orderTime);
        System.out.println("=".repeat(60));
        
        if (items.isEmpty()) {
            System.out.println("No items in order");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i).getItemDetails());
            }
        }
        
        System.out.println("-".repeat(60));
        System.out.printf("Subtotal: ₹%.2f%n", computeTotal());
        System.out.printf("CGST (2.5%%): ₹%.2f%n", computeTotal() * 0.025);
        System.out.printf("SGST (2.5%%): ₹%.2f%n", computeTotal() * 0.025);
        System.out.printf("TOTAL: ₹%.2f%n", computeTotalWithTax());
        System.out.println("=".repeat(60));
    }
}

// ==================== MenuManager.java (CRUD Operations) ====================
/**
 * Manages menu items with CRUD operations
 * Demonstrates composition and data management
 */
class MenuManager {
    private ArrayList<MenuEntry> menuItems;
    
    public MenuManager() {
        this.menuItems = new ArrayList<>();
        initializeSampleMenu();
    }
    
    // CREATE
    public void addMenuItem(MenuEntry item) {
        if (item != null) {
            menuItems.add(item);
            System.out.println("✓ Menu item added: " + item.getItemName());
        }
    }
    
    // READ
    public void displayMenu() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                        RESTAURANT MENU");
        System.out.println("=".repeat(70));
        
        displayMenuByCategory("Food");
        displayMenuByCategory("Beverage");
        
        System.out.println("=".repeat(70));
    }
    
    private void displayMenuByCategory(String category) {
        System.out.println("\n--- " + category.toUpperCase() + " ---");
        List<MenuEntry> filteredItems = menuItems.stream()
            .filter(item -> item.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
        
        for (int i = 0; i < filteredItems.size(); i++) {
            MenuEntry item = filteredItems.get(i);
            String availability = item.isAvailable() ? "✓" : "✗";
            System.out.println((i + 1) + ". " + availability + " " + item.getItemDetails());
        }
    }
    
    // UPDATE
    public boolean updateMenuItem(String itemName, double newPrice) {
        for (MenuEntry item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                item.setBasePrice(newPrice);
                System.out.println("✓ Price updated for: " + itemName);
                return true;
            }
        }
        System.out.println("✗ Item not found: " + itemName);
        return false;
    }
    
    public boolean toggleAvailability(String itemName) {
        for (MenuEntry item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                item.setAvailable(!item.isAvailable());
                String status = item.isAvailable() ? "Available" : "Unavailable";
                System.out.println("✓ " + itemName + " is now " + status);
                return true;
            }
        }
        return false;
    }
    
    // DELETE
    public boolean removeMenuItem(String itemName) {
        for (MenuEntry item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                menuItems.remove(item);
                System.out.println("✓ Removed from menu: " + itemName);
                return true;
            }
        }
        System.out.println("✗ Item not found: " + itemName);
        return false;
    }
    
    // Search functionality
    public MenuEntry findMenuItem(String itemName) {
        for (MenuEntry item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    
    public ArrayList<MenuEntry> getAllItems() {
        return new ArrayList<>(menuItems);
    }
    
    // Initialize sample menu
    private void initializeSampleMenu() {
        // Food items
        addMenuItem(new FoodEntry("Paneer Tikka", 250.0, "VEG", "INDIAN", 20));
        addMenuItem(new FoodEntry("Butter Chicken", 320.0, "NON-VEG", "INDIAN", 25));
        addMenuItem(new FoodEntry("Margherita Pizza", 280.0, "VEG", "ITALIAN", 15));
        addMenuItem(new FoodEntry("Hakka Noodles", 180.0, "VEG", "CHINESE", 15));
        addMenuItem(new FoodEntry("Grilled Salmon", 450.0, "NON-VEG", "CONTINENTAL", 30));
        addMenuItem(new FoodEntry("Dal Makhani", 200.0, "VEG", "INDIAN", 20));
        
        // Beverage items
        addMenuItem(new BeverageEntry("Cappuccino", 120.0, "SMALL", false, "HOT"));
        addMenuItem(new BeverageEntry("Fresh Lime Soda", 80.0, "MEDIUM", false, "COLD"));
        addMenuItem(new BeverageEntry("Mango Lassi", 100.0, "LARGE", false, "COLD"));
        addMenuItem(new BeverageEntry("Green Tea", 60.0, "SMALL", false, "HOT"));
        addMenuItem(new BeverageEntry("Cola", 50.0, "SMALL", false, "COLD"));
    }
}

// ==================== DineFlow.java (Main Application) ====================
/**
 * Main application class demonstrating MVC pattern
 * Orchestrates all components of the DineFlow
 */
public class DineFlow {
    private MenuManager menuManager;
    private ArrayList<OrderTicket> orders;
    private Scanner scanner;
    
    public DineFlow() {
        this.menuManager = new MenuManager();
        this.orders = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("          DineFlow");
        System.out.println("          Developed by: Aryan");
        System.out.println("=".repeat(70));
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    createNewOrder();
                    break;
                case 3:
                    viewAllOrders();
                    break;
                case 4:
                    updateOrderStatus();
                    break;
                case 5:
                    manageMenu();
                    break;
                case 6:
                    generateReports();
                    break;
                case 7:
                    running = false;
                    System.out.println("\nThank you for using DineFlow!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + "-".repeat(70));
        System.out.println("MAIN MENU:");
        System.out.println("1. View Menu");
        System.out.println("2. Create New Order");
        System.out.println("3. View All Orders");
        System.out.println("4. Update Order Status");
        System.out.println("5. Manage Menu (Admin)");
        System.out.println("6. Generate Reports");
        System.out.println("7. Exit");
        System.out.println("-".repeat(70));
    }
    
    private void viewMenu() {
        menuManager.displayMenu();
    }
    
    private void createNewOrder() {
        System.out.println("\n--- CREATE NEW ORDER ---");
        int tableNumber = getIntInput("Enter table number: ");
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        
        OrderTicket order = new OrderTicket(tableNumber, customerName);
        
        boolean addingItems = true;
        while (addingItems) {
            menuManager.displayMenu();
            System.out.print("\nEnter item name (or 'done' to finish): ");
            String itemName = scanner.nextLine();
            
            if (itemName.equalsIgnoreCase("done")) {
                addingItems = false;
            } else {
                MenuEntry item = menuManager.findMenuItem(itemName);
                if (item != null) {
                    order.addEntry(item);
                } else {
                    System.out.println("✗ Item not found in menu!");
                }
            }
        }
        
        if (!order.getItems().isEmpty()) {
            orders.add(order);
            order.displayOrder();
            System.out.println("\n✓ Order created successfully!");
        } else {
            System.out.println("\n✗ Order cancelled - no items added.");
        }
    }
    
    private void viewAllOrders() {
        System.out.println("\n--- ALL ORDERS ---");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (OrderTicket order : orders) {
                order.displayOrder();
            }
        }
    }
    
    private void updateOrderStatus() {
        System.out.println("\n--- UPDATE ORDER STATUS ---");
        int orderId = getIntInput("Enter Order ID: ");
        
        OrderTicket order = findOrderById(orderId);
        if (order != null) {
            System.out.println("Current Status: " + order.getStatus());
            System.out.println("1. PENDING  2. PREPARING  3. SERVED  4. BILLED");
            int statusChoice = getIntInput("Select new status: ");
            
            String[] statuses = {"PENDING", "PREPARING", "SERVED", "BILLED"};
            if (statusChoice >= 1 && statusChoice <= 4) {
                order.setStatus(statuses[statusChoice - 1]);
                System.out.println("✓ Order status updated to: " + statuses[statusChoice - 1]);
            }
        } else {
            System.out.println("✗ Order not found!");
        }
    }
    
    private void manageMenu() {
        System.out.println("\n--- MENU MANAGEMENT ---");
        System.out.println("1. Add Item");
        System.out.println("2. Update Price");
        System.out.println("3. Toggle Availability");
        System.out.println("4. Remove Item");
        System.out.println("5. Back");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1:
                addNewMenuItem();
                break;
            case 2:
                System.out.print("Enter item name: ");
                String updateItem = scanner.nextLine();
                double newPrice = getDoubleInput("Enter new price: ");
                menuManager.updateMenuItem(updateItem, newPrice);
                break;
            case 3:
                System.out.print("Enter item name: ");
                String toggleItem = scanner.nextLine();
                menuManager.toggleAvailability(toggleItem);
                break;
            case 4:
                System.out.print("Enter item name to remove: ");
                String removeItem = scanner.nextLine();
                menuManager.removeMenuItem(removeItem);
                break;
        }
    }
    
    private void addNewMenuItem() {
        System.out.println("1. Add Food Item  2. Add Beverage");
        int type = getIntInput("Select type: ");
        
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        double price = getDoubleInput("Enter price: ");
        
        if (type == 1) {
            System.out.print("Dietary type (VEG/NON-VEG/VEGAN): ");
            String dietary = scanner.nextLine();
            System.out.print("Cuisine (INDIAN/CHINESE/CONTINENTAL): ");
            String cuisine = scanner.nextLine();
            int prepTime = getIntInput("Preparation time (minutes): ");
            
            menuManager.addMenuItem(new FoodEntry(name, price, dietary, cuisine, prepTime));
        } else if (type == 2) {
            System.out.print("Serving size (SMALL/MEDIUM/LARGE): ");
            String size = scanner.nextLine();
            System.out.print("Is alcoholic? (true/false): ");
            boolean alcoholic = scanner.nextBoolean();
            scanner.nextLine();
            System.out.print("Temperature (HOT/COLD/ROOM): ");
            String temp = scanner.nextLine();
            
            menuManager.addMenuItem(new BeverageEntry(name, price, size, alcoholic, temp));
        }
    }
    
    private void generateReports() {
        System.out.println("\n--- REPORTS & ANALYTICS ---");
        System.out.printf("Total Orders: %d%n", orders.size());
        
        double totalRevenue = 0.0;
        for (OrderTicket order : orders) {
            totalRevenue += order.computeTotalWithTax();
        }
        System.out.printf("Total Revenue: ₹%.2f%n", totalRevenue);
        
        if (!orders.isEmpty()) {
            System.out.printf("Average Order Value: ₹%.2f%n", totalRevenue / orders.size());
        }
    }
    
    private OrderTicket findOrderById(int orderId) {
        for (OrderTicket order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
    
    // Main method
    public static void main(String[] args) {
        DineFlow system = new DineFlow();
        system.start();
    }
}
