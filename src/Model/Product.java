package Model;

public class Product {
    /**
     * Keeps the information requested for products
     */
    private int ID;
    private String name;
    private double price;
    private int Stock;
    /**
     * Constructor for Product class
     */
    public Product(int ID, String name, double price, int inStock) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.Stock = inStock;
    }
    /**
     * Empty constructor needed for getting the instance
     */
    public Product(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int inStock) {
        this.Stock = inStock;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}