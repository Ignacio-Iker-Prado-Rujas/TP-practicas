package tp.pr4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.cli.*;

import tp.pr4.cityLoader.CityLoaderFromTxtFile;
import tp.pr4.gui.MainWindow;

public class Main {
	public static void main(String[] args) {
		// Comprueba que se le hayan pasado un argumentos al main 
		if (args.length == 0) {
		   	EscribeConsola.llamadaIncorrecta();
					System.exit(1);
		}
		Options options = new Options();
        options.addOption("h", "help", false, "Shows this help message");
        options.addOption("i", "interface", true, "Type of interface ( 'console' or 'swing' ) ");
        options.addOption("m", "map", true, "File map name (.txt)");
        
        BasicParser parseador = new BasicParser();
        
        try {
        	City city = null;
        	CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
            CommandLine cmd = parseador.parse(options, args);
            if(cmd.hasOption('h')){
                HelpFormatter h = new HelpFormatter();
                h.printHelp("Help", options); 	//imprime todas las opcines correctas
            }
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
	    			EscribeConsola.mapaIncorrecto(e.getMessage());
	    			System.exit(2);
	    		}
            }
            if (cmd.hasOption('i')){
            	if(cmd.getOptionValue('i').equals("swing")){
			         /** Carga la información el robot y le indica que comience a moverse.
			          * Crea la ventana para que funcione el entorno gráfico
					 * Empieza el juego si no ha habido problemas	*/
					 
					RobotEngine engine = new RobotEngine(city,
							cityLoader.getInitialPlace(), Direction.NORTH);
					MainWindow window = new MainWindow(engine, cityLoader.getInitialPlace());
					window.setVisible(true);
            	}
            	else if(cmd.getOptionValue('i').equals("console")){
	            	/**
	        		 * Carga la información el robot y le indica que comience a moverse.
	        		 * Empieza el juego si no ha habido problemas, funcionando en consola
	        		 */
	        		RobotEngine engine = new RobotEngine(city,
	        				cityLoader.getInitialPlace(), Direction.NORTH);
	        		engine.startEngine();
                }
            }
        }catch (ParseException e) {
        	EscribeConsola.llamadaIncorrecta();
			System.exit(1);
        }
	}
}
	
