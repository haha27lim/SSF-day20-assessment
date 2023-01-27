package sg.edu.nus.iss.app.Assessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import sg.edu.nus.iss.app.Assessment.model.DeliveryDetails;

@Qualifier("orderRedis")
@Service
public class OrderRedis {
    
    // Defining the constant for Order Entity
    private static final String ORDER_ENTITY = "orderlist";
    
    //Autowiring the RedisTemplate
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    // Method to save Order
    public void save(final @Valid DeliveryDetails deliveryDetails) {
        redisTemplate.opsForList().leftPush(ORDER_ENTITY, deliveryDetails.getId());
        redisTemplate.opsForHash().put(ORDER_ENTITY + "_Map", deliveryDetails.getId(), deliveryDetails);
    }

    public DeliveryDetails findById(final String orderId) {
        DeliveryDetails result = (DeliveryDetails)redisTemplate.opsForHash().get(ORDER_ENTITY + "_Map", orderId);
        return result;
    }

    public List<DeliveryDetails> findAll(int startIndex) {
        List<Object> fromOrderList = redisTemplate.opsForList().range(ORDER_ENTITY, startIndex, 10);

        List<DeliveryDetails> ords = redisTemplate.opsForHash()
                                .multiGet(ORDER_ENTITY + "_Map", fromOrderList)
                                .stream()
                                .filter(DeliveryDetails.class::isInstance)
                                .map(DeliveryDetails.class::cast)
                                .toList();

        return ords;
    }
}
