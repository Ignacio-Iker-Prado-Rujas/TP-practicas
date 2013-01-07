package tp.pr2.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.City;
import tp.pr3.Direction;
import tp.pr3.Place;
import tp.pr3.RobotEngine;
import tp.pr3.Street;

public class TestRobotEngine {

	private RobotEngine testEngine;
	private Street testStreet;
	
	@Before
	public void setUp() {
		Place source = new MockPlace();
		Place target = new MockPlace();
		testStreet = new Street(source, Direction.NORTH, target);
		Street [] streets = {testStreet};
		testEngine = new RobotEngine(new City(streets), source, Direction.NORTH);
	}
	
	@Test
	public void testRobotEngineInitialConfiguration() {
		assertEquals("ERROR: Robot engine must start with 50 fuel units", 50, testEngine.getFuel());
		assertEquals("ERROR: Robot engine must start with 0 recycled material", 0, testEngine.getRecycledMaterial());
	}
	
	@Test
	public void testAddFuel() {
		int newFuel = 10;
		int currentFuel = testEngine.getFuel();
		testEngine.addFuel(newFuel);
		assertEquals("ERROR: addFuel is not working properly", currentFuel + newFuel, testEngine.getFuel());
		
		newFuel = -10;
		currentFuel = testEngine.getFuel();
		testEngine.addFuel(newFuel);
		assertEquals("ERROR: addFuel is not working properly with negative fuel", currentFuel + newFuel, testEngine.getFuel());		
	}

	@Test
	public void testAddRecycledMaterial() {
		int newMaterial = 10;
		int currentMaterial = testEngine.getRecycledMaterial();
		testEngine.addRecycledMaterial(newMaterial);
		assertEquals("ERROR: addRecycledMaterial is not working properly", currentMaterial + newMaterial, testEngine.getRecycledMaterial());
	}

	@Test
	public void testGetHeadingStreet() {
		Street actualStreet;
		assertNotNull("ERROR: There is a street where the robot is heading but getHeadingStreet returns null", actualStreet = testEngine.getHeadingStreet());
		assertEquals("ERROR: getHeadingStreet dos not return the correct street", testStreet, actualStreet);
		
		testEngine = new RobotEngine(new MockCity(), new MockPlace(), Direction.NORTH);
		assertNull("ERROR: There is not any street where the robot is heading but getHeadingStreet returns an object", testEngine.getHeadingStreet());

	}

}
