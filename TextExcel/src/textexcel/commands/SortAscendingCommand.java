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
		return command.toLowerCase().matches("sorta [A-Za-z]\\d+ \\- [A-Za-z]\\d+");
	}

	@Override
	public String executeCommand(Sheet sheet, Scanner input, String userCommand) {
		//TODO need to check for empty cells
		ArrayList<String> cellKeys = Utils.getCellsForFunction(userCommand, sheet);
		int i, j, first;
		ICell temp;
		for (i = 0; i < cellKeys.size() - 1; i++) {
			first = i; // initialize to subscript of first element
			for (j = i+1; j < cellKeys.size(); j++) // locate smallest element between
										// positions 1 and i.
			{
				Double firstValue = Double.parseDouble(sheet.getCell(cellKeys.get(first)).getValue());
				Double jValue = Double.parseDouble(sheet.getCell(cellKeys.get(j)).getValue());
				if (jValue < firstValue)
					first = j;
			}
			temp = sheet.getCell(cellKeys.get(first)); // swap smallest found with element in
										// position i.
			sheet.setCell(cellKeys.get(first), sheet.getCell(cellKeys.get(i)));
			sheet.setCell(cellKeys.get(i), temp);
		}
		return this.commandString;
	}

}
