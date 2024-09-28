package sg.edu.nus.iss.app.Assessment.model;

import java.io.Serializable;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L; // For serialization version control
    
    private String orderId;
    private double totalcost = 0;
    private Pizza pizza;
    private DeliveryDetails delivery;

    
    public Order(Pizza pizza, DeliveryDetails delivery) {
        this.pizza = pizza;
        this.delivery = delivery;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public double getTotalcost() {
        return totalcost;
    }


    public void setTotalcost(double totalcost) {
        this.totalcost = totalcost;
    }


    public Pizza getPizza() {
        return pizza;
    }


    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }


    public DeliveryDetails getDelivery() {
        return delivery;
    }


    public void setDelivery(DeliveryDetails delivery) {
        this.delivery = delivery;
    }
    
    // this.pizza refers to a Pizza object, and calling the getPizza() method of that Pizza object to return the pizza type
    public String getPizzaSelection() {return this.pizza.getPizzaSelection();}
    public String getPizzaSize() {return this.pizza.getPizzaSize();}
    public int getQuantity() {return this.pizza.getQuantity();}

    public String getName() {return this.delivery.getName();}
    public String getAddress() {return this.delivery.getAddress();}
    public String getPhone() {return this.delivery.getPhone();}
    public boolean isRush() {return this.delivery.isRush();}
    public String getComments() {return this.delivery.getComments();}

    
    // Method to calculate the cost of the pizza. If the order is rush, reduce the cost by $2.
    public double getPizzaCost() {
        return isRush()? getTotalcost() - 2: getTotalcost(); // Rush orders get a $2 discount
    }

    // Converts a JSON string into a JsonObject for internal processing
    public static JsonObject toJSON(String str) {
        JsonReader reader = Json.createReader(new StringReader(str)); // Create a JSON reader
        return reader.readObject(); // Return the parsed JsonObject
    }

    // Static factory method to create an Order object from a JSON string
    public static Order create(String str) {
        JsonObject json = toJSON(str); // Convert string to JsonObject
        Pizza pizza = Pizza.create(json); // Create a Pizza object from the JSON
        DeliveryDetails delivery = DeliveryDetails.create(json); // Create DeliveryDetails from the JSON
        Order order = new Order(pizza, delivery); // Create the Order with the pizza and delivery details
        order.setOrderId(json.getString("orderId"));
        order.setTotalcost(json.getJsonNumber("total").doubleValue());
        return order; // Return the newly created Order object
    }

    // Serialises an Order object into a JsonObject, converting the Order object's data into a JSON format)
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("orderId", orderId) // Adds the orderId to the JSON
            .add("name", getName())
            .add("address",getAddress())
            .add("phone", getPhone())
            .add("rush", isRush())
            .add("comments", getComments())
            .add("pizza", getPizzaSelection())
            .add("size", getPizzaSize())
            .add("quantity", getQuantity())
            .add("total", totalcost)
            .build(); // Builds and returns the JsonObject
    }

}
