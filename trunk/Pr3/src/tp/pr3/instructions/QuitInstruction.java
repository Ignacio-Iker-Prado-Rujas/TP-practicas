package tp.pr3.instructions;

import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.items.ItemContainer;

public class QuitInstruction implements Instruction{
	public QuitInstruction(){
	}
	
	public Instruction parse(String cad) {
		if(SALIR.equalsIgnoreCase(cad)||QUIT.equalsIgnoreCase(cad)) return this;
		else return null;
	}

	public String getHelp() {
		return HELP;
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {}
	public void execute() {}
	
	private static final String QUIT = "QUIT";
	private static final String SALIR = "SALIR";
	private static final String HELP = "QUIT|SALIR";
}
