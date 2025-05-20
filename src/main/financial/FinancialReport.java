package main.financial;

public class FinancialReport {
    private double salesRevenue = 0.0;
    private double purchaseCosts = 0.0;

    public void orderCost(double cost) {
        // Validate input
        if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative.");
        }

        purchaseCosts += cost;
    }

    public void orderRevenue(double revenue) {
        // Validate input
        if (revenue < 0) {
            throw new IllegalArgumentException("Revenue cannot be negative.");
        }
        salesRevenue += revenue;
    }

    public double getNetIncome() {
        return salesRevenue - purchaseCosts;
    }

    public void printReport() {
        System.out.println("==== Financial Report ====");
        System.out.printf("Total Stock Purchase Costs: £%.2f%n", purchaseCosts);
        System.out.printf("Total Sales Revenue: £%.2f%n", salesRevenue);
        System.out.printf("Net Income (Profit/Loss): £%.2f%n", getNetIncome());
        System.out.println("==========================");
    }
}
