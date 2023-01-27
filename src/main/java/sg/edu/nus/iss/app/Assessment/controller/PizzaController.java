package sg.edu.nus.iss.app.Assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.app.Assessment.model.DeliveryDetails;
import sg.edu.nus.iss.app.Assessment.model.PizzaOrder;
import sg.edu.nus.iss.app.Assessment.service.OrderRedis;
import sg.edu.nus.iss.app.Assessment.service.PizzaService;

@Controller
public class PizzaController {

    // service for interacting with the Redis database
    @Autowired
    private OrderRedis ordRedisSvc;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public String Form (Model model) {
        model.addAttribute("pizzaOrder", new PizzaOrder());
        return "delivery";
    }

    @PostMapping("/pizza")
    public String placeOrder(@ModelAttribute PizzaOrder pizzaOrder, Model model) {
        model.addAttribute("pizzaOrder", pizzaOrder);
        System.out.println("pizzaOrder: " + pizzaOrder);

        // Perform the checks on the pizza order
        List<String> errors = pizzaService.validateOrder(pizzaOrder);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            System.out.println("errors: " + errors);
            return "index";
        }

        // If all checks passed, store the order in Redis and display View 1
        pizzaService.storeOrder(pizzaOrder);
        return "delivery";
    }

    @GetMapping("/delivery")
    public String showDeliveryForm(Model model) {
        PizzaOrder pizzaOrder = (PizzaOrder) model.getAttribute("pizzaOrder");
        model.addAttribute("pizzaOrder", pizzaOrder);
        model.addAttribute("deliveryDetails", new DeliveryDetails());
        System.out.println("pizzaOrder: " + pizzaOrder);
        return "delivery";
    }

    @PostMapping("/delivery")
    public String submitDeliveryForm(@Valid DeliveryDetails deliveryDetails, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "delivery";
        }

        ordRedisSvc.save(deliveryDetails);
        model.addAttribute ("delivery", deliveryDetails);
        // Save the delivery details and redirect to the confirmation page
        return "redirect:/confirmation";
    }

}



