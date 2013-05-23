package tp.pr5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.cli.*;

import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.console.Console;
import tp.pr5.console.ConsoleController;
import tp.pr5.gui.GUIController;
import tp.pr5.gui.MainWindow;

public class Main {
	public static void main(String[] args) {
		
		// Comprueba que se le hayan pasado un argumentos al main 
		if (args.length == 0) {
		   	EscribeConsola.llamadaVacia();
			System.exit(1);
		}
		
		/* Crea las opciones */
		Options options = new Options();
        options.addOption("h", "help", false, "Shows this help message");
        Option inter = new Option("i", "interface", true, "The type of interface: console or swing");
        inter.setArgName("type");
        options.addOption(inter);
        Option mapa = new Option("m", "map", true, "File with the description of the city");
        mapa.setArgName("mapfile");
        options.addOption(mapa);
        
        BasicParser parseador = new BasicParser();
        
		try {
			City city = null;
			CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
			CommandLine cmd = parseador.parse(options, args);
			/* Si de solicita ayuda, se muestra */
			if (cmd.hasOption('h')) {
                HelpFormatter h = new HelpFormatter();
                EscribeConsola.mostrar("Execute this assignment with these parameters:");
                h.printHelp("tp.pr4.Main [-h] [-i <type>] [-m <mapfile>]", options); 	//imprime todas las opcines correctas
                System.exit(0);
            }
            /* Comprueba que los parametros sean correctos */
			boolean consola = false;
			if (cmd.hasOption('i')) {
				if (cmd.getOptionValue('i').equals("console"))
					consola = true;
				else if (cmd.getOptionValue('i').equals("swing"))
					consola = false;
				else {
					EscribeConsola.imprimirError("Wrong type of interface");
					System.exit(1);
				}
			} else {
				EscribeConsola.imprimirError("Interface not specified");
				System.exit(1);
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
            if (consola){
            	/**
        		 * Carga la información el robot y le indica que comience a moverse.
        		 * Empieza el juego si no ha habido problemas, funcionando en consola
        		 */
        		ConsoleController consoleController = new ConsoleController(engine);
        		Console console = new Console();
        		consoleController.registerEngineObserver(console);
        		consoleController.registerItemContainerObserver(console);
        		consoleController.registerRobotObserver(console);
        		consoleController.startController();
            }
    		else {
    			//OJO: Este try-catch es para que salga la ventana como en windows en el mac
    			try {
    				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    			} catch (ClassNotFoundException e1) {
    				e1.printStackTrace();
    			} catch (InstantiationException e1) {
    				e1.printStackTrace();
    			} catch (IllegalAccessException e1) {
    				e1.printStackTrace();
    			} catch (UnsupportedLookAndFeelException e1) {
    				e1.printStackTrace();
    			}
		         /** Carga la información el robot y le indica que comience a moverse.
		          * Crea la ventana para que funcione el entorno gráfico
		          * Empieza el juego si no ha habido problemas	
		          */
				GUIController guiController = new GUIController(engine);
				MainWindow window = new MainWindow(guiController);
				GUIController.registerEngineObserver(window);
				//GUIController.registerItemContainerObserver(window);
				//GUIController.registerRobotObserver(window);
				//GUIController.startController();
				//engine.autoEngine();
				window.setVisible(true);
    		}
        }catch (ParseException e) {
        	EscribeConsola.llamadaIncorrecta();
			System.exit(1);
        }
	}
}
	/*	1.- (HECHO) Modo consola. Observador consola.
	 *  2.- (HECHO creo) Robot engine. Combiar los System.out por emite
	 *  3.- (HECHO creo) Observador del itemContainer. La vista consola lo implementa, lo 
	 *  registramos, y ver si funciona
	 *  4.- (HECHO) Bucle principal al controller
	 *  5.- updateTable -> en el observador registrado	*/
	
