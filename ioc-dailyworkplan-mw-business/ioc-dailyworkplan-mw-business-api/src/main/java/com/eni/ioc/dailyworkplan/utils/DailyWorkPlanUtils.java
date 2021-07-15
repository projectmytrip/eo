package com.eni.ioc.dailyworkplan.utils;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DailyWorkPlanUtils {

	public static Timestamp getNow() {
		Date now = new Date();
		Timestamp ts = new Timestamp(now.getTime());
		return ts;
	}

	public static LocalDate findLastWorkingDay(List<LocalDate> holidays) {
		LocalDate end = LocalDate.now().plusMonths(1).with(lastDayOfMonth());
		LocalDate result = end;

		Boolean found = false;
		while (!found) {
			if(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY ||
					holidays.contains(result)) {
				result = result.minusDays(1);
			} else {
				found = true;
			}
		}

		return result;
	}

	public static LocalDate addWorkingDays(LocalDate date, int workdays, List<LocalDate> holidays) {
		LocalDate result = date;
		int addedDays = 0;
		while (addedDays < workdays) {
			if(!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY ||
					holidays.contains(result))) {
				++addedDays;
			}
			result = result.plusDays(1);
		}

		//non devo più aggiungere giorni, controllo solo che il giorno selezionato non sia festivo
		boolean foundWorkDay = false;
		while (!foundWorkDay) {
			if(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY ||
					holidays.contains(result)) {
				result = result.plusDays(1);
			} else {
				foundWorkDay = true;
			}
		}

		return result;
	}

	public static Timestamp localDateToTimestamp(LocalDate localDate, Boolean midnight) {
		if(midnight) {
			return Timestamp.valueOf(localDate.atStartOfDay());
		}
		return Timestamp.valueOf(localDate.atStartOfDay().withHour(23).withMinute(59).withSecond(59));
	}

	public static String formatLocalDateCapitalize(Timestamp date) {

		SimpleDateFormat day = new SimpleDateFormat("dd");
		String month = new SimpleDateFormat("MMMM yyyy").format(date);

		return day.format(date) + " " + month.substring(0, 1).toUpperCase() + month.substring(1);
	}
}

