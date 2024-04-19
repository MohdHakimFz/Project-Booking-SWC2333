package db_objs;

// Class representing menu items in the database
public class Menu {
    
    // Fields to store menu item details
    private int menuId;         // Unique identifier for the menu item
    private String menuName;    // Name of the menu item
    private double menuPrice;   // Price of the menu item
    private int menuStock;      // Stock quantity of the menu item
    private int userId;         // ID of the user associated with the menu item
    private int tableId;        // ID of the table associated with the menu item

    // Constructor to initialize menu item details
    public Menu(int menuId, String menuName, double menuPrice, int menuStock, int userId, int tableId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuStock = menuStock;
        this.userId = userId;
        this.tableId = tableId;
    }

    // Getter and setter methods for accessing and modifying menu item details

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getMenuStock() {
        return menuStock;
    }

    public void setMenuStock(int menuStock) {
        this.menuStock = menuStock;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
