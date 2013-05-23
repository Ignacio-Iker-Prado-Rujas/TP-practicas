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
	}
	
	public void communicateRobot(Instruction instruction){
        this.engine.communicateRobot(instruction);
	}

}
