package sg.edu.nus.iss.app.Assessment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.Assessment.model.Order;
import sg.edu.nus.iss.app.Assessment.service.PizzaService;

@RestController
@RequestMapping(path="/pizza")
public class PizzaRestController {
    
        @Autowired
    private PizzaService pizzaSvc;

    // GET request handler for retrieving an order by orderId
    @GetMapping(path="/{orderId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrder (@PathVariable String orderId) {

        // Call the service to get the order by its ID
        Optional<Order> opt = pizzaSvc.getOrderByOrderId(orderId);

        // If the order is not found, return a 404 NOT FOUND response with a JSON message
        if (opt.isEmpty()) {
            // Build a JSON response message indicating the order was not found
            JsonObject resp = Json.createObjectBuilder()
                    .add("message", "Order %s not found".formatted(orderId))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(resp.toString()); // Return the JSON response
        }

        // If the order is found, return a 200 OK response with the order details in JSON format
        return ResponseEntity.ok(opt.get().toJSON().toString());
    }
}
