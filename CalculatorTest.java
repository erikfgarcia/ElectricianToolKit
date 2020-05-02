


import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Unit test for Calculator.java, which is back-end for 
 * 		CalculatorTool.java
 * 
 * @author RyanS
 *
 */
public class CalculatorTest {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalculatorTest.testCalculator();
	}*/

	/**
	 * JUnit test method
	 */
	@Test
	public void testCalculator() {
		String result;
		// calculator input
		String[] input = {"abc", "", "(5+(10/5))^2", "5/2", "25^(1/2)",
				"7", "1/0", "1  +  100", "(7)(1/2)", "(8)", "2 1",
				"4*-2.0", "0*99", "0/3", "99 - 99*2"};
		
		// expected output from calculator
		String[] output = {"ERROR", "ERROR", "49", "2.5", "5",
				"7", "Infinity", "101", "3.5", "8", "ERROR",
				"-8", "0", "0", "-99"};
		
		
		/*String[] input = {""};
		String[] output = {"ERROR"};*/
		
		// self-check for valid input/expected-output
		if(input.length != output.length) {
			System.out.println("ERROR: invalid test parameters "
					+ "for Calculator");
			return;
		}
		
		// table header
		System.out.println("Calculator test cases:");
		System.out.printf("%12s %12s %12s %12s\n", "Case", "Input", "Output",
				"Expected");
		for(int i=0; i<60; i++)
			System.out.print("-");
		System.out.println();
		
		// loop for test comparison/assertions
		for(int i=0; i<input.length; i++) {
			// tries to convert to double and test
			try {
				result = getResult(input[i]);
				double res = Double.parseDouble(result);
				double out = Double.parseDouble(output[i]);
				
				System.out.printf("%12d %15s %12.4f %12s\n", i+1, input[i], 
						out, output[i]);
				
				assertEquals(Double.doubleToLongBits(res), 
						Double.doubleToLongBits(out));
			}
			// tests the strings if non-numeric [ERROR case]
			catch(Exception e) {
				result = getResult(input[i]);
				System.out.printf("%12d %15s %12s %12s\n", i+1, input[i], 
						result, output[i]);
				
				assertEquals(result, output[i]);
			}
		}
	}
	
	// simplifies Calculator method call
	public String getResult(String s) {
		return Calculator.getResult(s);
	}
	
}







