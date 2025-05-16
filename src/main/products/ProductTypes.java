package main.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductTypes {
    public static final String TOOL = "Tool";
    public static final String SAFETY_EQUIPMENT = "Safety Equipment";
    public static final String CONSTRUCTION_MATERIAL = "Construction Material";
    public static final String MACHINERY = "Machinery";

    public static final List<String> TYPES= new ArrayList<>(Arrays.asList(
            "Tool",
            "Safety Equipment",
            "Construction Material",
            "Machinery"
    ));

    // Create a product type
    public static void addProductType(String type) {
        // Checks to ensure the parameter is as expected
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Product type cannot be null or empty.");
        }
        if (!containsIgnoreCase(type)) {
            TYPES.add(type);
        } else {
            throw new IllegalArgumentException("Product type '" + type + "' already exists.");
        }
    }

    private static boolean containsIgnoreCase(String type) {
        for (String t : TYPES) {
            if (t.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
