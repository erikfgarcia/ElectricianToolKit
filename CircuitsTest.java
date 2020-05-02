
/**
 * Test cases
 * Note: The application checks for zeros and symbols
 * @author Aiesha Suarez Del Real
 */
public class CircuitsTest {
    public static void main(String[] args) {
        CircuitsTool test = new CircuitsTool(null);
        double result;

        result = test.seriesCapacitance(5,15); //Test Series Capacitance
        if (result == 3.75) {
            System.out.println("Test case 1 passed: Series capacitance calculations are correct.");
        }
        else {
            System.out.println("Test case 1 failed: Series capacitance calculations are incorrect.");
        }

        result = test.seriesResistance(5,10); //Test Series Resistance
        if (result == 15.0) {
            System.out.println("Test case 2 passed: Series resistance/inductance calculations are correct.");
        }
        else {
            System.out.println("Test case 2 failed: Series resistance/inductance calculations are incorrect.");
        }

        result = test.parallelCapacitance(3.75,2.10); //Test Parallel Capacitance
        if (result == 5.85) {
            System.out.println("Test case 3 passed: Parallel capacitance calculations are correct.");
        }
        else {
            System.out.println("Test case 3 failed. Parallel capacitance calculations are incorrect.");
        }

        result = test.parallelResistance(6,3); //Test Parallel Resistance
        if (result == 2.0) {
            System.out.println("Test case 4 passed: Parallel resistance/inductance calculations are correct.");
        }
        else {
            System.out.println("Test case 4 failed. Parallel resistance/inductance calculations are incorrect.");
        }


    }
}