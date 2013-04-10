package tp.pr4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import tp.pr4.cityLoader.CityLoaderFromTxtFile;

public class Main {
	public static void main(String[] args) {
		// Comprueba que se le haya pasado un argumento al main (si hay varios se carga el primero)
		if (args.length == 0) {
			Escribe.llamadaIncorrecta();
			System.exit(1);
		}
		// Comprueba que exista el fichero cuyo nombre se ha pasado como argumento
		FileInputStream input = null;
		try {
			input = new FileInputStream(args[0]);
		} catch (FileNotFoundException e) {
			Escribe.noExisteFichero(args[0]);
			System.exit(2);
		}
		// Carga el mapa de archivo
		CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
		City city = null;
		try {
			city = cityLoader.loadCity(input);
		} catch (IOException e) {
			Escribe.mapaIncorrecto(e.getMessage());
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