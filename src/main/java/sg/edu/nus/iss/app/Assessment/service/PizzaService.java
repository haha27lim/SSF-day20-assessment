package sg.edu.nus.iss.app.Assessment.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.Assessment.model.PizzaOrder;

@Service
public class PizzaService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<String> validateOrder(PizzaOrder pizzaOrder) {
        List<String> errors = new ArrayList<>();

        // Check that a pizza selection was made
        if (pizzaOrder.getPizzaSelection() == null || pizzaOrder.getPizzaSelection().isEmpty()) {
            errors.add("Please select a pizza.");
        } else if (!Arrays.asList("bella", "margherita", "marinara", "spianatacalabrese", "trioformaggio").contains(pizzaOrder.getPizzaSelection())) {
            // Check that the pizza selection is valid
            errors.add("Invalid pizza selection.");
        }

        // Check that a pizza size was selected
        if (pizzaOrder.getPizzaSize() == null || pizzaOrder.getPizzaSize().isEmpty()) {
            errors.add("Please select a pizza size.");
        } else if (!Arrays.asList("sm", "md", "lg").contains(pizzaOrder.getPizzaSize())) {
            // Check that the pizza size is valid
            errors.add("Invalid pizza size.");
        }

        // Check that the number of pizzas is between 1 and 10
        if (pizzaOrder.getQuantity() < 1 || pizzaOrder.getQuantity() > 10) {
            errors.add("Invalid quantity, you can order between 1 and 10 pizzas.");
        }

        return errors;
    }
    public void storeOrder(PizzaOrder pizzaOrder) {
        // Connect to Redis and store the order
        redisTemplate.opsForValue().set("pizza_order:"+pizzaOrder.getId(),pizzaOrder);
    }
}

