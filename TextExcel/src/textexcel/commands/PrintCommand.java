package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;


public class PrintCommand extends SheetCommand {

	public PrintCommand() {
		this.commandString = TextExcelProgram.PRINT_COMMAND;
	}
	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		sheet.print();
		return this.commandString;
	}

}
