package textexcel.commands;
public abstract class SheetCommand implements ISheetCommand {

	public String commandString = "";
	
	@Override
	public boolean isMatch(String command) {
		// TODO Auto-generated method stub
		return command.toLowerCase().contains(this.commandString);
	}


}
