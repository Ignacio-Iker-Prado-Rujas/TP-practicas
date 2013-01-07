package tp.pr2.testprofesor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.Action;
import tp.pr3.Instruction;
import tp.pr3.Interpreter;
import tp.pr3.Rotation;

public class InterpreterTest {
	
	@Before
	public void SetUp() throws Exception {
	}

	@Test
	public void testGenerateInstructionWronginstruction() {
		Instruction c = Interpreter.generateInstruction("GO");
		assertFalse("ERROR: A wrong instruction (GO) does not return an unknown instruction", c.isValid());
	}

	@Test
	public void testGenerateInstructionHelp() {
		Instruction c = Interpreter.generateInstruction("help");
		assertEquals("ERROR: String \"help\" does not return a HELP instruction", Action.HELP, c.getAction());
		
		c = Interpreter.generateInstruction("HELP");
		assertEquals("ERROR: String \"HELP\" does not return a HELP instruction", Action.HELP, c.getAction());
		
		c = Interpreter.generateInstruction("heLP");
		assertEquals("ERROR: String \"heLP\" does not return a HELP instruction", Action.HELP, c.getAction());
	}
	
	@Test
	public void testGenerateInstructionQuit() {
		Instruction c = Interpreter.generateInstruction("quit");
		assertEquals("ERROR: String \"quit\" does not return a QUIT instruction", Action.QUIT, c.getAction());
		
		c = Interpreter.generateInstruction("QUIT");
		assertEquals("ERROR: String \"QUIT\" does not return a QUIT instruction", Action.QUIT, c.getAction());
		
		c = Interpreter.generateInstruction("QuIT");
		assertEquals("ERROR: String \"QuIT\" does not return a QUIT instruction", Action.QUIT, c.getAction());
	}
	
	@Test
	public void testGenerateInstructionTurnWithoutRotation() {
		Instruction c = Interpreter.generateInstruction("turn");
		assertFalse("ERROR: String \"turn\" returns a valid instruction but it has not any rotation",c.isValid());
	}
	
	@Test
	public void testGenerateInstructionWithWrongRotation() {
		Instruction c = Interpreter.generateInstruction("turn dcha");
		assertEquals("ERROR: String \"turn dcha\" does not return a TURN instruction", Action.TURN, c.getAction());
		assertFalse("ERROR: String \"turn dcha\" returns a valid instruction but the rotation is not correct",c.isValid());
	}
	
	@Test
	public void testGenerateInstructionTurnWithCorrectRotation() {
		Instruction c = Interpreter.generateInstruction("TurN left");
		assertTrue("ERROR: String \"TurN left\" is a valid instruction",c.isValid());
		assertEquals("ERROR: String \"TurN left\" does not return a TURN instruction", Action.TURN, c.getAction());
		assertEquals("ERROR: String \"TurN left\" has a rotation different of LEFT", Rotation.LEFT, c.getRotation());
	}
	
	@Test
	public void testHelp() {
		String help = Interpreter.interpreterHelp();
		help.toUpperCase();
		assertTrue("ERROR: interpreterHelp returns a description which does not contain information about instruction MOVE", help.contains("MOVE"));
		assertTrue("ERROR: interpreterHelp returns a description which does not contain information about instruction TURN", help.contains("TURN"));
		assertTrue("ERROR: interpreterHelp returns a description which does not contain information about instruction HELP", help.contains("HELP"));
		assertTrue("ERROR: interpreterHelp returns a description which does not contain information about instruction QUIT", help.contains("QUIT"));
	}
	
	
	@Test
	public void testGenerateInstructionPickWithoutItemName() {
		Instruction c = Interpreter.generateInstruction("pick");
		assertFalse("ERROR: String \"pick\" returns a valid instruction but it has not any item name",c.isValid());
	}
	
	@Test
	public void testGenerateInstructionPickWithItemName() {
		String itemName = MockItem.DEF_NAME;
		Instruction c = Interpreter.generateInstruction("PIck "+itemName);
		assertTrue("ERROR: String \"PIck "+itemName+"\" is a valid instruction",c.isValid());
		assertEquals("ERROR: String \"PIck "+itemName+"\" does not return a PICK instruction", Action.PICK, c.getAction());
		assertEquals("ERROR: String \"PIck "+itemName+"\" has a different itemName", itemName, c.getId());
	}
	
	@Test
	public void testGenerateInstructionOperateWithoutItemName() {
		Instruction c = Interpreter.generateInstruction("OPERATE");
		assertFalse("ERROR: String \"OPERATE\" returns a valid instruction but it has not any item name",c.isValid());
	}
	
	@Test
	public void testGenerateInstructionOperateWithItemName() {
		String itemName = MockItem.DEF_NAME;
		Instruction c = Interpreter.generateInstruction("OPeraTE "+itemName);
		assertTrue("ERROR: String \"OPeraTE "+itemName+"\" is a valid instruction",c.isValid());
		assertEquals("ERROR: String \"OPeraTE "+itemName+"\" does not return a OPERATE instruction", Action.OPERATE, c.getAction());
		assertEquals("ERROR: String \"OPeraTE "+itemName+"\" has a different itemName", itemName, c.getId());
	}
	
	@Test
	public void testGenerateInstructionScanWithoutItemName() {
		Instruction c = Interpreter.generateInstruction("SCAn");
		assertTrue("ERROR: String \"SCan\" returns an invalid instruction but in fact it is a valid instruction",c.isValid());
		assertEquals("ERROR: String \"SCan\" does not return a SCAN instruction", Action.SCAN, c.getAction());
	}
	
	@Test
	public void testGenerateInstructionScanWithItemName() {
		String itemName = MockItem.DEF_NAME;
		Instruction c = Interpreter.generateInstruction("scan "+itemName);
		assertTrue("ERROR: String \"scan "+itemName+"\" is a valid instruction",c.isValid());
		assertEquals("ERROR: String \"scan "+itemName+"\" does not return a SCAN instruction", Action.SCAN, c.getAction());
		assertEquals("ERROR: String \"scan "+itemName+"\" has a different itemName", itemName, c.getId());
	}
}
