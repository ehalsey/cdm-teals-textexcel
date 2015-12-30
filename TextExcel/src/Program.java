import java.util.Scanner;


public class Program {

	private static final String FAREWELL = "Farewell!";
	private static final String ENTER_A_COMMAND = "Enter a command: ";
	private static final String WELCOME_TO_TEXT_EXCEL = "Welcome to TextExcel!";

	public static void main(String[] args) {
		//String val = "B3 = \"eric halsey\"";
		//testSplit();
		runTextExcel();

	}

	private static void testSplit() {
		String val = "B3 = \"TEST\"";
		String[] results = val.split(" = ");
		for (int i = 0; i < results.length; i++) {
			System.out.println(results[i]);
		}
	}

	private static void runTextExcel() {
		Sheet sheet = new Sheet();
		TextExcelProgram te = new TextExcelProgram();
		Scanner input = new Scanner(System.in);
		System.out.println(WELCOME_TO_TEXT_EXCEL);
		while(true) {
			System.out.println(ENTER_A_COMMAND);
			String commandReturn = TextExcelProgram.ProcessCommand(input.nextLine(), sheet);
			if(commandReturn.equals(TextExcelProgram.EXIT_COMMAND)) {
				System.out.println(FAREWELL);
				break;
			}
		}
		input.close();
	}

}
