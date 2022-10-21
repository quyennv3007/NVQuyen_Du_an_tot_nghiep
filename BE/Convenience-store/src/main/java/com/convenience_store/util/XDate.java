package com.convenience_store.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class XDate {
	public String dateConverter(String date, String format) throws ParseException {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate date_ = LocalDate.parse(date, inputFormatter).plusDays(1);
//		SimpleDateFormat formatter;
//		formatter = new SimpleDateFormat(format);
//		Date date_ = null;
//		date_ = formatter.parse(date.substring(0,24));
		return outputFormatter.format(date_);
	}
}
