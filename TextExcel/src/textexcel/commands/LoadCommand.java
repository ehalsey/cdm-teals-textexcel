package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;
import utils.Utils;


public class LoadCommand extends SheetCommand {
	public LoadCommand() {
		this.commandString = TextExcelProgram.LOAD_COMMAND;
	}

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		int cellCount = sheet.getCellCount();
		if (cellCount == 0 || cellCount > 0 && Utils.confirmAction(input)) {
			sheet.pushHistory();
			loadSheet(userCommand, sheet);
			return this.commandString;
		}
		return TextExcelProgram.NOTHING_DONE_COMMAND;
	}
	
	private static void loadSheet(String userInput, Sheet sheet) {
		String fileName = "";
		try {
			String[] options = userInput.split(" ");
			fileName = options[1];
			persistence.PersistenceHelper.load(fileName, sheet);
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Sorry couldn't find file: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
