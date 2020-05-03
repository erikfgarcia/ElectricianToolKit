import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VoltageDropTest {

	@Test
	void test() {
		
		String[] input = {"Copper feet 100 26240 40", "Aluminum meters 2000 33101 10", 
							"Aluminum meters 111 83690 300", "Copper meters 1.11 320 1.222",
							"Aluminum feet 20.30 1022 3.0303030303", "a b 20.30 1022 3.0303030303",
							"Copper meter 5 10 15", "Copp feet 20 25 30", "Aluminum Copper 90 8234 45",
							"Copper feet 999.98789 211592 230000.12561256"};
		
		String[] expectedOutput = {"3.9329", "84.0504", "55.3505", "0.3588", "2.5521", "ERROR", 
									"ERROR", "ERROR", "ERROR", "28044.2142"};
		
		for (int i = 0; i < input.length; i++) {
			
			//reset parameters to null for each input case
			String material = null;
			String unit = null;
			double length = 0;
			double diameter = 0;
			double current = 0;
			
			String[] splitInput = input[i].split("\\s");
			//[material],[unit],[length],[diameter],[current]
			
			if (splitInput.length != 5) {
				System.out.println("ERROR: invalid number of arguments for test case " + i);
				return;
			}
			
			material = splitInput[0];
			unit = splitInput[1];
			length = Double.parseDouble(splitInput[2]);
			diameter = Double.parseDouble(splitInput[3]);
			current = Double.parseDouble(splitInput[4]);
			
			String result = VoltageDropTool.calculate(material, unit, length, diameter, current);
			
			//System.out.println(result);
			assertEquals(result, expectedOutput[i]);
			
			
		}
		
	}
	
	
	

}
