import java.util.Scanner;

import persistence.Savable;

public class TextExcelProgram {
	public static final String SAVE_COMMAND = "save";
	public static final String LOAD_COMMAND = "load";
	public static final String CLEAR_COMMAND = "clear";
	public static final String PRINTCELL_COMMAND = "printcell";
	public static final String SETCELL_COMMAND = "setcell";
	public static final String PRINT_COMMAND = "print";
	public static final String EXIT_COMMAND = "exit";
	public static final String UNKNOWN_COMMAND = "unknown";
	public static final String NOTHING_DONE_COMMAND = "";

	public static String ProcessCommand(String userInput, Sheet sheet) {
		if (userInput.toLowerCase().contains(SAVE_COMMAND)) {
			saveSheet(userInput, sheet);
			return SAVE_COMMAND;
		} else if (userInput.toLowerCase().contains(LOAD_COMMAND)) {
			if (confirmAction()) {
				loadSheet(userInput, sheet);
				return LOAD_COMMAND;
			}
			return NOTHING_DONE_COMMAND;
		} else if (userInput.toLowerCase().contains(CLEAR_COMMAND)) {
			if (confirmAction()) {
				sheet.clear(userInput);
				return CLEAR_COMMAND;
			}
			return NOTHING_DONE_COMMAND;
		} else if (userInput.contains("=")) {
			String[] results = userInput.split("=");
			sheet.setCell(results[0].trim().toUpperCase(), results[1].trim());
			return SETCELL_COMMAND;
		} else {
			switch (userInput.toLowerCase()) {
			case EXIT_COMMAND:
				return EXIT_COMMAND;
			case PRINT_COMMAND:
				sheet.print();
				return PRINT_COMMAND;
			default: {
				String userInputUpper = userInput.toUpperCase();
				if (userInputUpper.matches("[A-Z]{1,2}[0-9]{1,4}")) {
					sheet.printCell(userInputUpper);
					return PRINTCELL_COMMAND;
				}
				return UNKNOWN_COMMAND;
			}
			}
		}
	}

	private static boolean confirmAction() {
		System.out.println("Are you sure (yes/no)?");
		Scanner input = new Scanner(System.in);
		String answer = input.nextLine().toLowerCase();
		input.close();
		return (answer.equals("yes") || answer.equals("y"));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void saveSheet(String userInput, Sheet sheet) {
		String fileName = "";
		try {
			String[] options = userInput.split(" ");
			fileName = options[1];
			persistence.PersistenceHelper.save(fileName, sheet);
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Sorry couldn't save file: " + fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
