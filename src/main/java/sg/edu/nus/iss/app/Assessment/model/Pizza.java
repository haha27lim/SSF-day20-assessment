package sg.edu.nus.iss.app.Assessment.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable {

    @NotNull(message="Pizza selection is required")
    private String pizzaSelection;

    @NotNull(message="Pizza size is required")
    private String pizzaSize;

    @Min(value=1, message="You must order at least 1")
    @Max(value=10, message="You can only maximum 10 pizzas")
    private int quantity;


    public String getPizzaSelection() {
        return pizzaSelection;
    }

    public void setPizzaSelection(String pizzaSelection) {
        this.pizzaSelection = pizzaSelection;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Static method to create a Pizza object from a JSON object.
    // The JSON object should have fields: "pizza" (selection), "size", and "quantity".
    public static Pizza create (JsonObject obj) {
        Pizza pizza = new Pizza(); // Create a new Pizza object.
        pizza.setPizzaSelection(obj.getString("pizza"));
        pizza.setPizzaSize(obj.getString("size"));
        pizza.setQuantity(obj.getInt("quantity"));
        return pizza;  // Return the newly created Pizza object.
    }
}
