package tp.pr4.cityLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import tp.pr4.City;
import tp.pr4.Direction;
import tp.pr4.Place;
import tp.pr4.Street;
import tp.pr4.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr4.items.CodeCard;
import tp.pr4.items.Fuel;
import tp.pr4.items.Garbage;

public class CityLoaderFromTxtFile {
	
	public CityLoaderFromTxtFile() {
		this.stk = null;
		this.streets = new ArrayList<Street>();
		this.places = new ArrayList<Place>();
	}
	
	/* Gestiona el parseo de un place individual */
	
	private Place parsePlace() throws IOException{
		String name = forceString();
		String description = forceString().replace("_", " ");
		Boolean isSpaceShip = forceString(IS_SPACESHIP, NO_SPACESHIP);
		return new Place(name, isSpaceShip, description);
	}
	
	/* Gestiona el parseo de los places */
	
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
	
	/* Gestiona el parseo de un Street individual */
	
	private Street parseStreet() throws IOException{
		forceString(PLACE);
		Place source = this.places.get(forceCorrectPlace(forceNumber()));
		Direction direction = forceDirection();
		forceString(PLACE);
		Place tarjet = this.places.get(forceCorrectPlace(forceNumber()));
		Boolean isOpen = forceString(OPEN, CLOSED);	// Si lee open, devuelve true, si es closed devuelve false. Exception cc.
		String code;
		if(!isOpen) code = forceString(); // Si la calle está cerrrada se le asocia un código. Si no, no
		else code = null;
		return new Street(source, direction, tarjet, isOpen, code);
	}
	
	/* Gestiona el parseo de todos los Streets, con un bucle que llama para
	 * cada street al parseador individual, y lo añade a su  array. */
	
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
	
	/* Gestiona el parseo de todos los items, con un bucle que llama para
	 * cada item a su correspondiente parseador. */
	
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
	
	/*Gestiona el parseo de un fuel individual*/
	
	private void parseFuel(int i) throws IOException {
		forceNumber(i);
		String name = forceString();
		String description = forceString();
		int power = forceNumber();
		int times = forceNumber();
		forceString(PLACE);
		this.places.get(forceCorrectPlace(forceNumber())).addItem(new Fuel(name, description, power, times));
	}
	
	/*Gestiona el parseo de una Garbage individual*/
	
	private void parseGarbage(int i) throws IOException {
		forceNumber(i);
		String name = forceString();
		String description = forceString();
		int recycledMaterial = forceNumber();
		forceString(PLACE);
		this.places.get(forceCorrectPlace(forceNumber())).addItem(new Garbage(name, description, recycledMaterial));
	}
	
	/*Gestiona el parseo de una CodeCard individual*/
	
	private void parseCodecard(int i) throws IOException {
		forceNumber(i);
		String name = forceString();
		String description = forceString();
		String code = forceString();
		forceString(PLACE);
		this.places.get(forceCorrectPlace(forceNumber())).addItem(new CodeCard(name, description, code));
	}
	
	/* Obliga a que el array de lugares contenga uno en la posición i.
	 * Si no hay uno lanza una excepción. Devuelve se nuevo el lugar recibido */
	
	public int forceCorrectPlace(int i)throws WrongCityFormatException{
		//Si en la posición "i" del array de places está fuera de rango, se lanza una excepción
		if(i >= this.places.size()||i<0)throw new WrongCityFormatException("Referecia a un place que no existe");
		return i;
	}
	
	/* Inicializa el StreamTokenizer y lo configura correctamente.
	 * Gestiona la lectura del arcivo en tres partes: parsePlaces, parseStreets 
	 * y parseItems. Devuelve un City debidamente inicializado, convirtiendo 
	 * el ArrayList<Street> a un array de streets igual.
	 */
	
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
	
	/* Devuelve el primer place lugar del array de lugares (supone que el array no es vacío) */
	
	public Place getInitialPlace(){
		return this.places.get(0);
	}
	
	/* Devuelve la dirección correspondiente al String */
	
	private Direction correctDirection(String direction) throws WrongCityFormatException{
		if(Direction.EAST.toString().equalsIgnoreCase(direction))return Direction.EAST;
		else if(Direction.WEST.toString().equalsIgnoreCase(direction))return Direction.WEST;
		else if(Direction.NORTH.toString().equalsIgnoreCase(direction))return Direction.NORTH;
		else if(Direction.SOUTH.toString().equalsIgnoreCase(direction))return Direction.SOUTH;
		else throw new WrongCityFormatException("Error, se esperaba una direccion valida y se " +
				"encontro "+ stk.sval + " en la linea "+ stk.lineno());
	}
	
	/* Obliga a que se lea una dirección válida, y la devuelve */
	
	private Direction forceDirection() throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD){
			throw new WrongCityFormatException("Error, se esperaba un string y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
		return correctDirection(stk.sval);	//Devuelve el String convertido a un Direction válido
	}
	
	/* Obliga a que el número leído sea el entero i */
	
	private void forceNumber (int i) throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_NUMBER||(stk.nval!=i)){
			throw new WrongCityFormatException("Error, se esperaba "+ i + " y se " +
					"encontro "+ stk.nval + " en la linea "+ stk.lineno());
		}
	}
	
	/* Devuelve el número leído del archivo. Si no es un número lanza una excepción*/
	
	private int forceNumber() throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_NUMBER){
			throw new WrongCityFormatException("Error, se esperaba un numero y se " +
					"encontro "+ stk.nval + " en la linea "+ stk.lineno());
		}
		return (int) stk.nval;
	}
	
	/* Lanza una excepción si no se lee la palabra esperada(expected)*/
	
	private void forceString(String expected) throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD||!stk.sval.equals(expected)){
			throw new WrongCityFormatException("Error, se esperaba "+ expected + " y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
	}
	
	/* Devuelve el String leído de archivo. Si no es un String o un token (entre comillas) lanza una excepción*/
	
	private String forceString() throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD&&stk.ttype!=34	){	/*El stk devuelve 34 cuando lee un token*/
			throw new WrongCityFormatException("Error, se esperaba un string y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
		return stk.sval;
	}
	
	/* Devuelve true si la cadena que se lee es expected1, y false si es expected2
	 *  si no es ninguna de las dos lanza una excepción */
	
	private boolean forceString(String expected1, String expected2) throws IOException{
		if(this.stk.nextToken()!=StreamTokenizer.TT_WORD||
				(!stk.sval.equals(expected1)&&!stk.sval.equals(expected2))){
			throw new WrongCityFormatException("Error, se esperaba "+ expected1
					+ " o " + expected2 + " y se " +
					"encontro "+ stk.sval + " en la linea "+ stk.lineno());
		}
		return stk.sval.equals(expected1); //Si no es expected1, y se ha llegado a este punto, es expected2.
	}
	
	/*Constantes para usar en el cargador se mapas*/
	
	private static final String OPEN = "open";
	private static final String CLOSED = "closed";
	private static final String BEGIN_CITY = "BeginCity";
	private static final String END_CITY = "EndCity";
	private static final String BEGIN_PLACES = "BeginPlaces";
	private static final String END_PLACES = "EndPlaces";
	private static final String BEGIN_ITEMS = "BeginItems";
	private static final String END_ITEMS = "EndItems";
	private static final String STREET = "street";
	private static final String PLACE = "place";
	private static final String IS_SPACESHIP = "spaceShip";
	private static final String NO_SPACESHIP = "noSpaceShip";
	private static final String BEGIN_STREETS = "BeginStreets";
	private static final String END_STREETS = "EndStreets";
	private static final String FUEL = "fuel";
	private static final String GARBAGE = "garbage";
	private static final String CODE_CARD = "codecard";

	/* El StreamTokenizer y los arrays de calles y lugares*/
	
	private StreamTokenizer stk;
	private ArrayList<Place> places;
    private ArrayList<Street> streets;
}
