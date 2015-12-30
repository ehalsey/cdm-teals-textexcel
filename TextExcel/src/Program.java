import java.util.Scanner;


public class Program {

	private static final String FAREWELL = "Farewell!";
	private static final String ENTER_A_COMMAND = "Enter a command: ";
	private static final String WELCOME_TO_TEXT_EXCEL = "Welcome to TextExcel!";

	public static void main(String[] args) {
		// see if this is working
		runTextExcel();

	}

	private static void runTextExcel() {
		Sheet sheet = new Sheet();
		TextExcelProgram te = new TextExcelProgram();
		Scanner input = new Scanner(System.in);
		System.out.println(WELCOME_TO_TEXT_EXCEL);
		while(true) {
			System.out.println(ENTER_A_COMMAND);
			String commandReturn = te.ProcessCommand(input.nextLine(), sheet);
			if(commandReturn.equals(te.EXIT_COMMAND)) {
				System.out.println(FAREWELL);
				break;
			}
		}
		
	}

}
