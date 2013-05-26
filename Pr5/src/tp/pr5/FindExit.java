package tp.pr5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.console.Console;
import tp.pr5.console.ConsoleController;

public class FindExit {
	public static void main(String[] args) {
		
		// Comprueba que se le hayan pasado un argumentos al main 
		if (args.length == 0) {
		   	EscribeConsola.llamadaVacia();
			System.exit(1);
		}	
		/* Crea las opciones */
		Options options = new Options();
        options.addOption("h", "help", false, "Shows this help message");
        Option inter = new Option("i", "interface", true, "The type of interface: only console");
        inter.setArgName("type");
        options.addOption(inter);
        Option mapa = new Option("m", "map", true, "File with the description of the city");
        mapa.setArgName("mapfile");
        options.addOption(mapa);
        Option findExit = new Option("d", "findExit", true, "Profundidad maxima a explorar");
        findExit.setArgName("prof max");
        options.addOption(findExit);
        
        BasicParser parseador = new BasicParser();
        
		try {
			City city = null;
			CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
			CommandLine cmd = parseador.parse(options, args);
			/* Si de solicita ayuda, se muestra */
			if (cmd.hasOption('h')) {
                HelpFormatter h = new HelpFormatter();
                EscribeConsola.mostrar("Execute this assignment with these parameters:");
                h.printHelp("tp.pr5.Main [-h] [-i <type>] [-m <mapfile>]", options); 	//imprime todas las opciones correctas
                System.exit(0);
            }
            /* Comprueba si quiere ejecutarse en consola */
			boolean interfaz = false;
			if (cmd.hasOption('i')) {
				if (cmd.getOptionValue('i').equals("console"))
					interfaz = true;
				else {
					EscribeConsola.imprimirError("Wrong type of interface");
					System.exit(3);
				}
			}
			int maxProfundidad = 0;
			if(cmd.hasOption('d')){
    			try{
    			maxProfundidad = Integer.parseInt(cmd.getOptionValue('d'));
    			}catch(NumberFormatException e){
    				EscribeConsola.imprimirError("Profundidad maxima no indicada");
    				System.exit(1);
    			}
    		}
            /* Carga el mapa del archivo que se haya puesto */
            if (cmd.hasOption('m')){
	            // Comprueba que exista el fichero cuyo nombre se ha pasado como argumento
	    		FileInputStream input = null;
	    		try {
	    			input = new FileInputStream(cmd.getOptionValue('m'));
	    		} catch (FileNotFoundException e) {
	    			EscribeConsola.noExisteFichero(cmd.getOptionValue('m'));
	    			System.exit(2);
	    		}
	    		// Carga el mapa de archivo
	    		
				try {
					city = cityLoader.loadCity(input);
				} catch (IOException e) {
					EscribeConsola.imprimirError(e.getMessage());
					System.exit(2);
				}
			} else {
				EscribeConsola.imprimirError("Map file not specified");
				System.exit(1);
			}
			/* Inicializa con la consola o con la interfaz de swing */
            RobotEngine engine = new RobotEngine(city, cityLoader.getInitialPlace(), Direction.NORTH);
            if(interfaz){
            	/**
        		 * Carga la información el robot y le indica que comience a moverse.
        		 * Empieza el juego si no ha habido problemas, funcionando en consola
        		 */
        		ConsoleController consoleController = new ConsoleController(engine);
        		Console console = new Console();
        		consoleController.registerEngineObserver(console);
        		consoleController.registerItemContainerObserver(console);
        		consoleController.registerRobotObserver(console);
        	}
            Stack<String> arraySolucion = engine.autoEngine(maxProfundidad);
        	if(arraySolucion.isEmpty()) EscribeConsola.say(EscribeConsola.EXIT_NOT_FOUND);
    		else{
    			EscribeConsola.say(EscribeConsola.THE_BEST_EXIT);
    			for (String s : arraySolucion) {
    				EscribeConsola.mostrar("		" + s);
    			}		
    		}
        }catch (ParseException e) {
        	EscribeConsola.llamadaIncorrecta();
			System.exit(1);
        }
	}
}