package sg.edu.nus.iss.app.Assessment.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import sg.edu.nus.iss.app.Assessment.model.DeliveryDetails;
import sg.edu.nus.iss.app.Assessment.model.Order;
import sg.edu.nus.iss.app.Assessment.model.Pizza;
import sg.edu.nus.iss.app.Assessment.repository.PizzaRepository;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository repository;

    public static final String[] PIZZA_NAMES = {
        "bella",
        "margherita",
        "marinara",
        "spianatacalabrese",
        "trioformaggio"
    };

    public static final String[] PIZZA_SIZES = {
        "sm",
        "md",
        "lg"
    };

    private final Set<String> pizzaNames;
    private final Set<String> pizzaSizes;


    // Constructor that initializes the pizzaNames and pizzaSizes sets
    public PizzaService() {
        pizzaNames = new HashSet<>(Arrays.asList(PIZZA_NAMES)); // Convert the array to a set for faster lookup
        pizzaSizes = new HashSet<>(Arrays.asList(PIZZA_SIZES)); // Same for pizza sizes
    }

    // Method to validate a pizza order and return any validation errors
    public List<ObjectError> validatePizzaOrder(Pizza pizza) { 
        List<ObjectError> errors = new LinkedList<>(); // List to store validation errors
        FieldError error;
        // Check if the pizza name is valid
        if (!pizzaNames.contains(pizza.getPizzaSelection().toLowerCase())) {
            error = new FieldError("pizza", "pizza", 
                "We do not have the following %s pizza"
                        .formatted(pizza.getPizzaSelection())); // Error message for invalid pizza name
            errors.add(error);
        }
        
        // Check if the pizza size is valid
        if(!pizzaSizes.contains(pizza.getPizzaSize().toLowerCase())) {
            error = new FieldError("pizza", "size", 
                "We do not have the following %s pizza size"
                        .formatted(pizza.getPizzaSize())); // Error message for invalid pizza size
            errors.add(error);
        }
        return errors; // Return the list of errors
    }

    // Method to fetch an order from the repository by orderId
    public Optional<Order> getOrderByOrderId(String OrderId) {
        return repository.get(OrderId);
    }

    // Method to create a new pizza order with a unique orderId
    public Order createPizzaOrder(Pizza pizza, DeliveryDetails delivery) {
        String orderId = UUID.randomUUID().toString().substring(0,8); // Generate an 8-character unique order ID
        Order order = new Order (pizza, delivery); // Create a new Order object
        order.setOrderId(orderId); // Set the generated orderId
        return order; // Return the created order
    }

    // Method to calculate the total cost of the order based on pizza type, size, and quantity
    private double calculateCost(Order order) {
        double total = 0d;

        // Calculate base cost based on pizza selection
        switch(order.getPizzaSelection()) {
            case "margherita":
                total += 22;
                break;
            case "trioformaggio":
                total +=25;
                break;
            case "bella", "marinara", "spianatacalabrese":
                total+=30;
                break;
        }

        // Adjust cost based on pizza size
        switch(order.getPizzaSize()) {
            case "sm":
                total *= 1;
                break;
            case "md":
                total *= 1.2;
                break;
            case "lg":
                total *=1.5;
                break;
        }

        // Multiply by the quantity ordered
        total *= order.getQuantity();

        // Add $2 for rush orders
        if (order.isRush())
            total += 2;

        // Set the total cost in the order
        order.setTotalcost(total);
        return total; // Return the calculated total
    }

    // Method to create, calculate cost, save, and return a pizza order
    public Order savePizzaOrder(Pizza pizza, DeliveryDetails delivery) {
        Order order = createPizzaOrder(pizza, delivery); // Create a new order
        calculateCost(order); // Calculate the cost of the order
        repository.save(order); // Save the order to the repository (Redis in this case)
        return order; // Return the saved order
    }
}

