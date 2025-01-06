package model;

public class Customer {

    private String name;
    private String id;

    // Constructor
    public Customer(String name, String id) {
        this.id = id.trim();
        this.name = name.trim();
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
        return  "Customer Name: " + name + '\n' +
                " Customer Id: " + id + '\n';

    }
}
