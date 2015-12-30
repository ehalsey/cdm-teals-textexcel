import java.util.HashMap;
import java.util.Map;
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
	public static final Map<String, ISheetCommand> _commands = getSheetCommands();

	public static void runApp() {
		Sheet sheet = new Sheet();
		Scanner input = new Scanner(System.in);
		System.out.println(WELCOME_TO_TEXT_EXCEL);
		while (true) {
			System.out.println(ENTER_A_COMMAND);
			String commandReturn = ProcessCommand(input, sheet);
			if (commandReturn.equals(TextExcelProgram.EXIT_COMMAND)) {
				System.out.println(FAREWELL);
				break;
			}
		}
		input.close();
	}

	private static Map<String, ISheetCommand> getSheetCommands() {
		Map<String, ISheetCommand> commands = new HashMap<String, ISheetCommand>();
		commands.put(UNDO_COMMAND,new UndoCommand());
		commands.put(SAVE_COMMAND,new SaveCommand());
		commands.put(LOAD_COMMAND,new LoadCommand());
		commands.put(CLEAR_COMMAND,new ClearCommand());
		commands.put(SETCELL_COMMAND,new SetCellCommand());
		commands.put(EXIT_COMMAND,new ExitCommand());
		commands.put(PRINT_COMMAND,new PrintCommand());
		commands.put(PRINTCELL_COMMAND,new PrintCellCommand());
		return commands;
	}

	public static String ProcessCommand(Scanner input, Sheet sheet) {
		String userInput = input.nextLine();
		for (Map.Entry<String, ISheetCommand> entry : _commands.entrySet())
		{
			ISheetCommand command = (ISheetCommand) entry.getValue();
			if(command.isMatch(userInput)) {
				return command.executeCommand(sheet, input, userInput);
			}
		}
		return TextExcelProgram.UNKNOWN_COMMAND;
	}






}
