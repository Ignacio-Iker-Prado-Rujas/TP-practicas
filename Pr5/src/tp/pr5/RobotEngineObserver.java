package tp.pr5;

public interface RobotEngineObserver {

	// El RobotEngine informa de que la comunicacion ha terminado
	void communicationCompleted();

	// El RobotEngine informa de que se ha pedido la ayuda
	void communicationHelp(java.lang.String help);

	/*
	 * El RobotEngine informa de que el robot se ha apagado
	 * (ya sea porque ha llegado al spaceship o porque no tiene fuel)
	 */
	void engineOff(boolean atShip);

	// El RobotEngine informa de que ha habido un error
	void raiseError(String msg);

	// El RobotEngine informa de que el robot quiere decir algo
	void robotSays(String message);

	/*
	 * El RobotEngine informa de que el fuel o la cantidad de 
	 * material reciclado ha cambiado
	 */
	void robotUpdate(int fuel, int recycledMaterial);

}
