package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;


public class UndoCommand extends SheetCommand {

	public UndoCommand() {
		this.commandString = TextExcelProgram.UNDO_COMMAND;
	}
	
	@Override
	public String executeCommand(Sheet sheet, Scanner input, String command) {
		sheet.popHistory();
		return this.commandString;
	}

}
