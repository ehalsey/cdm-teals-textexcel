import java.util.Scanner;


public class PrintCellCommand extends SheetCommand {
	
	PrintCellCommand() {
		this.commandString = TextExcelProgram.PRINTCELL_COMMAND;
	}
	
	@Override
	public boolean isMatch(String command) {
		return (command.toUpperCase().matches("[A-Z]{1,2}[0-9]{1,4}")); 
	};

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		sheet.printCell(userCommand.toUpperCase());
		return this.commandString;
	}

}
