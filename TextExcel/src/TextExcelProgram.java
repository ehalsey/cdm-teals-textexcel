public class TextExcelProgram {
	private static final String SETCELL_COMMAND = "setcell";
	public static final String PRINT_COMMAND = "print";
	public static final String EXIT_COMMAND = "exit";
	public static final String UNKNOWN_COMMAND = "unknown";

	public static String ProcessCommand(String userInput, Sheet sheet) {
		if (userInput.contains("=")) {
			String[] results = userInput.split(" = ");
			sheet.setCell(results[0], results[1]);
			return SETCELL_COMMAND;
		} else {
			switch (userInput.toLowerCase()) {
			case EXIT_COMMAND:
				return EXIT_COMMAND;
			case PRINT_COMMAND:
				sheet.print();
				return PRINT_COMMAND;
			default:
				return UNKNOWN_COMMAND;
			}
		}
	}
}
