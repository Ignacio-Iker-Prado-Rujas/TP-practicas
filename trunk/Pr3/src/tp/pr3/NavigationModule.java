package tp.pr3;

import tp.pr3.instructions.exceptions.InstructionExecutionException;
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
	
	/* Comprueba que haya una calle en la dirección del robot y que esté abierta. 
	 * Si está abierta y existe mueve al robot, si no, lanza la excepción correspondiente	*/
	
	public void move() throws InstructionExecutionException{
		Street newStreet = getHeadingStreet();
		if (newStreet == null)
			throw new InstructionExecutionException(Escribe.THERE_IS_NO_STREET.replace("<direction>", currentHeading.toString()));
		else if (!newStreet.isOpen()) throw new InstructionExecutionException(Escribe.STREET_CLOSED);
		else this.currentPlace = newStreet.nextPlace(this.currentPlace);		
	}
	
	public Item pickItemFromCurrentPlace(String id){
		return this.currentPlace.pickItem(id);
	}
	/*En contra de la docu, si ya esta el objeto
	 *  en el lugar, devuelve false. */ 	
	public void dropItemAtCurrentPlace(Item it){
		this.currentPlace.dropItem(it);
	}
	/* True si el item buscado esta en el lugar*/
	public boolean findItemAtCurrentPlace(String id){
		return this.currentPlace.existItem(id);
	}
	public void initHeading(Direction heading){
		this.currentHeading = heading;
	}
	public void scanCurrentPlace(){
		Escribe.mostrar(this.currentPlace.toString());
	}
	public Street getHeadingStreet(){
		return this.city.lookForStreet(this.currentPlace, this.currentHeading);
	}
	public Direction getCurrentHeading(){
		return this.currentHeading;
	}
	/*Para los tests, no usar*/
	public Place getCurrentPlace(){
		return this.currentPlace;
	}
	
	
	private City city;
	private Place currentPlace;
	private Direction currentHeading;
}
	