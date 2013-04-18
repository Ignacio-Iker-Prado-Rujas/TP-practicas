package tp.pr4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.cli.*;

import tp.pr4.cityLoader.CityLoaderFromTxtFile;

public class Main {
	public static void main(String[] args) {
		
		Options option = new Options();
		Option help = new Option("h", "help", false, "Shows this help message");
        option.addOption(help);
        Option interf = new Option("i", "interface", true, "Type of interface");
        interf.setArgName("type");
        Option map = new Option("m", "map", true, "File map name");
        option.addOption(interf);
        option.addOption(map);
        
        BasicParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(option, args);
            if(cmd.hasOption('h')){
                HelpFormatter h = new HelpFormatter();
                h.printHelp("Help", option);
            }
            
            //
            
            if(cmd.getOptionValue('i').equals("swing")){
                //getOptionValue devuelve el parámetro tras -i     
            }
            
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		// Comprueba que se le haya pasado un argumento al main (si hay varios se carga el primero)
		if (args.length == 0) {
			EscribeConsola.llamadaIncorrecta();
			System.exit(1);
		}
		// Comprueba que exista el fichero cuyo nombre se ha pasado como argumento
		FileInputStream input = null;
		try {
			input = new FileInputStream(args[0]);
		} catch (FileNotFoundException e) {
			EscribeConsola.noExisteFichero(args[0]);
			System.exit(2);
		}
		// Carga el mapa de archivo
		CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
		City city = null;
		try {
			city = cityLoader.loadCity(input);
		} catch (IOException e) {
			EscribeConsola.mapaIncorrecto(e.getMessage());
			System.exit(2);
		}
		/**
		 * Carga la información el robot y le indica que comience a moverse.
		 * Empieza el juego si no ha habido problemas
		 */
		RobotEngine engine = new RobotEngine(city,
				cityLoader.getInitialPlace(), Direction.NORTH);

		engine.startEngine();

	}
}