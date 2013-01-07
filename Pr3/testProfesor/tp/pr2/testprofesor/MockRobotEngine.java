package tp.pr2.testprofesor;

import tp.pr3.City;
import tp.pr3.Direction;
import tp.pr3.Place;
import tp.pr3.RobotEngine;
import tp.pr3.Street;

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
