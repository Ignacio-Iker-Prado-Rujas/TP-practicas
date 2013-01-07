package tp.pr3;

import tp.pr3.items.Item;

public class NavigationModule {
	public NavigationModule(City city, Place initialPlace) {
		this.city = city;
		this.currentPlace = initialPlace;
		this.currentHeading = Direction.NORTH;
	}
	public boolean atSpaceship(){
		return this.currentPlace.isSpaceship();
	}
	public void rotate(Rotation rotation){
		if(rotation == Rotation.LEFT) this.currentHeading = this.currentHeading.turnLeft();
		else if(rotation == Rotation.RIGHT) this.currentHeading = this.currentHeading.turnRight();
	}
	public void move(){}	// throws InstructionExecutionException
	
	public Item pickItemFromCurrentPlace(String id){return null;}
	public void dropItemAtCurrentPlace(Item it){}
	public boolean findItemAtCurrentPlace(String id){return false;}
	public void initHeading(Direction heading){
		this.currentHeading = heading;
	}
	public void scanCurrentPlace(){}
	public Street getHeadingStreet(){
		return this.city.lookForStreet(this.currentPlace, this.currentHeading);
	}
	public Direction getCurrentHeading(){
		return this.currentHeading;
	}
	public Place getCurrentPlace(){
		return this.currentPlace;
	}
	
	
	private City city;
	private Place currentPlace;
	private Direction currentHeading;
}
	