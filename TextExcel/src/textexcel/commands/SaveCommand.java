package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;


public class SaveCommand extends SheetCommand {
	public SaveCommand() {
		this.commandString = TextExcelProgram.SAVE_COMMAND;
	}

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		saveSheet(sheet,userCommand);
		return this.commandString;
	}

	private static void saveSheet(Sheet sheet, String userInput) {
		String fileName = "";
		try {
			String[] options = userInput.split(" ");
			fileName = options[1];
			persistence.PersistenceHelper.save(fileName, sheet);
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Sorry couldn't save file: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
