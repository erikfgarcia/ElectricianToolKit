import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
/**
 * JUnit Testing. This class test the minimum number of circuits require based on the circuit load 
 * and the circuit type. 
 * 
 * @author Erik F Garcia
 *
 */
class EstimateToolTest {

	@Test
	void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		EstimateTool test = new  EstimateTool(null);
		
		//generalPurposeCircuit
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
		
		//applianceBranchCircuit
        
        result = test.applianceBranchCircuit(1,0);
        assertEquals(2, result);
        
        result = test.applianceBranchCircuit(1,1);
        assertEquals(2, result);
        
        result = test.applianceBranchCircuit(13,0);
        assertEquals(2, result);
        
        result = test.applianceBranchCircuit(0,7);
        assertEquals(2, result);
        
        result = test.applianceBranchCircuit(27,0);
        assertEquals(3, result);
        
        result = test.applianceBranchCircuit(26,0);
        assertEquals(2, result);
        
        result = test.applianceBranchCircuit(0,13);
        assertEquals(2, result);
        
        result = test.applianceBranchCircuit(1,13);
        assertEquals(3, result);
        
        //bathLoundry
        result = test.bathLoundry(0, 0, true);
        assertEquals(0, result);
        
        result = test.bathLoundry(0, 0, false);
        assertEquals(0, result);
        
        result = test.bathLoundry(1, 0, true);
        assertEquals(1, result);
        
        result = test.bathLoundry(1, 0, false);
        assertEquals(1, result);
        
        result = test.bathLoundry(14, 0, true);
        assertEquals(2, result);
        
        result = test.bathLoundry(13, 0, true);
        assertEquals(1, result);
        
        result = test.bathLoundry(27, 0, true);
        assertEquals(3, result);
        
        boolean reslt; 
        //needs database connectivity 
        
        // true if database has saved list
      /* reslt = test.getTableNames();
        assertEquals(true, reslt); */
        
        //false if database has no saved list
        /* reslt = test.getTableNames();
        assertEquals(false, reslt); */
        
	}

}
