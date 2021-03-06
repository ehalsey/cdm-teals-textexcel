package textexcel.unit_tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import textexcel.Sheet;
import textexcel.cells.ICell;
import textexcel.commands.*;

public class SheetTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	}

	@Test
	public void testSheet() {
		Sheet sheet = new Sheet(10,7);
		assertNotNull(sheet);
	}

	@Test
	public void testSetToStringCell() {
		Sheet sheet = new Sheet(10,7);
		populateSheet(sheet);

		checkCellsToString(sheet);
		
	}

	private void checkCellsToString(Sheet sheet) {
		ICell cell;
		// test TextCell
		cell = sheet.getCell("A1");
		assertEquals("\"ABCD\"", cell.toString());
		// test DateCell
		cell = sheet.getCell("A2");
		assertEquals("01/01/2016", cell.toString());
		// test NumberCell
		cell = sheet.getCell("A3");
		assertEquals("1234", cell.toString());
		// test FormulaCell
		cell = sheet.getCell("A4");
		assertEquals("(1+2+3+4)", cell.toString());
	}

	private void populateSheet(Sheet sheet) {
		sheet.setCell("A1", "\"ABCD\"");
		sheet.setCell("A2", "1/1/2016");
		sheet.setCell("A3", "1234");
		sheet.setCell("A4", "(1+2+3+4)");
	}

	@Test
	public void testToString() throws FileNotFoundException {
		//test default sheet
		Sheet sheet;
		sheet = new Sheet(10,7);
		assertEquals(getFileAsString("misc\\defaultprint.txt"),sheet.toString());
		
		//test 1x1 sheet
		sheet = new Sheet(1,1);
		assertEquals(getFileAsString("misc\\OneByOnePrint.txt"),sheet.toString());
		
		sheet = new Sheet(3, 1);
		//test overflow
		sheet.setCell("A1", "\"ABCDEFGHIJKLMNO\"");
		sheet.setCell("A2", "123456789101234");
		sheet.setCell("A3", "(123456789101234+123456789101234)");
		//TestOverFlowPrint.txt
		assertEquals(getFileAsString("misc\\TestOverFlowPrint.txt"),sheet.toString());
	}

	private String getFileAsString(String fileNamePath)
			throws FileNotFoundException {
		Scanner input;
		StringBuffer defaultOutput;
		defaultOutput = new StringBuffer();
		input = new Scanner(new File(fileNamePath));

		try {
			while (input.hasNextLine()) {
				defaultOutput.append(input.nextLine()+"\r\n");
			}
		} finally {
			input.close();
		}
		return defaultOutput.toString();
	}

	@Test
	public void testClear() {
		Sheet sheet = new Sheet(4, 1);
		populateSheet(sheet);
		assertEquals(4, sheet.getCellCount());
		sheet.clear("");
		assertEquals(0, sheet.getCellCount());
	}
	
	@Test
	public void testClearCell() {
		Sheet sheet = new Sheet(4, 1);
		populateSheet(sheet);
		assertEquals(4, sheet.getCellCount());
		sheet.clear("clear A1");
		assertEquals(3, sheet.getCellCount());
	}	

	@Test
	public void testGetSaveData() {
		Sheet sheet = new Sheet(4, 4);
		populateSheet(sheet);
		assertArrayEquals(new String[]{"A2DateCell01/01/2016","A1TextCell\"ABCD\"","A4FormulaCell(1+2+3+4)","A3NumberCell1234"}, sheet.getSaveData());
	}

	@Test
	public void testLoadFrom() {
		Sheet sheet = new Sheet(4, 4);
		populateSheet(sheet);
		sheet.loadFrom(new String[]{"A2DateCell01/01/2016","A1TextCell\"ABCD\"","A4FormulaCell(1+2+3+4)","A3NumberCell1234"});
		assertEquals(4, sheet.getCellCount());
		checkCellsToString(sheet);
	}

	@Test
	public void testGetCellCount() {
		Sheet sheet = new Sheet(4, 4);
		populateSheet(sheet);
		assertEquals(4, sheet.getCellCount());
	}

	@Test
	public void testUndo() {
		Sheet sheet = new Sheet(4, 4);
		populateSheet(sheet);
		sheet.clear("clear A4");
		assertNull(sheet.getCell("A4"));
		sheet.undo();
		assertEquals("(1+2+3+4)", sheet.getCell("A4").toString());
		sheet.clear("clear A1");
		sheet.pushHistory();
		sheet.setCell("A1","ABCD");
		assertEquals("ABCD",sheet.getCell("A1").getValue());
		sheet.undo();
		assertNull(sheet.getCell("A1"));
		SetCellCommand command = new SetCellCommand();
		command.executeCommand(sheet, null, "A1 = DEFG");
		assertEquals("DEFG",sheet.getCell("A1").getValue());
		command.executeCommand(sheet, null, "A1 = HIJK");
		assertEquals("HIJK",sheet.getCell("A1").getValue());
		sheet.undo();
		assertEquals("DEFG",sheet.getCell("A1").getValue());
	}

	@Test
	public void testCellEvalValue() {
		Sheet sheet = new Sheet(1, 1);
		sheet.setCell("A1", "(1 + 2 + 3 + 4");
		assertEquals(10.0,sheet.getCell("A1").getValue());
		sheet.setCell("A1", "(1 + 2 - 5 * 4 / 5");
		assertEquals(-1.6,sheet.getCell("A1").getValue());
		sheet.setCell("A1", "(1 + 2 - 5 * 4 / 5");
		assertEquals(-1.6,sheet.getCell("A1").getValue());
		
		sheet = new Sheet(5, 3);
		sheet.setCell("A1", "1");
		sheet.setCell("B1", "2");
		sheet.setCell("C1", "3");
		sheet.setCell("A2", "4");
		sheet.setCell("B2", "5");
		sheet.setCell("C2", "6");
		sheet.setCell("A4", "( A1 )");
		assertEquals(1.0,sheet.getCell("A4").getValue());
		sheet.setCell("B4", "( A1 + C1 / 3)");
		assertEquals(1.3333333333333333,sheet.getCell("B4").getValue());
		sheet.setCell("C4", "( A4 - B4 * -1 )");
		assertEquals(0.33333333333333326,sheet.getCell("C4").getValue());
		sheet.setCell("A5", "( sum A1 - C2 )");
		assertEquals(21.0,sheet.getCell("A5").getValue());
		sheet.setCell("B5", "( avg A1 - C2 )");
		assertEquals(3.5,sheet.getCell("B5").getValue());
		sheet.setCell("C5", "( sum A1 - C1 )");
		assertEquals(6.0,sheet.getCell("C5").getValue());
		
		PrintCommand pc = new PrintCommand();
		pc.executeCommand(sheet, null, "print");
		try {
			assertEquals(getFileAsString("misc\\functiontestprint.txt"),outContent.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void testSortAscending() {
		Sheet sheet = new Sheet(10, 7);
		sheet.setCell("B3", "100");
		sheet.setCell("C3", "2");
		sheet.setCell("D3", "10");
		sheet.setCell("E3", "200");
		sheet.setCell("F3", "2");
		sheet.setCell("D6", "5");
		sheet.setCell("D7", "60");
		sheet.setCell("D8", "500");
		sheet.setCell("D9", "6");
		sheet.setCell("D10", "5");	
		//Enter a command: sorta B3 - F3
		SortAscendingCommand command = new SortAscendingCommand();
		command.executeCommand(sheet, null, "sorta B3 - F3");
		assertEquals(2.0,sheet.getCell("B3").getValue());
		assertEquals(2.0,sheet.getCell("C3").getValue());
		assertEquals(10.0,sheet.getCell("D3").getValue());
		assertEquals(100.0,sheet.getCell("E3").getValue());
		assertEquals(200.0,sheet.getCell("F3").getValue());
		//Enter a command: sorta D6 - D10
		command.executeCommand(sheet, null, "sorta D6 - D10");
		assertEquals(5.0,sheet.getCell("D6").getValue());
		assertEquals(5.0,sheet.getCell("D7").getValue());
		assertEquals(6.0,sheet.getCell("D8").getValue());
		assertEquals(60.0,sheet.getCell("D9").getValue());
		assertEquals(500.0,sheet.getCell("D10").getValue());
	}

	private void testSortAscendingWithMissingCell() {
		Sheet sheet = new Sheet(10, 7);
		sheet.setCell("B3", "100");
		sheet.setCell("D3", "10");
		sheet.setCell("E3", "200");
		sheet.setCell("F3", "2");
		sheet.setCell("D6", "5");
		sheet.setCell("D8", "500");
		sheet.setCell("D9", "6");
		sheet.setCell("D10", "5");	
		//Enter a command: sorta B3 - F3
		SortAscendingCommand command = new SortAscendingCommand();
		command.executeCommand(sheet, null, "sorta B3 - F3");
		assertNull(sheet.getCell("B3"));
		assertEquals(2.0,sheet.getCell("C3").getValue());
		assertEquals(10.0,sheet.getCell("D3").getValue());
		assertEquals(100.0,sheet.getCell("E3").getValue());
		assertEquals(200.0,sheet.getCell("F3").getValue());
		//Enter a command: sorta D6 - D10
		command.executeCommand(sheet, null, "sorta D6 - D10");
		assertNull(sheet.getCell("D6"));
		assertEquals(5.0,sheet.getCell("D7").getValue());
		assertEquals(5.0,sheet.getCell("D8").getValue());
		assertEquals(6.0,sheet.getCell("D9").getValue());
		assertEquals(500.0,sheet.getCell("D10").getValue());
	}	
	@Test
	public void testZeroCell() {
		Sheet sheet = new Sheet(1,1);
		sheet.setCell("A1", "0");
		assertEquals(0.0,sheet.getCell("A1").getValue());
	}	
	
	private void testSortAscendingRect() {
		Sheet sheet = new Sheet(10, 7);
		SortAscendingCommand command = new SortAscendingCommand();
		sheet.setCell("B2", "7");
		sheet.setCell("C2", "23");
		sheet.setCell("D2", "-2.5");
		sheet.setCell("B3", "15");
		sheet.setCell("C3", "0");
		sheet.setCell("D3", "7");
		command.executeCommand(sheet, null, "sorta B2 - D3");
		assertEquals(-2.5,sheet.getCell("B2").getValue());
		assertEquals(0.0,sheet.getCell("C2").getValue());
		assertEquals(7.0,sheet.getCell("D2").getValue());
		assertEquals(7.0,sheet.getCell("B3").getValue());
		assertEquals(15.0,sheet.getCell("C3").getValue());
		assertEquals(23.0,sheet.getCell("D3").getValue());
	}
	
	private void testSortDescendingRect() {
		Sheet sheet = new Sheet(10, 7);
		SortDescendingCommand command = new SortDescendingCommand();
		sheet.setCell("B2", "7");
		sheet.setCell("C2", "23");
		sheet.setCell("D2", "-2.5");
		sheet.setCell("B3", "15");
		sheet.setCell("C3", "0");
		sheet.setCell("D3", "7");
		command.executeCommand(sheet, null, "sortd B2 - D3");
		assertEquals(23.0,sheet.getCell("B2").getValue());
		assertEquals(15.0,sheet.getCell("C2").getValue());
		assertEquals(7.0,sheet.getCell("D2").getValue());
		assertEquals(7.0,sheet.getCell("B3").getValue());
		assertEquals(0.0,sheet.getCell("C3").getValue());
		assertEquals(-2.5,sheet.getCell("D3").getValue());
	}	
	
	private void testSortDescending() {
		Sheet sheet = new Sheet(10, 7);
		sheet.setCell("B3", "100");
		sheet.setCell("C3", "2");
		sheet.setCell("D3", "10");
		sheet.setCell("E3", "200");
		sheet.setCell("F3", "2");
		sheet.setCell("D6", "5");
		sheet.setCell("D7", "60");
		sheet.setCell("D8", "500");
		sheet.setCell("D9", "6");
		sheet.setCell("D10", "5");	
		//Enter a command: sorta B3 - F3
		SortDescendingCommand command = new SortDescendingCommand();
		command.executeCommand(sheet, null, "sortd B3 - F3");
		assertEquals(200.0,sheet.getCell("B3").getValue());
		assertEquals(100.0,sheet.getCell("C3").getValue());
		assertEquals(10.0,sheet.getCell("D3").getValue());
		assertEquals(2.0,sheet.getCell("E3").getValue());
		assertEquals(2.0,sheet.getCell("F3").getValue());
		//Enter a command: sorta D6 - D10
		command.executeCommand(sheet, null, "sortd D6 - D10");
		assertEquals(500.0,sheet.getCell("D6").getValue());
		assertEquals(60.0,sheet.getCell("D7").getValue());
		assertEquals(6.0,sheet.getCell("D8").getValue());
		assertEquals(5.0,sheet.getCell("D9").getValue());
		assertEquals(5.0,sheet.getCell("D10").getValue());
	}	
	
	@Test
	public void testAllCommands() {
		ClearCommand clearCommand = new ClearCommand();
		Sheet sheet = new Sheet(10, 7);
		populateSheet(sheet);
		assertEquals("ABCD",sheet.getCell("A1").getValue());
		clearCommand.executeCommand(sheet, null, "clear A1");
		assertNull(sheet.getCell("A1"));	
		populateSheet(sheet);
		clearCommand.executeCommand(sheet, null, "clear");
		assertNull(sheet.getCell("A1"));	
		assertNull(sheet.getCell("A2"));	
		assertNull(sheet.getCell("A3"));	
		assertNull(sheet.getCell("A4"));
		assertEquals(0, sheet.getCellCount());
		populateSheet(sheet);
		SaveCommand saveCommand = new SaveCommand();
		saveCommand.executeCommand(sheet, null, "save .\\misc\\temptest.textexcel");
		clearCommand.executeCommand(sheet, null, "clear");
		LoadCommand loadCommand = new LoadCommand();
		loadCommand.executeCommand(sheet, null, "load .\\misc\\temptest.textexcel");
		assertEquals("ABCD",sheet.getCell("A1").getValue());
		assertEquals(new Date("1/1/2016"),sheet.getCell("A2").getValue());
		assertEquals(1234.0,sheet.getCell("A3").getValue());
		assertEquals(10.0,sheet.getCell("A4").getValue());

		sheet.setCell("A1", "\"ABCDEFGHIJKLMNO\"");
		sheet.setCell("A2", "123456789101234");
		sheet.setCell("A3", "(123456789101234+123456789101234)");
		sheet.setCell("A4", "(1+2+3+4");
		sheet.setCell("A5", "1/1/2016");
		PrintCommand printCommand = new PrintCommand();
		printCommand.executeCommand(sheet, null, "print");
		try {
			String fileAsString = getFileAsString("misc\\testprintcommand.txt");
			String string = outContent.toString();
			assertEquals(fileAsString,string);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		testSortAscending();
		testSortDescending();
		testSortAscendingRect();
		testSortDescendingRect();
		testSortAscendingWithMissingCell();
		clearCommand.executeCommand(sheet, null, "clear");
		populateSheet(sheet);
		SetCellCommand setCellCommand = new SetCellCommand();
		setCellCommand.executeCommand(sheet, null, "A1 = \"ABCDEFGHIJKLMNO\"");
		assertEquals("ABCDEFGHIJKLMNO",sheet.getCell("A1").getValue());

		UndoCommand undoCommand = new UndoCommand();
		undoCommand.executeCommand(sheet, null, "undo");
		assertEquals("ABCD",sheet.getCell("A1").getValue());
		PrintCellCommand printCellCommand = new PrintCellCommand();
		sheet.clear("clear");
		populateSheet(sheet);
		outContent.reset();
		printCellCommand.executeCommand(sheet, null, "A1");
		assertEquals("A1 = \"ABCD\"\r\n",outContent.toString());
		outContent.reset();
		printCellCommand.executeCommand(sheet, null, "A2");
		assertEquals("A2 = 01/01/2016\r\n",outContent.toString());
		outContent.reset();
		printCellCommand.executeCommand(sheet, null, "A3");
		assertEquals("A3 = 1234\r\n",outContent.toString());
		outContent.reset();
		printCellCommand.executeCommand(sheet, null, "A4");
		assertEquals("A4 = (1+2+3+4)\r\n",outContent.toString());
		
	}
}
