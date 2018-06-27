package pl.duncol.truckito.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
	
	private static final String DEFAULT_FORMAT = "dd.MM.yyyy (HH:mm)";
	
	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		return sdf.format(date);
	}
}
