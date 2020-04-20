import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class generalPurposeCircuitTest {

	@Test
	void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		EstimateTool test = new  EstimateTool(null);
		//generalPurposeCircuit(dT, dD, constantLoad, breakerSide)
		
		int result = test.generalPurposeCircuit(10, 0, 0, 15);
        assertEquals(1, result);		
        
        result = test.generalPurposeCircuit(11, 0, 0, 15);
        assertEquals(2, result);
        
        result = test.generalPurposeCircuit(0, 5, 0, 15);
        assertEquals(1, result);
        
        result = test.generalPurposeCircuit(1, 5, 0, 15);
        assertEquals(2, result);
        
        result = test.generalPurposeCircuit(0, 0, 1440, 15);
        assertEquals(1, result);
        
        result = test.generalPurposeCircuit(0, 0, 1441, 15);
        assertEquals(2, result);
        
        
        
        result = test.generalPurposeCircuit(13, 0, 0, 20);
        assertEquals(1, result);		
        
        result = test.generalPurposeCircuit(14, 0, 0, 20);
        assertEquals(2, result);
	}

}
