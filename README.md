# 🍽️ DineFlow - Restaurant Management System

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![OOP](https://img.shields.io/badge/OOP-Principles-blue?style=for-the-badge)](https://github.com/codev-aryan/DineFlow)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)

A comprehensive restaurant management system built with Java, demonstrating core Object-Oriented Programming principles including **Inheritance**, **Polymorphism**, **Encapsulation**, and **Abstraction**.

## 📋 Table of Contents

- [Features](#-features)
- [OOP Concepts Demonstrated](#-oop-concepts-demonstrated)
- [System Architecture](#-system-architecture)
- [Installation](#-installation)
- [Usage](#-usage)
- [Class Structure](#-class-structure)
- [Screenshots](#-screenshots)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)
- [Author](#-author)

## ✨ Features

### Core Functionality
- **Menu Management**: Complete CRUD operations for food and beverage items
- **Order Processing**: Create, track, and manage customer orders
- **Real-time Status Updates**: Track order status from pending to billed
- **Dynamic Pricing**: Automatic price calculation based on item type and size
- **Tax Calculation**: Built-in CGST and SGST calculation (2.5% each)
- **Reports & Analytics**: Generate revenue reports and order statistics
- **Inventory Tracking**: Toggle item availability in real-time

### Menu Categories
- **Food Items**: Support for multiple cuisines (Indian, Chinese, Continental, Italian)
- **Beverages**: Hot and cold drinks with size variants
- **Dietary Options**: VEG, NON-VEG, and VEGAN classifications
- **Preparation Time**: Estimated cooking time for each dish

## 🎯 OOP Concepts Demonstrated

### 1. **Abstraction**
```java
abstract class MenuEntry {
    public abstract String getItemDetails();
}
```
- Abstract base class `MenuEntry` hides implementation details
- Defines common interface for all menu items

### 2. **Inheritance**
```java
class FoodEntry extends MenuEntry { }
class BeverageEntry extends MenuEntry { }
```
- `FoodEntry` and `BeverageEntry` inherit from `MenuEntry`
- Code reusability through parent-child relationships

### 3. **Polymorphism**
```java
@Override
public double calculatePrice() {
    // Different pricing logic for different item types
}
```
- Method overriding for specialized behavior
- Runtime polymorphism in price calculations

### 4. **Encapsulation**
```java
private String itemName;
public String getItemName() { return itemName; }
```
- Private fields with public getters/setters
- Data protection and controlled access

## 🏗️ System Architecture

```
DineFlow
├── MenuEntry (Abstract)
│   ├── FoodEntry
│   └── BeverageEntry
├── OrderTicket
├── MenuManager
└── DineFlow (Main Controller)
```

### Design Patterns Used
- **MVC Pattern**: Separation of concerns (Model-View-Controller)
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
1. View Menu          - Display all available items
2. Create New Order   - Place a new customer order
3. View All Orders    - See all orders with details
4. Update Order Status - Change order status (Pending/Preparing/Served/Billed)
5. Manage Menu (Admin) - Add/Update/Delete menu items
6. Generate Reports   - View revenue and analytics
7. Exit              - Close the application
```

### Sample Workflow

**Creating an Order:**
1. Select option 2 from main menu
2. Enter table number and customer name
3. Browse menu and add items by name
4. Type 'done' when finished
5. View order summary with total bill

**Managing Menu:**
1. Select option 5 from main menu
2. Add new items with detailed attributes
3. Update prices or toggle availability
4. Remove items from menu

## 📊 Class Structure

### MenuEntry (Abstract)
- **Purpose**: Base class for all menu items
- **Key Fields**: `itemName`, `basePrice`, `category`, `isAvailable`
- **Abstract Method**: `getItemDetails()`

### FoodEntry
- **Purpose**: Represents food menu items
- **Additional Fields**: `dietaryType`, `cuisine`, `preparationTime`
- **Special Feature**: Cuisine-based price markup

### BeverageEntry
- **Purpose**: Represents beverage menu items
- **Additional Fields**: `servingSize`, `isAlcoholic`, `temperature`
- **Special Feature**: Size-based dynamic pricing

### OrderTicket
- **Purpose**: Manages customer orders
- **Key Features**: 
  - Auto-incrementing order IDs
  - Item collection management
  - Tax calculation (5% total GST)
  - Order status tracking

### MenuManager
- **Purpose**: CRUD operations for menu
- **Operations**: Create, Read, Update, Delete
- **Features**: Search, filter by category, availability toggle

## 📸 Screenshots

```
==================================================================
                    RESTAURANT MENU
==================================================================

--- FOOD ---
1. ✓ Paneer Tikka | VEG | INDIAN Cuisine | Prep: 20 mins | ₹250.00
2. ✓ Butter Chicken | NON-VEG | INDIAN Cuisine | Prep: 25 mins | ₹320.00
3. ✓ Grilled Salmon | NON-VEG | CONTINENTAL Cuisine | Prep: 30 mins | ₹495.00

--- BEVERAGE ---
1. ✓ Cappuccino | SMALL | Non-Alcoholic | HOT | ₹120.00
2. ✓ Mango Lassi | LARGE | Non-Alcoholic | COLD | ₹150.00
==================================================================
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
