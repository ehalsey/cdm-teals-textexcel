package textexcel.commands;
import java.util.Scanner;

import textexcel.Sheet;



public interface ISheetCommand {
	public boolean isMatch(String command);
	public String executeCommand(Sheet sheet, Scanner input, String userCommand);
}
