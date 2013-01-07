package tp.pr2.testprofesor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.CodeCard;
import tp.pr2.Direction;
import tp.pr2.Place;
import tp.pr2.RobotEngine;
import tp.pr2.Street;

public class CodeCardTest {
	private CodeCard testItem;
	private RobotEngine testRobot;
	private Place testPlace;
	Street nonClosingStreet;
	Street openClosetreet;
	
	@Before
	public void setUp() throws Exception {
		testItem = new CodeCard(MockCodeCard.DEF_NAME, MockCodeCard.DEF_DESC, MockCodeCard.DEF_CODE);
		Place targetPlace = new Place ("Target Place", false, "Target place desc");
		Place sourcePlace = new Place ("Source Place", false, "Source place desc");
		nonClosingStreet = new Street(sourcePlace, Direction.NORTH, targetPlace, false, MockCodeCard.WRONG_CODE);
		openClosetreet = new Street(sourcePlace, Direction.NORTH, targetPlace, false, MockCodeCard.DEF_CODE);
		testRobot = new MockRobotEngine(nonClosingStreet);
		testPlace = new MockPlace();		
	}
	
	@Test
	public void testCanBeUsed() {
		assertTrue("ERROR: A codecard can be used more than once", testItem.canBeUsed());
		testItem.use(testRobot, testPlace);
		assertTrue("ERROR: A codecard can be used more than once", testItem.canBeUsed());
	}
	
	@Test 
	public void testUse() {
		// Try with a street with a different code
		assertFalse("ERROR: Heading street has a code different from the code card but use returns true", testItem.use(testRobot, testPlace));
		
		// Now try with a street with the same code
		testRobot = new MockRobotEngine(openClosetreet);
		assertTrue("ERROR: Heading street has the same code that the code card has but use returns false", testItem.use(testRobot, testPlace));
		assertTrue("ERROR: Heading street has the same code that the code card has but use returns false", testItem.use(testRobot, testPlace));

		
		// There is no heading street
		testRobot = new MockRobotEngine(null);
		assertFalse("ERROR: There is no Heading street but use returns true", testItem.use(testRobot, testPlace));
	}
	
}
