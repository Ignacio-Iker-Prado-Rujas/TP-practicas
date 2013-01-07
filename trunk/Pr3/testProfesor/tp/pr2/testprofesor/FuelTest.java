package tp.pr2.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.Direction;
import tp.pr3.Fuel;
import tp.pr3.Place;
import tp.pr3.RobotEngine;

public class FuelTest {
	private Fuel testItem;
	private RobotEngine testRobot;
	private Place testPlace;
	private int inc;
	private int times;
	
	@Before
	public void setUp() throws Exception {
		inc = MockItem.DEF_VALUE;
		times = MockItem.DEF_TIMES;
		testItem = new Fuel(MockItem.DEF_NAME, MockItem.DEF_DESC, inc, times);
		testRobot = new RobotEngine(new MockCity(), null, Direction.UNKNOWN);
		testPlace = new MockPlace();		
	}

	@Test
	public void testUse() {
		int fuel = testRobot.getFuel();
		if (testItem.use(testRobot, testPlace))
			assertEquals("ERROR: use method from a Fuel object is not working properly", fuel+inc, testRobot.getFuel());
		else
			fail("ERROR: use method is not working properly because the Fuel can be used at least once");
	}
	
	@Test
	public void testUseOnce() {
		assertTrue("ERROR: A Fuel with one use can be used at least once", testItem.use(testRobot, testPlace));
		assertFalse("ERROR: A Fuel with one use cannot be used more than once", testItem.canBeUsed());
		assertFalse("ERROR: A Fuel with one use cannot be used again", testItem.use(testRobot, testPlace));
	}
	
	
	@Test
	public void testMultipleUse() {
		int times = 2;
		testItem = new Fuel("itemID", "item desc", inc, times);
		assertTrue("ERROR: A Fuel created for multiple uses (2) could be used at least once", testItem.use(testRobot, testPlace));
		assertTrue("ERROR: A Fuel created for multiple uses (2) can be used more than once", testItem.use(testRobot, testPlace));
		assertFalse("ERROR: A Fuel created for multiple uses (2) cannot be used more than the times defined during its construction", testItem.canBeUsed());
		assertFalse("ERROR: A Fuel created for multiple uses (2) ccannot be used more than the times defined during its construction", testItem.use(testRobot, testPlace));

	}	
}
