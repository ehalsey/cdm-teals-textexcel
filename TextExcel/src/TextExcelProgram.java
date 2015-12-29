public class TextExcelProgram {
	public static final String PRINT_COMMAND = "print";
	public static final String EXIT_COMMAND = "exit";
	public static final String UNKNOWN_COMMAND = "unknown";

	public static String ProcessCommand(String userInput, Sheet sheet) {
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
