package textexcel.cells;
import java.text.DateFormat;
import java.text.ParseException;


public class DateCellValue implements ICellValue {

	@Override
	public boolean isMatch(String value) {
		if(value.contains("/"))
		{
			DateFormat formatter = DateCell.SIMPLE_DATE_FORMAT;
			try {
				formatter.parse(value);
				return true;
			} catch (ParseException e) {
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCellTypeName() {
		// TODO Auto-generated method stub
		return ICellValue.nameSpace + "DateCell";
	}

}
