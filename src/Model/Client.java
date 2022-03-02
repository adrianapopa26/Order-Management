package Model;

public class Client {
    /**
     * Keeps the information requested for clients
     */
    private int ID;
    private String name;
    private String email;
    private String phoneNumber;
    /**
     * Constructor for Client class
     */
    public Client(int ID, String name, String email, String phoneNumber) {

        this.ID = ID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    /**
     * Empty constructor needed for getting the instance
     */
    public Client(){}

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

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
