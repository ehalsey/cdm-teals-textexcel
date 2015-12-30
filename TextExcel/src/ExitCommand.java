import java.util.Scanner;


public class ExitCommand extends SheetCommand {

	ExitCommand() {
		this.commandString = TextExcelProgram.EXIT_COMMAND;
	}
	
	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		// TODO Auto-generated method stub
		return this.commandString;
	}

}
