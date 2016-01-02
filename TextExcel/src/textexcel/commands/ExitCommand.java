package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;


public class ExitCommand extends SheetCommand {

	public ExitCommand() {
		this.commandString = TextExcelProgram.EXIT_COMMAND;
	}
	
	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		return this.commandString;
	}

}
