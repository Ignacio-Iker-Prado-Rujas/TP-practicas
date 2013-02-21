package tp.pr3.cityLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import tp.pr3.City;
import tp.pr3.Direction;
import tp.pr3.Place;
import tp.pr3.Street;
import tp.pr3.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr3.items.CodeCard;
import tp.pr3.items.Fuel;
import tp.pr3.items.Garbage;

public class CityLoaderFromTxtFile {
	public CityLoaderFromTxtFile() {
		this.stk = null;
		this.streets = new ArrayList<Street>();
		this.places = new ArrayList<Place>();
	}
	private Place parsePlace() throws IOException{
		String name = forceString();
		String description = forceString().replace("_", " ");
		Boolean isSpaceShip = IS_SPACESHIP.equals(forceString(IS_SPACESHIP, NO_SPACESHIP));
		return new Place(name, isSpaceShip, description);
	}
	private void parsePlaces()throws IOException{
		forceString(BEGIN_PLACES);
		int i = 0;
		while(this.stk.nextToken()==StreamTokenizer.TT_WORD && stk.sval.equals(PLACE)){
			forceNumber(i);
			this.places.add(parsePlace());
			i++;
		}
		stk.pushBack();
		forceString(END_PLACES);
	}
	private Street parseStreet() throws IOException{
		forceString(PLACE);
		Place source = this.places.get(forceNumber());
		Direction direction = forceDirection();
		forceString(PLACE);
		Place tarjet = this.places.get(forceNumber());
		Boolean isOpen = OPEN.equals(forceString(OPEN, CLOSED));
		String code;
		if(!isOpen) code = forceString();
		else code = null;
		return new Street(source, direction, tarjet, isOpen, code);
	}
	private void parseStreets() throws IOException{
		forceString(BEGIN_STREETS);
		int i = 0;
		while(this.stk.nextToken()==StreamTokenizer.TT_WORD && stk.sval.equals(STREET)){
			forceNumber(i);
			this.streets.add(parseStreet());
			i++;
		}
		stk.pushBack();
		forceString(END_STREETS);
		
	}
	private void parseItems() throws IOException{
		forceString(BEGIN_ITEMS);
		int i = 0;
		while(this.stk.nextToken()==StreamTokenizer.TT_WORD && (stk.sval.equals(GARBAGE) || stk.sval.equals(FUEL) || stk.sval.equals(CODE_CARD))){
			if(FUEL.equals(stk.sval)) parseFuel(i);
			else if(GARBAGE.equals(stk.sval)) parseGarbage(i);
			else if(CODE_CARD.equals(stk.sval)) parseCodecard(i);
			else throw new WrongCityFormatException("Error, se esperaba un item y se " +
						"encontro "+ stk.sval + " en la linea "+ stk.lineno());
			i++;
		}
		stk.pushBack();
		forceString(END_ITEMS);
	}	
	private void parseFuel(int i) throws IOException {
		forceNumber(i);
		String name = forceString();
		String description = forceString();
		int power = forceNumber();
		int times = forceNumber();
		forceString(PLACE);
		this.places.get(forceNumber()).addItem(new Fuel(name, description, power, times));
	}
	private void parseGarbage(int i) throws IOException {
		forceNumber(i);
		String name = forceString();
		String description = forceString();
		int recycledMaterial = forceNumber();
		forceString(PLACE);
		this.places.get(forceNumber()).addItem(new Garbage(name, description, recycledMaterial));
	}
	private void parseCodecard(int i) throws IOException {
		forceNumber(i);
		String name = forceString();
		String description = forceString();
		String code = forceString();
		forceString(PLACE);
		this.places.get(forceNumber()).addItem(new CodeCard(name, description, code));
	}
	
	public City loadCity(InputStream file)throws IOException{
		stk = new StreamTokenizer(new InputStreamReader(file));
		stk.wordChars('\u0021','\u007E');
		stk.quoteChar('"');
		forceString(BEGIN_CITY);
        parsePlaces();
        parseStreets();
        parseItems();
        forceString(END_CITY);
		return new City(streets.toArray(new Street[this.streets.size()]));
	}
	
	public Place getInitialPlace(){
		return this.places.get(0);
	}
	private Direction correctDirection(String direction) throws WrongCityFormatException{
		if(Direction.EAST.toString().equalsIgnoreCase(direction))return Direction.EAST;
		else if(Direction.WEST.toString().equalsIgnoreCase(direction))return Direction.WEST;
		else if(Direction.NORTH.toString().equalsIgnoreCase(direction))return Direction.NORTH;
		else if(Direction.SOUTH.toString().equalsIgnoreCase(direction))return Direction.SOUTH;
		else throw new WrongCityFormatException("Error, se esperaba una direccion valida y se " +
				"encontro "+ stk.sval + " en la linea "+ stk.lineno());
	}
	private Direction forceDirection() throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD){
			throw new WrongCityFormatException("Error, se esperaba un string y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
		return correctDirection(stk.sval);//TODO error si no es una direccion valida
	}
	private void forceNumber (int i) throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_NUMBER||(stk.nval!=i)){
			throw new WrongCityFormatException("Error, se esperaba "+ i + " y se " +
					"encontro "+ stk.nval + " en la linea "+ stk.lineno());
		}
	}
	private int forceNumber() throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_NUMBER){
			throw new WrongCityFormatException("Error, se esperaba un numero y se " +
					"encontro "+ stk.nval + " en la linea "+ stk.lineno());
		}
		return (int) stk.nval;
	}
	private void forceString(String expected) throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD||!stk.sval.equals(expected)){
			throw new WrongCityFormatException("Error, se esperaba "+ expected + " y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
	}
	private String forceString() throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD&&stk.ttype!=34/*34Cuando se lee un token entre comillas(Chapuza)*/){
			throw new WrongCityFormatException("Error, se esperaba un string y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
		return stk.sval;
	}
	private String forceString(String expected1, String expected2) throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD||
				(!stk.sval.equals(expected1)&&!stk.sval.equals(expected2))){
			throw new WrongCityFormatException("Error, se esperaba "+ expected1
					+ " o " + expected2 + " y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
		return stk.sval;
	}
	
	private final String OPEN = "open";
	private final String CLOSED = "closed";
	private final String BEGIN_CITY = "BeginCity";
	private final String END_CITY = "EndCity";
	private final String BEGIN_PLACES = "BeginPlaces";
	private final String END_PLACES = "EndPlaces";
	private final String BEGIN_ITEMS = "BeginItems";
	private final String END_ITEMS = "EndItems";
	private final String STREET = "street";
	private final String PLACE = "place";
	private final String IS_SPACESHIP = "spaceShip";
	private final String NO_SPACESHIP = "noSpaceShip";
	private final String BEGIN_STREETS = "BeginStreets";
	private final String END_STREETS = "EndStreets";
	private final String FUEL = "fuel";
	private final String GARBAGE = "garbage";
	private final String CODE_CARD = "codecard";

	
	private StreamTokenizer stk;
	private ArrayList<Place> places;
    private ArrayList<Street> streets;
}
