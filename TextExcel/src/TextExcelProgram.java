import java.util.Scanner;

public class TextExcelProgram {
	private static final String FAREWELL = "Farewell!";
	private static final String ENTER_A_COMMAND = "Enter a command: ";
	private static final String WELCOME_TO_TEXT_EXCEL = "Welcome to TextExcel!";	
	
	public static final String UNDO_COMMAND = "undo";
	public static final String SAVE_COMMAND = "save";
	public static final String LOAD_COMMAND = "load";
	public static final String CLEAR_COMMAND = "clear";
	public static final String PRINTCELL_COMMAND = "printcell";
	public static final String SETCELL_COMMAND = "setcell";
	public static final String PRINT_COMMAND = "print";
	public static final String EXIT_COMMAND = "exit";
	public static final String UNKNOWN_COMMAND = "unknown";
	public static final String NOTHING_DONE_COMMAND = "";

	public static void runApp() {
		Sheet sheet = new Sheet();
		Scanner input = new Scanner(System.in);
		System.out.println(WELCOME_TO_TEXT_EXCEL);
		while(true) {
			System.out.println(ENTER_A_COMMAND);
			String commandReturn = ProcessCommand(input, sheet);
			if(commandReturn.equals(TextExcelProgram.EXIT_COMMAND)) {
				System.out.println(FAREWELL);
				break;
			}
		}
		input.close();		
	}
	
	public static String ProcessCommand(Scanner input, Sheet sheet) {
		String userInput = input.nextLine();
		if (userInput.toLowerCase().contains(UNDO_COMMAND)) {
			sheet.popHistory();
			return UNDO_COMMAND;
		}
		if (userInput.toLowerCase().contains(SAVE_COMMAND)) {
			saveSheet(userInput, sheet);
			return SAVE_COMMAND;
		} else if (userInput.toLowerCase().contains(LOAD_COMMAND)) {
			int cellCount = sheet.getCellCount();
			if (cellCount == 0 || cellCount > 0 && confirmAction(input)) {
				sheet.pushHistory();
				loadSheet(userInput, sheet);
				return LOAD_COMMAND;
			}
			return NOTHING_DONE_COMMAND;
		} else if (userInput.toLowerCase().contains(CLEAR_COMMAND)) {
			if (sheet.getCellCount() > 0 && confirmAction(input)) {
				sheet.pushHistory();
				sheet.clear(userInput);
				return CLEAR_COMMAND;
			}
			return NOTHING_DONE_COMMAND;
		} else if (userInput.contains("=")) {
			sheet.pushHistory();
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

	private static boolean confirmAction(Scanner input) {
		System.out.println("Are you sure (yes/no)?");
		String answer = input.nextLine().toLowerCase();
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
			e.printStackTrace();
		}
	}
}
