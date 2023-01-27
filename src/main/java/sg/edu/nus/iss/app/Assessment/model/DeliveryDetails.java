package sg.edu.nus.iss.app.Assessment.model;

import java.io.Serializable;
import java.util.Random;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DeliveryDetails implements Serializable {
    
    // The serial version UID is used for serialization.
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters")
    private String name;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{8}$", message = "Phone must be 8 digits")
    private String phone;

    private Boolean rush;

    private String comments;
    
    private String id;

    private int quantity;
    
    private double totalCost;


    public DeliveryDetails(
        String name, String address, String phone, Boolean rush, String comments, String id, int quantity, double totalCost) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rush = rush;
        this.comments = comments;
        this.id = id;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public DeliveryDetails() {
        // Generate a random ID for the new contact.
        this.id = this.generateId(8);
    }

    private synchronized String generateId(int numChars) {
        // Create a new random number generator
        Random r = new Random();
        // Create a new string builder
        StringBuilder sb = new StringBuilder();
        // Keep generating random hexadecimal strings until the desired number of characters is reached
        while (sb.length() < numChars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        // Return the generated ID
        return sb.toString().substring(0, numChars);
    }

     // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getRush() {
        return rush;
    }

    public void setRush(Boolean rush) {
        this.rush = rush;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


}
