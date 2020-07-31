package com.Javacruitment.interview.template.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateProcessUtil {
	
	public static final String CLIENT_DISPLAY_DATE_FORMAT = "dd/MM/yyyy";
	
	public static Long getSubscriptionDateDifference(Date date1, Date date2) throws Exception{
		YearMonth m1 = YearMonth.from(date1.toInstant().atZone(ZoneOffset.UTC));
	    YearMonth m2 = YearMonth.from(date2.toInstant().atZone(ZoneOffset.UTC));

	    return m1.until(m2, ChronoUnit.MONTHS);
	}
	
	public static Date convertStringToDate(String dateString, String dateFormat) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
		return Date.from(dateTime.toInstant(ZoneOffset.UTC));
	}
	
	public static String convertDateToString(Date date, String dateFormat) {
		Instant instant = date.toInstant();
		LocalDateTime localDateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
		
		return localDateTime.format(dateTimeFormatter);
	}
	
	public static void main(String[] args) {
		//System.out.println(addMonth(new Date(),3 ));
		Date newDate = new Date();
		Date subscriptionDateEnd = DateUtils.addMonths(newDate, 3);
		System.out.println("subscriptionDateEnd= " + subscriptionDateEnd);
		System.out.println(newDate);
		while(newDate.compareTo(subscriptionDateEnd) < 0) {
			newDate = DateUtils.addMonths(newDate, 1);
			System.out.print(","+newDate);
		}
	}
}
