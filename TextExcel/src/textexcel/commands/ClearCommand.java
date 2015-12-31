package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;
import utils.Utils;


public class ClearCommand extends SheetCommand {
	public ClearCommand() {
		this.commandString = TextExcelProgram.CLEAR_COMMAND;
	}

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		if (sheet.getCellCount() > 0 && Utils.confirmAction(input)) {
			sheet.pushHistory();
			sheet.clear(userCommand);
			return this.commandString;
		}
		return TextExcelProgram.NOTHING_DONE_COMMAND;
	}

}
