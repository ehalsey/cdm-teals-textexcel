import java.util.Scanner;



public interface ISheetCommand {
	public boolean isMatch(String command);
	public String executeCommand(Sheet sheet, Scanner input, String userCommand);
}
