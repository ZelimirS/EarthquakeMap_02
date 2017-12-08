package probni.zelimir;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String str = "Testing <B><I>bold italic</I></B> text";
		
		Pattern pat = Pattern.compile("<([a-z][a-z0-9]*)\\b[^>]*>.*?</\\1>", Pattern.CASE_INSENSITIVE);
		Matcher m = pat.matcher(str);
		while (m.find()) {
			System.out.println(m.group());
		}

		System.out.println("br. grupa: " + m.groupCount());
	}

}
