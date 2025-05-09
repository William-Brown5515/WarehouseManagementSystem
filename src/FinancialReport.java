public class FinancialReport {
    private double currentMoney = 150000.0;
    private double salesRevenue = 0.0;
    private double purchaseCosts = 0.0;

    public double getCurrentMoney() { return currentMoney; }
    public double getSalesRevenue() { return salesRevenue; }
    public double getPurchaseCosts() { return purchaseCosts; }

    public void orderCost(double cost) {
        purchaseCosts += cost;
        currentMoney -= cost;
    }

    public void orderRevenue(double revenue) {
        salesRevenue += revenue;
        currentMoney += revenue;
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
