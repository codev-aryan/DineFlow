// ==================== DineFlow.java ====================
// Developed By: Aryan Mehta
// Status: FIXED & OPTIMIZED
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.nio.charset.StandardCharsets;

// ==================== MenuEntry.java (Abstract Base Class) ====================
abstract class MenuEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String itemName;
    private double basePrice;
    private String category;
    private boolean isAvailable;
    private int popularity;

    public MenuEntry(String itemName, double basePrice, String category) {
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.category = category;
        this.isAvailable = true;
        this.popularity = 0;
    }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) {
        if (basePrice >= 0) this.basePrice = basePrice;
    }

    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public int getPopularity() { return popularity; }
    public void incrementPopularity() { this.popularity++; }

    public abstract String getItemDetails();

    public double calculatePrice() { return basePrice; }

    @Override
    public String toString() {
        return itemName + " - ₹" + String.format("%.2f", basePrice);
    }
}

// ==================== FoodEntry.java ====================
class FoodEntry extends MenuEntry {
    private static final long serialVersionUID = 1L;

    private String dietaryType;
    private String cuisine;
    private int preparationTime;
    private boolean isSpicy;

    public FoodEntry(String itemName, double basePrice, String dietaryType,
                     String cuisine, int preparationTime, boolean isSpicy) {
        super(itemName, basePrice, "Food");
        this.dietaryType = dietaryType;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
        this.isSpicy = isSpicy;
    }

    // Method Overriding
    @Override
    public String getItemDetails() {
        String spicyIndicator = isSpicy ? " 🌶️" : "";
        return String.format("%s | %s | %s | Prep: %d min | ₹%.2f%s",
                getItemName(), dietaryType, cuisine, preparationTime, getBasePrice(), spicyIndicator);
    }

    @Override
    public double calculatePrice() {
        // Example: Premium cuisine has 10% markup
        if (cuisine.equalsIgnoreCase("CONTINENTAL")) {
            return getBasePrice() * 1.10;
        }
        return getBasePrice();
    }
}

// ==================== BeverageEntry.java ====================
class BeverageEntry extends MenuEntry {
    private static final long serialVersionUID = 1L;

    private String servingSize;
    private boolean isAlcoholic;
    private String temperature;

    public BeverageEntry(String itemName, double basePrice, String servingSize,
                        boolean isAlcoholic, String temperature) {
        super(itemName, basePrice, "Beverage");
        this.servingSize = servingSize;
        this.isAlcoholic = isAlcoholic;
        this.temperature = temperature;
    }

    @Override
    public String getItemDetails() {
        String type = isAlcoholic ? "Alcoholic" : "Non-Alcoholic";
        return String.format("%s | %s | %s | %s | ₹%.2f",
                getItemName(), servingSize, type, temperature, calculatePrice());
    }

    @Override
    public double calculatePrice() {
        double price = getBasePrice();
        switch (servingSize.toUpperCase()) {
            case "MEDIUM": return price * 1.25;
            case "LARGE": return price * 1.50;
            default: return price;
        }
    }
}

// ==================== OrderTicket.java ====================
class OrderTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    // Changed from private to protected so OrderManager can adjust it
    private static int orderCounter = 1000;

    private int orderId;
    private int tableNumber;
    private ArrayList<MenuEntry> items;
    private Date orderTime;
    private String status;
    private String customerName;
    private String specialInstructions;
    private double discount;

    public static synchronized int getNextOrderId() {
        return ++orderCounter;
    }

    public OrderTicket(int tableNumber, String customerName) {
        this.orderId = getNextOrderId();
        this.tableNumber = tableNumber;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.orderTime = new Date();
        this.status = "PENDING";
        this.specialInstructions = "";
        this.discount = 0.0;
    }

    // Helper to reset counter based on loaded data
    public static void setCounter(int count) {
        orderCounter = count;
    }

    public void addEntry(MenuEntry item) {
        if (item != null && item.isAvailable()) {
            items.add(item);
            item.incrementPopularity();
            System.out.println("✓ Added: " + item.getItemName());
        } else {
            System.out.println("✗ Item not available or invalid");
        }
    }

    public void setSpecialInstructions(String instructions) {
        this.specialInstructions = instructions;
    }

    public void applyDiscount(double discountPercent) {
        if (discountPercent >= 0 && discountPercent <= 100) {
            this.discount = discountPercent;
            System.out.printf("✓ %.0f%% discount applied!%n", discountPercent);
        }
    }

    public double computeTotal() {
        double total = 0.0;
        for (MenuEntry item : items) {
            total += item.calculatePrice();
        }
        return total;
    }

    public double computeTotalWithTax() {
        double subtotal = computeTotal();
        double discountAmount = subtotal * (discount / 100);
        double afterDiscount = subtotal - discountAmount;
        double cgst = afterDiscount * 0.025;
        double sgst = afterDiscount * 0.025;
        return afterDiscount + cgst + sgst;
    }

    public int getOrderId() { return orderId; }
    public int getTableNumber() { return tableNumber; }
    public String getCustomerName() { return customerName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public ArrayList<MenuEntry> getItems() { return new ArrayList<>(items); }

    public void displayOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

        System.out.println("\n" + "=".repeat(65));
        System.out.println("                    RESTAURANT BILL");
        System.out.println("=".repeat(65));
        System.out.println("ORDER ID: #" + orderId + " | TABLE: " + tableNumber);
        System.out.println("Customer: " + customerName);
        System.out.println("Status: " + status);
        System.out.println("Date & Time: " + sdf.format(orderTime));
        System.out.println("-".repeat(65));

        if (items.isEmpty()) {
            System.out.println("No items in order");
        } else {
            System.out.printf("%-3s %-30s %10s%n", "No.", "Item", "Price");
            System.out.println("-".repeat(65));
            for (int i = 0; i < items.size(); i++) {
                MenuEntry item = items.get(i);
                System.out.printf("%-3d %-30s ₹%9.2f%n",
                    (i + 1), item.getItemName(), item.calculatePrice());
            }
        }

        if (!specialInstructions.isEmpty()) {
            System.out.println("\nSpecial Instructions: " + specialInstructions);
        }

        System.out.println("-".repeat(65));
        double subtotal = computeTotal();
        System.out.printf("%-42s ₹%9.2f%n", "Subtotal:", subtotal);

        if (discount > 0) {
            double discountAmount = subtotal * (discount / 100);
            System.out.printf("%-42s -₹%9.2f%n",
                String.format("Discount (%.0f%%):", discount), discountAmount);
            subtotal -= discountAmount;
        }

        System.out.printf("%-42s ₹%9.2f%n", "CGST (2.5%):", subtotal * 0.025);
        System.out.printf("%-42s ₹%9.2f%n", "SGST (2.5%):", subtotal * 0.025);
        System.out.println("=".repeat(65));
        System.out.printf("%-42s ₹%9.2f%n", "TOTAL AMOUNT:", computeTotalWithTax());
        System.out.println("=".repeat(65));
        System.out.println("           Thank you for dining with us! 😊");
        System.out.println("=".repeat(65));
    }

    // FIX: Use UTF-8 Encoding so '₹' symbol saves correctly on Windows
    public void saveToFile() {
        String filename = "order_" + orderId + ".txt";
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            writer.println("=".repeat(65));
            writer.println("                    RESTAURANT BILL");
            writer.println("=".repeat(65));
            writer.println("ORDER ID: #" + orderId + " | TABLE: " + tableNumber);
            writer.println("Customer: " + customerName);
            writer.println("Date & Time: " + sdf.format(orderTime));
            writer.println("-".repeat(65));

            for (int i = 0; i < items.size(); i++) {
                MenuEntry item = items.get(i);
                writer.printf("%-3d %-30s ₹%9.2f%n",
                    (i + 1), item.getItemName(), item.calculatePrice());
            }

            writer.println("-".repeat(65));
            writer.printf("TOTAL: ₹%.2f%n", computeTotalWithTax());
            writer.println("=".repeat(65));
            System.out.println("✓ Bill saved to " + filename);
        } catch (IOException e) {
            System.out.println("✗ Error saving bill: " + e.getMessage());
        }
    }
}

// ==================== OrderManager.java (NEW: Handles Persistence) ====================
/**
 * NEW CLASS
 * Manages the list of orders and ensures they are saved to disk.
 * Fixes the bug where orders were lost after restart.
 */
class OrderManager {
    private ArrayList<OrderTicket> orders;
    private static final String ORDER_FILE = "orders_data.ser";

    public OrderManager() {
        this.orders = new ArrayList<>();
        loadOrders();
        syncOrderCounter();
    }

    public void addOrder(OrderTicket order) {
        orders.add(order);
        saveOrders();
    }

    public ArrayList<OrderTicket> getAllOrders() {
        return orders;
    }

    public OrderTicket findOrderById(int id) {
        for(OrderTicket o : orders) {
            if(o.getOrderId() == id) return o;
        }
        return null;
    }

    public void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER_FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            System.out.println("Note: Could not save order history.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadOrders() {
        File f = new File(ORDER_FILE);
        if(!f.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            orders = (ArrayList<OrderTicket>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Note: Starting with fresh order history.");
        }
    }

    // Fix for ID Reset Bug: Finds the highest ID and sets the counter
    private void syncOrderCounter() {
        if(!orders.isEmpty()) {
            int maxId = orders.stream()
                              .mapToInt(OrderTicket::getOrderId)
                              .max()
                              .orElse(1000);
            OrderTicket.setCounter(maxId);
        }
    }
}

// ==================== MenuManager.java ====================
class MenuManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<MenuEntry> menuItems;
    private static final String MENU_FILE = "menu_data.ser";

    public MenuManager() {
        this.menuItems = new ArrayList<>();
        if (!loadMenu()) {
            initializeSampleMenu();
        }
    }

    public void addMenuItem(MenuEntry item) {
        if (item != null) {
            menuItems.add(item);
            System.out.println("✓ Menu item added: " + item.getItemName());
            saveMenu();
        }
    }

    public void displayMenu() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("                        RESTAURANT MENU");
        System.out.println("=".repeat(75));

        displayMenuByCategory("Food");
        displayMenuByCategory("Beverage");

        System.out.println("=".repeat(75));
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

    public boolean updateMenuItem(String itemName, double newPrice) {
        for (MenuEntry item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                item.setBasePrice(newPrice);
                System.out.println("✓ Price updated for: " + itemName);
                saveMenu();
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
                saveMenu();
                return true;
            }
        }
        return false;
    }

    public boolean removeMenuItem(String itemName) {
        for (MenuEntry item : menuItems) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                menuItems.remove(item);
                System.out.println("✓ Removed from menu: " + itemName);
                saveMenu();
                return true;
            }
        }
        System.out.println("✗ Item not found: " + itemName);
        return false;
    }

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

    public List<MenuEntry> getMostPopularItems(int count) {
        return menuItems.stream()
            .sorted((a, b) -> Integer.compare(b.getPopularity(), a.getPopularity()))
            .limit(count)
            .collect(Collectors.toList());
    }

    private void saveMenu() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(MENU_FILE))) {
            oos.writeObject(menuItems);
        } catch (IOException e) {
            System.out.println("Note: Menu changes not persisted");
        }
    }

    @SuppressWarnings("unchecked")
    private boolean loadMenu() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(MENU_FILE))) {
            menuItems = (ArrayList<MenuEntry>) ois.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    private void initializeSampleMenu() {
        addMenuItem(new FoodEntry("Paneer Tikka", 250.0, "VEG", "INDIAN", 20, true));
        addMenuItem(new FoodEntry("Butter Chicken", 320.0, "NON-VEG", "INDIAN", 25, true));
        addMenuItem(new FoodEntry("Margherita Pizza", 280.0, "VEG", "ITALIAN", 15, false));
        addMenuItem(new FoodEntry("Hakka Noodles", 180.0, "VEG", "CHINESE", 15, false));
        addMenuItem(new FoodEntry("Grilled Salmon", 450.0, "NON-VEG", "CONTINENTAL", 30, false));
        addMenuItem(new FoodEntry("Dal Makhani", 200.0, "VEG", "INDIAN", 20, false));
        addMenuItem(new FoodEntry("Chicken Biryani", 280.0, "NON-VEG", "INDIAN", 30, true));
        addMenuItem(new FoodEntry("Veg Spring Rolls", 150.0, "VEG", "CHINESE", 12, false));
        addMenuItem(new BeverageEntry("Cappuccino", 120.0, "SMALL", false, "HOT"));
        addMenuItem(new BeverageEntry("Fresh Lime Soda", 80.0, "MEDIUM", false, "COLD"));
        addMenuItem(new BeverageEntry("Mango Lassi", 100.0, "LARGE", false, "COLD"));
    }
}

// ==================== DineFlow.java (Main Application) ====================
public class DineFlow {
    private MenuManager menuManager;
    private OrderManager orderManager; // Changed from ArrayList to Manager
    private Scanner scanner;

    public DineFlow() {
        this.menuManager = new MenuManager();
        this.orderManager = new OrderManager(); // Initialize Manager
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        clearScreen();
        System.out.println("\n" + "=".repeat(75));
        System.out.println("          🍽️  DineFlow  🍽️");
        System.out.println("          Developed by: Aryan");
        System.out.println("=".repeat(75));

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1: viewMenu(); break;
                case 2: createNewOrder(); break;
                case 3: viewAllOrders(); break;
                case 4: updateOrderStatus(); break;
                case 5: manageMenu(); break;
                case 6: generateReports(); break;
                case 7: searchMenu(); break;
                case 8: viewPopularItems(); break;
                case 9:
                    running = false;
                    System.out.println("\n" + "=".repeat(75));
                    System.out.println("   Thank you for using DineFlow! 👋");
                    System.out.println("=".repeat(75));
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }

            if (running && choice != 2) {
                pressEnterToContinue();
            }
        }
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n" + "-".repeat(75));
        System.out.println("📋 MAIN MENU:");
        System.out.println("1. 📖 View Menu");
        System.out.println("2. ➕ Create New Order");
        System.out.println("3. 📝 View All Orders");
        System.out.println("4. 🔄 Update Order Status");
        System.out.println("5. ⚙️  Manage Menu (Admin)");
        System.out.println("6. 📊 Generate Reports");
        System.out.println("7. 🔍 Search Menu Items");
        System.out.println("8. ⭐ View Popular Items");
        System.out.println("9. 🚪 Exit");
        System.out.println("-".repeat(75));
    }

    private void viewMenu() {
        menuManager.displayMenu();
    }

    private void createNewOrder() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("➕ CREATE NEW ORDER");
        System.out.println("=".repeat(75));

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
                    System.out.println("❌ Item not found in menu!");
                }
            }
        }

        if (!order.getItems().isEmpty()) {
            System.out.print("\nAny special instructions? (press Enter to skip): ");
            String instructions = scanner.nextLine();
            if (!instructions.isEmpty()) {
                order.setSpecialInstructions(instructions);
            }

            double discount = getDoubleInput("Apply discount? Enter % (0 for none): ");
            if (discount > 0) {
                order.applyDiscount(discount);
            }

            // Save to OrderManager (Persistence)
            orderManager.addOrder(order);
            order.displayOrder();

            System.out.print("\n💾 Save bill to text file? (y/n): ");
            String saveBill = scanner.nextLine();
            if (saveBill.equalsIgnoreCase("y")) {
                order.saveToFile();
            }

            System.out.println("\n✅ Order created successfully!");
        } else {
            System.out.println("\n❌ Order cancelled - no items added.");
            // Restore counter if cancelled (Optional logic, but good for gaps)
            // OrderTicket.setCounter(OrderTicket.orderCounter - 1);
        }

        pressEnterToContinue();
    }

    private void viewAllOrders() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("📝 ALL ORDERS");
        System.out.println("=".repeat(75));

        ArrayList<OrderTicket> orders = orderManager.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.printf("%-10s %-15s %-10s %-15s %-10s%n",
                "Order ID", "Customer", "Table", "Status", "Total");
            System.out.println("-".repeat(75));

            for (OrderTicket order : orders) {
                System.out.printf("#%-9d %-15s %-10d %-15s ₹%.2f%n",
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getTableNumber(),
                    order.getStatus(),
                    order.computeTotalWithTax());
            }
        }
    }

    private void updateOrderStatus() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("🔄 UPDATE ORDER STATUS");
        System.out.println("=".repeat(75));

        int orderId = getIntInput("Enter Order ID: ");

        OrderTicket order = orderManager.findOrderById(orderId);
        if (order != null) {
            System.out.println("Current Status: " + order.getStatus());
            System.out.println("\n1. PENDING  2. PREPARING  3. SERVED  4. BILLED");
            int statusChoice = getIntInput("Select new status: ");

            String[] statuses = {"PENDING", "PREPARING", "SERVED", "BILLED"};
            if (statusChoice >= 1 && statusChoice <= 4) {
                order.setStatus(statuses[statusChoice - 1]);
                // Save changes to file
                orderManager.saveOrders();
                System.out.println("✅ Order status updated to: " + statuses[statusChoice - 1]);
            }
        } else {
            System.out.println("❌ Order not found!");
        }
    }

    private void manageMenu() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("⚙️  MENU MANAGEMENT");
        System.out.println("=".repeat(75));
        System.out.println("1. ➕ Add Item");
        System.out.println("2. 💰 Update Price");
        System.out.println("3. 🔄 Toggle Availability");
        System.out.println("4. 🗑️  Remove Item");
        System.out.println("5. ⬅️  Back");

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
        System.out.println("\n1. Add Food Item  2. Add Beverage");
        int type = getIntInput("Select type: ");

        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        double price = getDoubleInput("Enter price: ");

        if (type == 1) {
            System.out.print("Dietary type (VEG/NON-VEG/VEGAN): ");
            String dietary = scanner.nextLine();
            System.out.print("Cuisine (INDIAN/CHINESE/CONTINENTAL/ITALIAN): ");
            String cuisine = scanner.nextLine();
            int prepTime = getIntInput("Preparation time (minutes): ");
            System.out.print("Is spicy? (y/n): ");
            boolean spicy = scanner.nextLine().equalsIgnoreCase("y");

            menuManager.addMenuItem(new FoodEntry(name, price, dietary, cuisine, prepTime, spicy));
        } else if (type == 2) {
            System.out.print("Serving size (SMALL/MEDIUM/LARGE): ");
            String size = scanner.nextLine();
            System.out.print("Is alcoholic? (y/n): ");
            boolean alcoholic = scanner.nextLine().equalsIgnoreCase("y");
            System.out.print("Temperature (HOT/COLD/ROOM): ");
            String temp = scanner.nextLine();

            menuManager.addMenuItem(new BeverageEntry(name, price, size, alcoholic, temp));
        }
    }

    private void generateReports() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("📊 REPORTS & ANALYTICS");
        System.out.println("=".repeat(75));

        ArrayList<OrderTicket> orders = orderManager.getAllOrders();

        System.out.printf("📦 Total Orders: %d%n", orders.size());

        double totalRevenue = 0.0;
        int pendingOrders = 0;
        int completedOrders = 0;

        for (OrderTicket order : orders) {
            totalRevenue += order.computeTotalWithTax();
            if (order.getStatus().equals("BILLED")) {
                completedOrders++;
            } else {
                pendingOrders++;
            }
        }

        System.out.printf("💰 Total Revenue: ₹%.2f%n", totalRevenue);

        if (!orders.isEmpty()) {
            System.out.printf("📈 Average Order Value: ₹%.2f%n", totalRevenue / orders.size());
        }

        System.out.printf("✅ Completed Orders: %d%n", completedOrders);
        System.out.printf("⏳ Pending Orders: %d%n", pendingOrders);

        // Table utilization
        Map<Integer, Integer> tableOrders = new HashMap<>();
        for (OrderTicket order : orders) {
            tableOrders.put(order.getTableNumber(),
                tableOrders.getOrDefault(order.getTableNumber(), 0) + 1);
        }

        if (!tableOrders.isEmpty()) {
            System.out.println("\n🪑 Table Utilization:");
            tableOrders.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry ->
                    System.out.printf("   Table %d: %d orders%n", entry.getKey(), entry.getValue())
                );
        }

        System.out.println("=".repeat(75));
    }

    private void searchMenu() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("🔍 SEARCH MENU");
        System.out.println("=".repeat(75));

        System.out.print("Enter item name to search: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        List<MenuEntry> results = menuManager.getAllItems().stream()
            .filter(item -> item.getItemName().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("❌ No items found matching: " + searchTerm);
        } else {
            System.out.println("\n✅ Found " + results.size() + " item(s):");
            System.out.println("-".repeat(75));
            for (MenuEntry item : results) {
                System.out.println("• " + item.getItemDetails());
            }
        }
    }

    private void viewPopularItems() {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("⭐ MOST POPULAR ITEMS");
        System.out.println("=".repeat(75));

        List<MenuEntry> popularItems = menuManager.getMostPopularItems(5);

        if (popularItems.isEmpty() || popularItems.get(0).getPopularity() == 0) {
            System.out.println("No popularity data available yet. Create some orders first!");
        } else {
            System.out.printf("%-5s %-30s %-12s %s%n", "Rank", "Item", "Orders", "Category");
            System.out.println("-".repeat(75));

            int rank = 1;
            for (MenuEntry item : popularItems) {
                if (item.getPopularity() > 0) {
                    System.out.printf("%-5d %-30s %-12d %s%n",
                        rank++,
                        item.getItemName(),
                        item.getPopularity(),
                        item.getCategory());
                }
            }
        }
    }

    // FIXED: Better input handling to avoid skipping inputs
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            }
        }
    }

    // FIXED: Better input handling
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                if (!prompt.isEmpty()) {
                    System.out.print(prompt);
                }
                String input = scanner.nextLine();
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }

    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    public static void main(String[] args) {
        DineFlow system = new DineFlow();
        system.start();
    }
}
