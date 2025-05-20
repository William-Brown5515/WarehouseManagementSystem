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
        // Verify initial net income is zero
        assertEquals(0.0, report.getNetIncome(), 0.001);
    }

    @Test
    void testOrderCost_updatesPurchaseCostsAndCurrentMoney() {
        // Verify that orderCost correctly updates purchase costs and net income
        report.orderCost(500.0);
        assertEquals(-500.0, report.getNetIncome(), 0.001);

        report.orderCost(300.0);
        assertEquals(-800.0, report.getNetIncome(), 0.001);
    }

    @Test
    void testOrderRevenue_updatesSalesRevenueAndCurrentMoney() {
        // Verify that orderRevenue correctly updates sales revenue and net income
        report.orderRevenue(1000.0);
        assertEquals(1000.0, report.getNetIncome(), 0.001);

        report.orderRevenue(500.0);
        assertEquals(1500.0, report.getNetIncome(), 0.001);
    }

    @Test
    void testOrderCost_negativeValue_throwsException() {
        // Verify that passing a negative cost throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> report.orderCost(-1));
    }

    @Test
    void testOrderRevenue_negativeValue_throwsException() {
        // Verify that passing a negative revenue throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> report.orderRevenue(-1));
    }
}
