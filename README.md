# 🍽️ DineFlow - Restaurant Management System

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![OOP](https://img.shields.io/badge/OOP-Principles-blue?style=for-the-badge)](https://github.com/codev-aryan/DineFlow)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)

A comprehensive restaurant management system built with Java, demonstrating core Object-Oriented Programming principles including **Inheritance**, **Polymorphism**, **Encapsulation**, and **Abstraction**. Features persistent data storage, analytics, and a complete order management workflow.

## 📋 Table of Contents

- [Features](#-features)
- [What's New](#-whats-new)
- [OOP Concepts Demonstrated](#-oop-concepts-demonstrated)
- [System Architecture](#-system-architecture)
- [Installation](#-installation)
- [Usage](#-usage)
- [Class Structure](#-class-structure)
- [Data Persistence](#-data-persistence)
- [Screenshots](#-screenshots)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)
- [Author](#-author)

## ✨ Features

### Core Functionality
- **Menu Management**: Complete CRUD operations for food and beverage items
- **Order Processing**: Create, track, and manage customer orders with special instructions
- **Real-time Status Updates**: Track order status from pending to billed
- **Dynamic Pricing**: Automatic price calculation based on item type and size
- **Discount System**: Apply percentage-based discounts to orders
- **Tax Calculation**: Built-in CGST and SGST calculation (2.5% each)
- **Reports & Analytics**: Revenue reports, order statistics, and table utilization
- **Search Functionality**: Quick search for menu items by name
- **Popularity Tracking**: View most ordered items
- **Bill Export**: Save bills to text files with UTF-8 encoding
- **Data Persistence**: All menu items and orders are automatically saved

### Menu Categories
- **Food Items**: Support for multiple cuisines (Indian, Chinese, Continental, Italian)
- **Beverages**: Hot and cold drinks with size variants (Small, Medium, Large)
- **Dietary Options**: VEG, NON-VEG, and VEGAN classifications
- **Spice Indicators**: Visual indicators for spicy food items 🌶️
- **Preparation Time**: Estimated cooking time for each dish

## 🆕 What's New

### Version 2.0 Improvements
- ✅ **Persistent Data Storage**: Orders and menu items are now saved to disk and persist across sessions
- ✅ **Order ID Management**: Fixed bug where order IDs would reset after restart
- ✅ **UTF-8 Encoding**: Rupee symbol (₹) and special characters now save correctly on all platforms
- ✅ **Enhanced Input Handling**: Fixed scanner issues preventing proper input reading
- ✅ **OrderManager Class**: New dedicated class for order persistence and management
- ✅ **Discount System**: Apply percentage-based discounts to orders
- ✅ **Special Instructions**: Add custom notes to orders
- ✅ **Popularity Tracking**: Track which items are ordered most frequently
- ✅ **Advanced Analytics**: Table utilization, completed vs pending orders
- ✅ **Search Feature**: Quick search for menu items
- ✅ **Better UI**: Improved formatting, emojis, and visual indicators
- ✅ **Bill Export**: Save formatted bills to text files

## 🎯 OOP Concepts Demonstrated

### 1. **Abstraction**
```java
abstract class MenuEntry {
    public abstract String getItemDetails();
}
```
- Abstract base class `MenuEntry` hides implementation details
- Defines common interface for all menu items
- Enforces contract for subclasses

### 2. **Inheritance**
```java
class FoodEntry extends MenuEntry { }
class BeverageEntry extends MenuEntry { }
```
- `FoodEntry` and `BeverageEntry` inherit from `MenuEntry`
- Code reusability through parent-child relationships
- Specialized attributes for each item type

### 3. **Polymorphism**
```java
@Override
public double calculatePrice() {
    // Different pricing logic for different item types
}
```
- Method overriding for specialized behavior
- Runtime polymorphism in price calculations
- Different implementations in Food vs Beverage classes

### 4. **Encapsulation**
```java
private String itemName;
public String getItemName() { return itemName; }
```
- Private fields with public getters/setters
- Data protection and controlled access
- Return defensive copies to prevent external modification

### 5. **Serialization**
```java
class MenuEntry implements Serializable {
    private static final long serialVersionUID = 1L;
}
```
- Implements Serializable for object persistence
- Enables saving and loading objects to/from files

## 🏗️ System Architecture

```
DineFlow
├── MenuEntry (Abstract)
│   ├── FoodEntry
│   └── BeverageEntry
├── OrderTicket
├── OrderManager (NEW)
├── MenuManager
└── DineFlow (Main Controller)
```

### Design Patterns Used
- **MVC Pattern**: Separation of concerns (Model-View-Controller)
- **Singleton Pattern**: Static order counter management
- **Manager Pattern**: OrderManager and MenuManager for data operations
- **Composition**: MenuManager composes MenuEntry objects
- **Data Protection**: Returning copies instead of references

## 🚀 Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, VS Code) or command line

### Steps

1. Clone the repository
```bash
git clone https://github.com/codev-aryan/DineFlow.git
cd DineFlow
```

2. Compile the program
```bash
javac DineFlow.java
```

3. Run the application
```bash
java DineFlow
```

## 💻 Usage

### Main Menu Options

```
1. 📖 View Menu          - Display all available items
2. ➕ Create New Order   - Place a new customer order
3. 📋 View All Orders    - See all orders with details
4. 🔄 Update Order Status - Change order status
5. ⚙️ Manage Menu (Admin) - Add/Update/Delete menu items
6. 📊 Generate Reports   - View revenue and analytics
7. 🔍 Search Menu Items  - Search for items by name
8. ⭐ View Popular Items - See most ordered items
9. 🚪 Exit              - Close the application
```

### Sample Workflow

**Creating an Order:**
1. Select option 2 from main menu
2. Enter table number and customer name
3. Browse menu and add items by name
4. Type 'done' when finished
5. Add special instructions (optional)
6. Apply discount percentage (optional)
7. View order summary with total bill
8. Optionally save bill to text file

**Managing Menu:**
1. Select option 5 from main menu
2. Add new items with detailed attributes (including spice level)
3. Update prices or toggle availability
4. Remove items from menu
5. All changes are automatically saved

**Viewing Analytics:**
1. Select option 6 from main menu
2. View total orders and revenue
3. See average order value
4. Check completed vs pending orders
5. View table utilization statistics

## 📊 Class Structure

### MenuEntry (Abstract)
- **Purpose**: Base class for all menu items
- **Key Fields**: `itemName`, `basePrice`, `category`, `isAvailable`, `popularity`
- **Abstract Method**: `getItemDetails()`
- **Features**: Serializable for persistence, popularity tracking

### FoodEntry
- **Purpose**: Represents food menu items
- **Additional Fields**: `dietaryType`, `cuisine`, `preparationTime`, `isSpicy`
- **Special Feature**: Cuisine-based price markup (10% for Continental)
- **Spice Indicator**: Visual 🌶️ indicator for spicy items

### BeverageEntry
- **Purpose**: Represents beverage menu items
- **Additional Fields**: `servingSize`, `isAlcoholic`, `temperature`
- **Special Feature**: Size-based dynamic pricing (Small: 1x, Medium: 1.25x, Large: 1.5x)

### OrderTicket
- **Purpose**: Manages customer orders
- **Key Features**: 
  - Auto-incrementing order IDs
  - Item collection management
  - Tax calculation (5% total GST)
  - Order status tracking
  - Special instructions support
  - Discount application
  - Bill export to text file with UTF-8 encoding

### OrderManager (NEW)
- **Purpose**: Manages order persistence and operations
- **Key Features**:
  - Saves orders to `orders_data.ser`
  - Loads orders on startup
  - Synchronizes order counter to prevent ID conflicts
  - Find orders by ID
- **Fixes**: Order persistence bug, ID reset issue

### MenuManager
- **Purpose**: CRUD operations for menu
- **Operations**: Create, Read, Update, Delete
- **Features**: 
  - Search and filter by category
  - Availability toggle
  - Persistent storage to `menu_data.ser`
  - Get most popular items
  - Automatic save on modifications

## 💾 Data Persistence

### Automatic Saving
- **Menu Data**: Saved to `menu_data.ser` on every modification
- **Order Data**: Saved to `orders_data.ser` when orders are created or updated
- **Bill Export**: Individual order bills saved as `order_[ID].txt`

### File Locations
```
DineFlow/
├── DineFlow.java
├── menu_data.ser         (Menu persistence)
├── orders_data.ser       (Order persistence)
└── order_1001.txt        (Exported bills)
```

### Data Recovery
- On startup, the system automatically loads saved menu and order data
- If files are missing or corrupted, the system starts with a fresh sample menu
- Order IDs are synchronized to prevent conflicts

## 📸 Screenshots

### Menu Display
```
=========================================================================
                        RESTAURANT MENU
=========================================================================

--- FOOD ---
1. ✓ Paneer Tikka | VEG | INDIAN | Prep: 20 min | ₹250.00 🌶️
2. ✓ Butter Chicken | NON-VEG | INDIAN | Prep: 25 min | ₹320.00 🌶️
3. ✓ Grilled Salmon | NON-VEG | CONTINENTAL | Prep: 30 min | ₹495.00

--- BEVERAGE ---
1. ✓ Cappuccino | SMALL | Non-Alcoholic | HOT | ₹120.00
2. ✓ Mango Lassi | LARGE | Non-Alcoholic | COLD | ₹150.00
=========================================================================
```

### Order Bill
```
=================================================================
                    RESTAURANT BILL
=================================================================
ORDER ID: #1001 | TABLE: 5
Customer: John Doe
Status: PENDING
Date & Time: 18-Nov-2025 14:30:45
-----------------------------------------------------------------
No. Item                           Price
-----------------------------------------------------------------
1   Paneer Tikka                   ₹   250.00
2   Mango Lassi                    ₹   150.00

Special Instructions: Extra spicy, no onions
-----------------------------------------------------------------
Subtotal:                                      ₹   400.00
Discount (10%):                               -₹    40.00
CGST (2.5%):                                   ₹     9.00
SGST (2.5%):                                   ₹     9.00
=================================================================
TOTAL AMOUNT:                                  ₹   378.00
=================================================================
           Thank you for dining with us! 😊
=================================================================
```

### Analytics Report
```
=========================================================================
📊 REPORTS & ANALYTICS
=========================================================================
📦 Total Orders: 25
💰 Total Revenue: ₹12,450.00
📈 Average Order Value: ₹498.00
✅ Completed Orders: 18
⏳ Pending Orders: 7

🪑 Table Utilization:
   Table 5: 6 orders
   Table 3: 5 orders
   Table 7: 4 orders
=========================================================================
```

## 🔮 Future Enhancements

- [ ] Database integration (MySQL/PostgreSQL)
- [ ] GUI implementation using JavaFX
- [ ] Payment gateway integration
- [ ] Kitchen Display System (KDS)
- [ ] Mobile app integration

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Aryan Mehta**
- GitHub: [@codev-aryan](https://github.com/codev-aryan)
- Project Link: [https://github.com/codev-aryan/DineFlow](https://github.com/codev-aryan/DineFlow)

---

**⭐ Star this repository if you found it helpful!**

Made with ❤️ by Aryan Mehta
