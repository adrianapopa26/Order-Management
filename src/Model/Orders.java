package Model;

public class Orders {
    /**
     * Keeps the information requested for orders
     */
    private int ID;
    private int clientID;
    private int productID;
    private int quantity;
    /**
     * Constructor for Orders class
     */
    public Orders(int ID, int clientID, int productID, int quantity) {
        this.ID = ID;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }
    /**
     * Empty constructor needed for getting the instance
     */
    public Orders(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getClientID() {
        return clientID;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
