package tp.pr2.testprofesor;

import tp.pr2.City;
import tp.pr2.Direction;
import tp.pr2.Place;
import tp.pr2.RobotEngine;
import tp.pr2.Street;

public class MockRobotEngine extends RobotEngine {
	
	private Street headingStreet;

	public MockRobotEngine(City city, Place initialPlace, Direction dir) {
		super(city, initialPlace, dir);
	}
	 
	public MockRobotEngine(Street head) {
		super(new MockCity(), new MockPlace(), Direction.NORTH);
		headingStreet = head;
	}
	
	public Street getHeadingStreet() {
		return headingStreet;
	}

}
