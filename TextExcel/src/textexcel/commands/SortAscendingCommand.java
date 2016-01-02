package textexcel.commands;

import java.util.ArrayList;
import java.util.Scanner;

import textexcel.Sheet;
import textexcel.TextExcelProgram;
import textexcel.cells.*;
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
		ArrayList<String> cellKeys = Utils.getCellsForFunction(userCommand, sheet);
		int i, j, first;
		ICell temp;
		for (i = 0; i < cellKeys.size() - 1; i++) {
			first = i; // initialize to subscript of first element
			for (j = i+1; j < cellKeys.size(); j++) // locate smallest element between
										// positions 1 and i.
			{
				ICell firstCell = sheet.getCell(cellKeys.get(first));
				ICell jCell = sheet.getCell(cellKeys.get(j));
				double firstValue=0.0, jValue=0.0;
				if(firstCell != null && firstCell instanceof FormulaCell || firstCell instanceof NumberCell) {
					firstValue = (double)firstCell.getValue();
				}
				if(jCell != null && jCell instanceof FormulaCell || jCell instanceof NumberCell) {
					jValue = (double)jCell.getValue();
				}
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
