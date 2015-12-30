import java.util.Scanner;


public class PrintCommand extends SheetCommand {

	PrintCommand() {
		this.commandString = TextExcelProgram.PRINT_COMMAND;
	}
	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		sheet.print();
		return this.commandString;
	}

}
