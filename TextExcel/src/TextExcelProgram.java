public class TextExcelProgram {
	public static final String CLEAR_COMMAND = "clear";
	public static final String PRINTCELL_COMMAND = "printcell";
	public static final String SETCELL_COMMAND = "setcell";
	public static final String PRINT_COMMAND = "print";
	public static final String EXIT_COMMAND = "exit";
	public static final String UNKNOWN_COMMAND = "unknown";

	public static String ProcessCommand(String userInput, Sheet sheet) {
		if(userInput.toLowerCase().contains(CLEAR_COMMAND)) {
			sheet.clear(userInput);
			return CLEAR_COMMAND;
		}
		else if (userInput.contains("=")) {
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
				if(userInputUpper.matches("[A-Z]{1,2}[0-9]{1,4}")) {
					sheet.printCell(userInputUpper);
					return PRINTCELL_COMMAND;
				}
				return UNKNOWN_COMMAND;
			}
			}
		}
	}
}
