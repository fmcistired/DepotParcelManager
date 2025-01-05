package model;

public class Customer {

    private String id;           // Unique identifier for the customer
    private String name;      // Customer's name

    // Constructor
    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString method for a human-readable representation
    @Override
    public String toString() {
        return  "Customer ==========" + '\n' +
                "Customer Id: " + id + '\n' +
                "Customer Name: " + name + '\n';

    }
}
