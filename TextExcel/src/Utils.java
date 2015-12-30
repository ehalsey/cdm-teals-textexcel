import java.util.Arrays;
import java.util.Scanner;


public class Utils {
	public static boolean confirmAction(Scanner input) {
		System.out.println("Are you sure (yes/no)?");
		String answer = input.nextLine().toLowerCase();
		return (answer.equals("yes") || answer.equals("y"));
	}	
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	public static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s);  
	}

	public static String center(String s, int size) {
        return center(s, size, " ");
    }

    public static String center(String s, int size, String pad) {
        if (pad == null)
            throw new NullPointerException("pad cannot be null");
        if (pad.length() <= 0)
            throw new IllegalArgumentException("pad cannot be empty");
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
    
    public static String getStringWithLengthAndFilledWithCharacter(int length, char charToFill) {
    	  if (length > 0) {
    	    char[] array = new char[length];
    	    Arrays.fill(array, charToFill);
    	    return new String(array);
    	  }
    	  return "";
    	}
    
	
}
