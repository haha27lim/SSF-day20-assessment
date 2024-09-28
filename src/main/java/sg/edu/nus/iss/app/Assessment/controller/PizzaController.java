package sg.edu.nus.iss.app.Assessment.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.Assessment.model.DeliveryDetails;
import sg.edu.nus.iss.app.Assessment.model.Order;
import sg.edu.nus.iss.app.Assessment.model.Pizza;
import sg.edu.nus.iss.app.Assessment.service.PizzaService;

@Controller
public class PizzaController {

    @Autowired
    private PizzaService pizzaSvc;

    // Logger to log information during the request processing
    private Logger logger = Logger.getLogger(PizzaController.class.getName());

    // Handles GET requests to the root ("/") or index page
    @GetMapping(path={"/", "/index.html"})
    public String showLandingPage(Model model, HttpSession session) {
        session.invalidate(); // Clear any previous session data
        model.addAttribute("pizza", new Pizza()); // Add an empty Pizza object to the model for form binding
        return "index"; // Return the view name "index" (to show the pizza selection page)
    }

    // Handles POST requests when a pizza order form is submitted
    @PostMapping(path="/pizza", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postPizza(@Valid Pizza pizza, BindingResult bindingResult, HttpSession session, Model model) {

        // Log the pizza order details
        logger.info("POST /pizza: %s".formatted(pizza.toString()));

        // If there are validation errors in the pizza form, return to the index page
        if (bindingResult.hasErrors())
            return "index";

        // Call the service to validate the pizza order
        List<ObjectError> errors = pizzaSvc.validatePizzaOrder(pizza);
        if (!errors.isEmpty()) {
            for(ObjectError e: errors)
                bindingResult.addError(e); // Add custom validation errors to BindingResult
            return "index"; // Return to the pizza form if there are errors
        }
            
        // Store the valid pizza object in the session
        session.setAttribute("pizza",pizza);

        // Prepare the DeliveryDetails form for the next step
        model.addAttribute("delivery", new DeliveryDetails());

        return "delivery"; // Go to the delivery details page
    }

    // Handles POST requests when the delivery form is submitted
    @PostMapping(path="/pizza/order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postPizzaDelivery(@Valid DeliveryDetails delivery, BindingResult bindingResult, HttpSession session, Model model) {

        // Log the delivery details
        logger.info("POST /pizza/order: %s".formatted(delivery.toString()));

        // If there are validation errors in the delivery form, return to the delivery page
        if (bindingResult.hasErrors())
        return "delivery";

        // Retrieve the pizza object from the session
        Pizza pizza = (Pizza) session.getAttribute("pizza");

        // Use the service to save the pizza order (which includes calculating the cost)
        Order order = pizzaSvc.savePizzaOrder(pizza, delivery);
       
        // Add the order to the model to display in the confirmation page
        model.addAttribute("order", order);

        return "confirmation"; // Go to the order confirmation page
    }

}



