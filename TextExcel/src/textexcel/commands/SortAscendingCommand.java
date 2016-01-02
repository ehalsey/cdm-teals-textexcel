package textexcel.commands;

import java.util.ArrayList;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;
import textexcel.cells.ICell;
import utils.Utils;

public class SortAscendingCommand extends SheetCommand implements ISheetCommand {

	public SortAscendingCommand() {
		this.commandString = TextExcelProgram.SORTASCENDING_COMMAND;
	}
	
	@Override
	public boolean isMatch(String command) {
		// TODO Auto-generated method stub
		return command.toLowerCase().matches("sorta [A-Za-z]\\d+ \\- [A-Za-z]\\d+");
	}

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		ArrayList<ICell> cells = Utils.getCellsForFunction(userCommand, sheet);
		int i, j, first;
		ICell temp;
		for (i = 0; i < cells.size() - 1; i++) {
			first = i; // initialize to subscript of first element
			for (j = i+1; j < cells.size(); j++) // locate smallest element between
										// positions 1 and i.
			{
				Double firstValue = Double.parseDouble(cells.get(first).getValue());
				Double jValue = Double.parseDouble(cells.get(j).getValue());
				if (jValue < firstValue)
					first = j;
			}
			temp = cells.get(first); // swap smallest found with element in
										// position i.
			cells.set(first, cells.get(i));
			cells.set(i, temp);
		}
		return TextExcelProgram.SORTASCENDING_COMMAND;
	}

}
