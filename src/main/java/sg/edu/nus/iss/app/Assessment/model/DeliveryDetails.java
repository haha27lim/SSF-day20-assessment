package sg.edu.nus.iss.app.Assessment.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DeliveryDetails implements Serializable{

    @NotNull(message="Name is required")
    @Size(min=3, message= "Name must be at least 3 characters")
    @NotBlank(message ="Name is required")
    private String name;

    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Phone is required")
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{8}$", message = "Phone must be 8 digits")
    private String phone;

    private boolean rush = false;
    private String comments;

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

    public boolean isRush() {
        return rush;
    }

    public void setRush(boolean rush) {
        this.rush = rush;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    // Static method to create a DeliveryDetails object from a JSON object.
    // The JSON object should have fields: "name", "address", "phone", "rush", and "comments".
    public static DeliveryDetails create (JsonObject obj) {
        DeliveryDetails delivery = new DeliveryDetails(); // Create a new DeliveryDetails object.
        delivery.setName(obj.getString("name"));
        delivery.setAddress(obj.getString("address"));
        delivery.setPhone(obj.getString("phone"));
        delivery.setRush(obj.getBoolean("rush"));
        delivery.setComments(obj.getString("comments"));
        return delivery; // Return the newly created DeliveryDetails object.
    }

}
