package tp.pr5.gui;

import tp.pr5.Controller;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;

public class GUIController extends Controller {

	public GUIController(RobotEngine game) {
		super(game);
	}

	@Override
	public void startController() {
		this.engine.requestStart();
		//TODO: Esto es muy juno... Es para que avise a los observers y se actualice en los dos sitios donde sale
		this.engine.addFuel(0);
		this.engine.addRecycledMaterial(0);
	}
	
	public void communicateRobot(Instruction instruction){
        this.engine.communicateRobot(instruction);
	}

}
