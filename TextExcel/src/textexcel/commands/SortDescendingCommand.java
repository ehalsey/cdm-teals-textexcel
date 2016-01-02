package textexcel.commands;

import java.util.ArrayList;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;
import textexcel.cells.ICell;
import utils.Utils;

public class SortDescendingCommand extends SheetCommand implements
		ISheetCommand {

	public SortDescendingCommand() {
		this.commandString = TextExcelProgram.SORTDESCENDING_COMMAND;
	}

	@Override
	public boolean isMatch(String command) {
		return command.toLowerCase().matches(
				"sortd [A-Za-z]\\d+ \\- [A-Za-z]\\d+");
	}

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		//TODO need to check for empty cells
		ArrayList<String> cellKeys = Utils.getCellsForFunction(userCommand,
				sheet);
		int numCells = cellKeys.size();

		int i, j, first;
		ICell temp;
		for (i = numCells - 1; i > 0; i--) {
			first = 0; // initialize to subscript of first element
			for (j = 1; j <= i; j++) // locate smallest element between
										// positions 1 and i.
			{
				double firstValue = Double.parseDouble(sheet.getCell(
						cellKeys.get(first)).getValue());
				double jValue = Double.parseDouble(sheet.getCell(
						cellKeys.get(j)).getValue());

				if (jValue < firstValue)
					first = j;
			}
			temp = sheet.getCell(cellKeys.get(first));
			sheet.setCell(cellKeys.get(first), sheet.getCell(cellKeys.get(i)));
			sheet.setCell(cellKeys.get(i), temp);
		}
		return this.commandString;
	}

}
