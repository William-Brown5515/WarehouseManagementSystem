package financial;

import main.financial.FinancialReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialReportTest {

    private FinancialReport report;

    @BeforeEach
    void setUp() {
        report = new FinancialReport();
    }

    @Test
    void testInitialState() {
        assertEquals(0.0, report.getNetIncome(), 0.001);
    }

    @Test
    void testOrderCost_updatesPurchaseCostsAndCurrentMoney() {
        report.orderCost(500.0);
        assertEquals(-500.0, report.getNetIncome(), 0.001);  // salesRevenue = 0 - purchaseCosts = 500

        // You could add another cost to check accumulation
        report.orderCost(300.0);
        assertEquals(-800.0, report.getNetIncome(), 0.001);
    }

    @Test
    void testOrderRevenue_updatesSalesRevenueAndCurrentMoney() {
        report.orderRevenue(1000.0);
        assertEquals(1000.0, report.getNetIncome(), 0.001);

        // Add more revenue
        report.orderRevenue(500.0);
        assertEquals(1500.0, report.getNetIncome(), 0.001);
    }

    @Test
    void testOrderCost_negativeValue_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> report.orderCost(-1));
    }

    @Test
    void testOrderRevenue_negativeValue_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> report.orderRevenue(-1));
    }
}
