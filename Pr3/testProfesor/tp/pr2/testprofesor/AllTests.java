package tp.pr2.testprofesor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class) 

@Suite.SuiteClasses( { 
						ItemContainerTest.class,
						ItemTest.class,
						FuelTest.class,
						GarbageTest.class,
						CodeCardTest.class,
						PlaceTest.class,
						StreetTest.class,
						CityTest.class,
						InstructionTest.class, 
						InterpreterTest.class, 
					} ) 


public class AllTests {
    // Add new classes to the SuiteClasses array
}
