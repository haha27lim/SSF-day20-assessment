package sg.edu.nus.iss.app.Assessment.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.Assessment.model.Order;

@Repository
public class PizzaRepository {
    
        @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Method to save an Order object in Redis
    public void save (Order order) {

        // Serialize the Order object to JSON string and store it in Redis using orderId as the key
        redisTemplate.opsForValue().set(order.getOrderId(), order.toJSON().toString());
    }

    // Method to retrieve an Order from Redis by its orderId
    public Optional<Order> get(String orderId) {

        // Retrieve the JSON string from Redis using the orderId as the key
        String json = (String) redisTemplate.opsForValue().get(orderId);

        // If the JSON string is null or empty, return an empty Optional
        if ((null == json) || (json.trim().length() <= 0))
            return Optional.empty();

         // Convert the JSON string back to an Order object and return it wrapped in an Optional
        return Optional.of(Order.create(json));
    }
}
