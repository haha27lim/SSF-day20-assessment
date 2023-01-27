package sg.edu.nus.iss.app.Assessment.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PizzaOrder {

        @NotEmpty(message = "Pizza selection is required")
        @Size(min = 3, max = 20, message = "Pizza selection must be between 3 and 20 characters")
        private String pizzaSelection;
    
        @NotEmpty(message = "Pizza size is required")
        @Size(min = 2, max = 2, message = "Pizza size must be 2 characters")
        private String pizzaSize;
    
        @NotNull(message = "Number of pizzas is required")
        @Min(value = 1, message = "Minimum number of pizzas is 1")
        @Max(value = 10, message = "Maximum number of pizzas is 10")
        private Integer quantity;

        private String id;
        
        //getters and setters
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

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    
}
