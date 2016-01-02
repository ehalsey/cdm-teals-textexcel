package textexcel.unit_tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import textexcel.Sheet;
import textexcel.cells.ICell;

public class SheetTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
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
	}

	@Test
	public void testCellEvalValue() {
		Sheet sheet = new Sheet(1, 1);
		sheet.setCell("A1", "(1 + 2 + 3 + 4");
		assertEquals("10",sheet.getCell("A1").getValue().trim());
		sheet.setCell("A1", "(1 + 2 - 5 * 4 / 5");
		assertEquals("-1.6",sheet.getCell("A1").getValue().trim());
		sheet.setCell("A1", "(1 + 2 - 5 * 4 / 5");
		assertEquals("-1.6",sheet.getCell("A1").getValue().trim());
		
		sheet = new Sheet(5, 3);
		sheet.setCell("A1", "1");
		sheet.setCell("B1", "2");
		sheet.setCell("C1", "3");
		sheet.setCell("A2", "4");
		sheet.setCell("B2", "5");
		sheet.setCell("C2", "6");
		sheet.setCell("A4", "( A1 )");
		assertEquals("1",sheet.getCell("A4").getValue().trim());
		sheet.setCell("B4", "( A1 + C1 / 3)");
		assertEquals("1.333333333>",sheet.getCell("B4").getValue());
		sheet.setCell("C4", "( A4 - B4 * -1 )");
		assertEquals("0.333333333>",sheet.getCell("C4").getValue());
		sheet.setCell("A5", "( sum A1 - C2 )");
		assertEquals("21",sheet.getCell("A5").getValue().trim());
		sheet.setCell("B5", "( avg A1 - C2 )");
		assertEquals("3.5",sheet.getCell("B5").getValue().trim());
		sheet.setCell("C5", "( sum A1 - C1 )");
		assertEquals("6",sheet.getCell("C5").getValue().trim());
	}

}
