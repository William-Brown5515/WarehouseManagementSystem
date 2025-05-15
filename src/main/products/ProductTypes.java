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

    public static void addProductType(String type) {
        if (!containsIgnoreCase(type)) {
            TYPES.add(type);
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
