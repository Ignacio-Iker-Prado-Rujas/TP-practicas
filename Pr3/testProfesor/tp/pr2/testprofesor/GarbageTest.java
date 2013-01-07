package tp.pr2.testprofesor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.Direction;
import tp.pr3.Place;
import tp.pr3.RobotEngine;
import tp.pr3.items.Garbage;

public class GarbageTest {
	private RobotEngine testPlayer;
	private Place testRoom;
	private Garbage vItem;
	private int inc;
	
	@Before
	public void setUp() throws Exception {
		testPlayer = new RobotEngine(new MockCity(), null, Direction.UNKNOWN);
		testRoom = new MockPlace();
		inc = MockItem.DEF_VALUE;
		vItem = new Garbage(MockItem.DEF_NAME, MockItem.DEF_DESC, inc);
	}
	
	@Test
	public void testUse() {
		int score = testPlayer.getRecycledMaterial();
		if (vItem.use(testPlayer, testRoom))
			assertEquals("ERROR: use method from a Valuable object is not working properly", score+inc, testPlayer.getRecycledMaterial());
		else
			fail("ERROR: use method is not working properly because a Valuable object could be used at least once");
	}

	@Test
	public void testUseMoreThanOnce() {
		if (vItem.use(testPlayer, testRoom)){
			assertFalse("ERROR: a Valuable Item cannot be used more than once", vItem.canBeUsed());
		}			
		else
			fail("ERROR: use method is not working properly because a Valuable object could be used at least once");
	}
	
	@Test
	public void testMultipleUse() {

		assertTrue("ERROR: A valuable created could be used at least once", vItem.use(testPlayer, testRoom));
		assertFalse("ERROR: A valuable cannot be used more than once", vItem.use(testPlayer, testRoom));
		assertFalse("ERROR: A valuable cannot be used more than once", vItem.canBeUsed());
		assertFalse("ERROR: A valuable cannot be used more than once", vItem.use(testPlayer, testRoom));

	}	
}
