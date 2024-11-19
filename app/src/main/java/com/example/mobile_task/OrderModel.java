package com.example.mobile_task;

import java.util.HashMap;
import java.util.Map;

public class OrderModel {
    public String typeOfDish;
    public String mealOption;
    public String size;
    public String extra;
    public int quantity;

    private Map<String, Map<String, Map<String, Double>>> pricingMap = new HashMap<>();
    private Map<String, Double> extrasMap = new HashMap<>();

    public OrderModel() {
        initializePricing();
    }

    public OrderModel(String typeOfDish, String mealOption, String size, String extra, int quantity) {
        this.typeOfDish = typeOfDish;
        this.mealOption = mealOption;
        this.size = size;
        this.extra = extra;
        this.quantity = quantity;
        initializePricing();
    }

    public double getPrice() {
        double basePrice = pricingMap.get(typeOfDish).get(mealOption).get(size);
        double extraPrice = extrasMap.getOrDefault(extra, 0.0);
        return (basePrice + extraPrice) * quantity;
    }

    private void initializePricing() {
        pricingMap.put("Main Course", Map.of(
                "Pizza", Map.of("Small", 10.0, "Medium", 15.0, "Large", 100.0),
                "Burger", Map.of("Small", 8.0, "Medium", 12.0, "Large", 50.0),
                "Pasta", Map.of("Small", 9.0, "Medium", 13.0, "Large", 35.0)
        ));

        pricingMap.put("Side Dish", Map.of(
                "Mash Potato", Map.of("Small", 5.0, "Medium", 7.0, "Large", 9.5),
                "French Fries", Map.of("Small", 4.0, "Medium", 6.0, "Large", 2.3),
                "Soup", Map.of("Small", 1.0, "Medium", 1.5, "Large", 1.3)
        ));

        pricingMap.put("Drink", Map.of(
                "Fizzy Drink", Map.of("Small", 2.0, "Medium", 3.0, "Large", 2.0),
                "Hot Drink", Map.of("Small", 3.0, "Medium", 4.0, "Large", 2.0),
                "Water", Map.of("Small", 4.0, "Medium", 6.0, "Large", 1.0)
        ));

        extrasMap.put("Sauce", 1.0);
        extrasMap.put("Cheese", 1.5);
        extrasMap.put("Without Extra", 0.0);
    }
}
