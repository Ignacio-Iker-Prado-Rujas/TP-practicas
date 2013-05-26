package tp.pr5;

import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.Item;

public class NavigationModule extends Observable<NavigationObserver>{
	public NavigationModule(City city, Place initialPlace) {
		this.city = city;
		this.currentPlace = initialPlace;
		this.currentHeading = Direction.NORTH; // De forma predeterminada mira al norte
	}

	public boolean atSpaceship() {
		return this.currentPlace.isSpaceship();
	}

	/* Recibe una rotation correcta: right o left */
	public void rotate(Rotation rotation) {
		if (rotation == Rotation.LEFT)
			this.currentHeading = this.currentHeading.turnLeft();
		else if (rotation == Rotation.RIGHT)
			this.currentHeading = this.currentHeading.turnRight();
		/* Notifica a los observadores la nueva dirección */
		for (NavigationObserver o : arrayObservers) {
			o.headingChanged(currentHeading);
		}
	}

	/*
	 * Comprueba que haya una calle en la dirección del robot y que esté
	 * abierta. Si está abierta y existe mueve al robot, si no, lanza la
	 * excepción correspondiente
	 */
	public void move() throws InstructionExecutionException {
		Street newStreet = getHeadingStreet();
		if (newStreet == null)
			throw new InstructionExecutionException(EscribeConsola.THERE_IS_NO_STREET.replace("<direction>", currentHeading.toString()));
		else if (!newStreet.isOpen()) {
			throw new InstructionExecutionException(EscribeConsola.STREET_CLOSED);
		} else {
			this.currentPlace = newStreet.nextPlace(this.currentPlace);
			for (NavigationObserver o : arrayObservers) {
				o.robotArrivesAtPlace(currentHeading, currentPlace);
			}
		}
	}
	
	public void undoMove(){
		initHeading(getCurrentHeading().oppositeDirection());
		Street newStreet = getHeadingStreet();
		if (newStreet == null){} //No debería darse porque la instrucción no se habría apilado
		else if (!newStreet.isOpen()) {} // Tampoco debería darse
		else {
			this.currentPlace = newStreet.nextPlace(this.currentPlace);
			for (NavigationObserver o : arrayObservers) {
				o.robotArrivesAtPlace(currentHeading, currentPlace);
				o.undoMove(currentHeading, currentPlace);
			}
		}
		initHeading(getCurrentHeading().oppositeDirection());
	}

	/*
	 * Coge el objeto indicado con la id del lugar en el que está el robot.
	 * Devuelve null si no hay un objeto con esa id
	 */
	public Item pickItemFromCurrentPlace(String id) {
		Item item = currentPlace.pickItem(id);
		if (item != null)
			// Notifica a los observers que el lugar ha sufrido un cambio
			for (NavigationObserver o : arrayObservers) {
				o.placeHasChanged(currentPlace);
			}
		return item;
	}

	/*
	 * Tira el item en el lugar en que está. Supone que ya se ha comprobado que
	 * en el lugar no hay otro objeto con el midmo id
	 */
	public void dropItemAtCurrentPlace(Item it) {
		if (currentPlace.dropItem(it)) {
			for (NavigationObserver o : arrayObservers) {
				o.placeHasChanged(currentPlace);
			}
		}
	}

	/* True si el item buscado esta en el lugar. False si no */
	public boolean findItemAtCurrentPlace(String id) {
		return currentPlace.existItem(id);
	}

	/* Inicializa la dirección del robot */
	public void initHeading(Direction heading) {
		currentHeading = heading;
	}

	/*
	 * Le da a los observers la informacion del lugar donde esta WALL·E, y la
	 * lista de los items
	 */
	public void scanCurrentPlace() {
		for (NavigationObserver o : this.arrayObservers) {
			o.placeScanned(currentPlace);
		}
	}

	/*
	 * Duevuelve la calle hacia la que está mirando el robot. 
	 * Null si no hay calle
	 */
	public Street getHeadingStreet() {
		return this.city.lookForStreet(this.currentPlace, this.currentHeading);
	}

	/* Devuelve el lugar donde está el robot */
	public Direction getCurrentHeading() {
		return this.currentHeading;
	}

	/* Para los tests, no usar */
	public Place getCurrentPlace() {
		return this.currentPlace;
	}

	private City city;
	private Place currentPlace;
	private Direction currentHeading;
}
	