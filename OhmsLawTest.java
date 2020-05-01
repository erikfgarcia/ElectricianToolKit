

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test cases
 * Note: Zeros entered into the OhmsLawCalculator parameters represent inputs left blank by the user. Those
 *       are the values the user is trying to find.
 * @author Aiesha Suarez Del Real
 *
 */
public class OhmsLawTest {
    public static void main(String[] args) {
        OhmsTool test = new OhmsTool(null);
        ArrayList<Double> result;

        try { //Test the input of two numbers.
            result = test.OhmsLawCalculator(15, 3, 0, 0);
            if (result.equals(Arrays.asList(15.0, 3.0, 5.0, 45.0))) {
                System.out.println("Test case 1 passed: Able to calculate resistance and power given voltage and current.");
            }
            else {
                System.out.println("Test case 1 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 1 failed: Exception occured.");
        }

        try { //Test the input of two numbers.
            result = test.OhmsLawCalculator(25, 0, 10, 0);
            if (result.equals(Arrays.asList(25.0, 2.5, 10.0, 62.5))) {
                System.out.println("Test case 2 passed: Able to calculate current and power given voltage and resistance.");
            }
            else {
                System.out.println("Test case 2 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 2 failed: Exception occured.");
        }

        try { //Test the input of two numbers.
            result = test.OhmsLawCalculator(35, 0, 0, 7);
            if (result.equals(Arrays.asList(35.0, 0.2, 175.0, 7.0))) {
                System.out.println("Test case 3 passed: Able to calculate current and resistance given voltage and power.");
            }
            else {
                System.out.println("Test case 3 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 3 failed: Exception occured.");
        }

        try { //Test the input of two numbers.
            result = test.OhmsLawCalculator(0, 43, 2, 0);
            if (result.equals(Arrays.asList(86.0, 43.0, 2.0, 3698.0))) {
                System.out.println("Test case 4 passed: Able to calculate voltage and power given current and resistance.");
            }
            else {
                System.out.println("Test case 4 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 4 failed: Exception occured.");
        }

        try { //Test the input of two numbers.
            result = test.OhmsLawCalculator(0, 20, 0, 30);
            if (result.equals(Arrays.asList(1.5, 20.0, 0.075, 30.0))) {
                System.out.println("Test case 5 passed: Able to calculate voltage and resistance given current and power.");
            }
            else {
                System.out.println("Test case 5 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 5 failed: Exception occured.");
        }

        try { //Test the input of two numbers.
            result = test.OhmsLawCalculator(0, 0, 36, 19);
            if (result.equals(Arrays.asList(26.153393661244042, 0.7264831572567789, 36.0, 19.0))) {
                System.out.println("Test case 6 passed: Able to calculate voltage and current given resistance and power.");
            }
            else {
                System.out.println("Test case 6 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 6 failed: Exception occured.");
        }

        try { //Test the input of three numbers.
            result = test.OhmsLawCalculator(30, 80, 15, 0);
            if (result.equals(Arrays.asList(30.0, 80.0, 15.0, 2400.0))) {
                System.out.println("Test case 7 passed: Able to calculate the last value given three values.");
            }
            else {
                System.out.println("Test case 7 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 7 failed: Exception occured.");
        }

        try { //Test the input of decimal values.
            result = test.OhmsLawCalculator(5.25, 0, 10.5, 0);
            if (result.equals(Arrays.asList(5.25, 0.5, 10.5, 2.625))) {
                System.out.println("Test case 8 passed: Able to input decimal numbers and get correct solutions.");
            }
            else {
                System.out.println("Test case 8 failed: calculations are incorrect.");
            }
        }
        catch (Exception ex) {
            System.out.println("Test case 8 failed: Exception occurred.");
        }

        try { //Test the input of only 1 value.
            result = test.OhmsLawCalculator(0, 6, 0, 0);
            System.out.println("Test case 9 failed: Exception was supposed to occur.");
        }
        catch (Exception ex) {
            System.out.println("Test case 9 passed: An exception was thrown for entering only one value.");
        }

        try { //Test the input of 4 values.
            result = test.OhmsLawCalculator(14, 5, 23, 9);
            System.out.println("Test case 10 failed: Exception was supposed to occur.");
        }
        catch (Exception ex) {
            System.out.println("Test case 10 passed: An exception was thrown for entering four values.");
        }
    }
}