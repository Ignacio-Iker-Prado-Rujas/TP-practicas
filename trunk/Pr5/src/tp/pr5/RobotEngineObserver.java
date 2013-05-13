package tp.pr5;

public interface RobotEngineObserver {
	
	//The robot engine informs that the communication is over. 
	void communicationCompleted();
	
	//The robot engine informs that the help has been requested 
	void communicationHelp(java.lang.String help);
	
	/*	The robot engine informs that the robot has shut down 
	(because it has arrived at the spaceship or it has run out of fuel);*/
	void engineOff(boolean atShip); 
	
	//The robot engine informs that it has raised an error 
	void raiseError(String msg); 
	
	//The robot engine informs that the robot wants to say something 
	void robotSays(String message); 
	
		//The robot engine informs that the fuel and/or the amount of recycled material has changed 
	void robotUpdate(int fuel, int recycledMaterial); 


}
