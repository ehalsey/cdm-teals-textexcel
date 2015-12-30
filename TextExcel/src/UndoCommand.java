import java.util.Scanner;


public class UndoCommand extends SheetCommand {

	UndoCommand() {
		this.commandString = TextExcelProgram.UNDO_COMMAND;
	}
	
	@Override
	public String executeCommand(Sheet sheet, Scanner input, String command) {
		sheet.popHistory();
		return this.commandString;
	}

}
