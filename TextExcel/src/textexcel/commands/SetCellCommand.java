package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;


public class SetCellCommand extends SheetCommand {
	public SetCellCommand() {
		this.commandString = TextExcelProgram.SETCELL_COMMAND;
	}
	
	@Override
	public boolean isMatch(String command) {
		return command.contains("=");
	};

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		String[] results = userCommand.split("=");
		sheet.setCell(results[0].trim().toUpperCase(), results[1].trim());
		return this.commandString;
	}

}
