import java.util.Scanner;


public class SetCellCommand extends SheetCommand {
	SetCellCommand() {
		this.commandString = TextExcelProgram.SETCELL_COMMAND;
	}
	
	@Override
	public boolean isMatch(String command) {
		return command.contains("=");
	};

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		sheet.pushHistory();
		String[] results = userCommand.split("=");
		sheet.setCell(results[0].trim().toUpperCase(), results[1].trim());
		return this.commandString;
	}

}