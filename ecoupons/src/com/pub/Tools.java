package com.pub;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Tools {

	public static String PrintDate() {
		SimpleDateFormat NewDay = new SimpleDateFormat("yyyyMMddhhmmss");
		String NewDaystr = NewDay.format(new Date());
		return NewDaystr;
	}

}
