package utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
	
	public static ArrayList<String> breakString(String regex, String value) {
		ArrayList<String> parts = new ArrayList<>();
		
		Pattern pattern = Pattern
				.compile(regex);
		Matcher m = pattern.matcher(value);
		while (m.find()) {
			parts.add(m.group());
		}		
		return parts;
	}
	
	
	public static String stripOuter(String value, String wrappedWith) {
		return stripOuter(value,wrappedWith,wrappedWith);
	}
	public static String stripOuter(String value, String startsWith, String endsWith) {
		if (value.startsWith(startsWith)) {
			value = value.substring(1);
			if (value.endsWith(endsWith)) {
				value = value.substring(0, value.length() - 1);
			}
		}
		return value;
	}
	

	public static String stripZeroDecimal(String contents) {
		if(contents.endsWith(".0")) {
			contents = contents.replace(".0", "");
		}
		return contents;
	}
	
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
