package ua.com.curex.web.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColumnHelper {
	 public static boolean isValidColumn(String dataIndx) {
	        String pattern = "^[a-z,A-Z]*$";

	        Pattern r = Pattern.compile(pattern);

	        Matcher m = r.matcher(dataIndx);
	        if (m.find()) {
	            return true;
	        } else {
	            return false;
	        }
	    }
}
