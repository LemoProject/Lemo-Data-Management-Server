/**
 * File ./src/main/java/de/lemo/dms/processing/BoxPlotGeneratorForDates.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/processing/BoxPlotGeneratorForDates.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.lemo.dms.processing.resulttype.BoxPlot;

/**
 * Generates Boxplot objects for dates and accumulate them to weeks
 * @author Boris Wenzlaff
 * @author Sebastian Schwarzrock
 *
 */
public class BoxPlotGeneratorForDates {
	
	private static final int WEEK = 7;
	private static final int HOUR = 24;
	private static final int TIDE = 28;
	private static final int THOU = 1000;
	private final Map<Integer, HashMap<Date, Long>> weekmap;
	private final Map<Integer, HashMap<Date, Long>> hourmap;
	private final Map<Integer, HashMap<Date, Long>> tidemap;
	private final Set<Date> dates;
	private Date min = null, max = null;
	private final int[] weekdays;

	public BoxPlotGeneratorForDates() {
		// initialisieren
		this.weekmap = new HashMap<Integer, HashMap<Date, Long>>();
		this.hourmap = new HashMap<Integer, HashMap<Date, Long>>();
		this.tidemap = new HashMap<Integer, HashMap<Date, Long>>();
		this.dates = new HashSet<Date>();
		this.weekdays = new int[WEEK];

		// wochen
		for (int i = 0; i < WEEK; i++) {
			this.weekmap.put(Integer.valueOf(i), new HashMap<Date, Long>());
			this.weekdays[i] = 0;
		}
		// stunden
		for (int i = 0; i < HOUR; i++) {
			this.hourmap.put(Integer.valueOf(i), new HashMap<Date, Long>());
		}
		// tides
		for (int i = 0; i < TIDE; i++) {
			this.tidemap.put(Integer.valueOf(i), new HashMap<Date, Long>());
		}
	}

	/**
	 * add new day to the calculation list
	 * 
	 * @param timestamp
	 *            timestamp of the data access
	 */
	@SuppressWarnings("deprecation")
	public void addAccess(final long timestamp) {
		// datum filtern
		final Calendar cal = Calendar.getInstance();
		// TODO Länge des TimeStamps prüfen
		final Date date = new Date(timestamp * THOU);
		cal.setTime(date);
		// generate key for day
		final Date keydate = new Date(timestamp * THOU);
		keydate.setHours(0);
		keydate.setMinutes(0);
		keydate.setSeconds(0);
		// tag und stunde des zugriffs ermitteln
		int day = 0, hour = 0, tide = 0;
		// stunde
		hour = cal.get(Calendar.HOUR_OF_DAY);
		// tag
		final int cday = cal.get(Calendar.DAY_OF_WEEK);
		// TAG 0 = montag
		switch (cday) {
			case Calendar.MONDAY:
				day = 0;
				break;
			case Calendar.TUESDAY:
				day = 1;
				break;
			case Calendar.WEDNESDAY:
				day = 2;
				break;
			case Calendar.THURSDAY:
				day = 3;
				break;
			case Calendar.FRIDAY:
				day = 4;
				break;
			case Calendar.SATURDAY:
				day = 5;
				break;
			case Calendar.SUNDAY:
				day = 6;
				break;
		}
		
		tide = (4 * day + (hour / 6)) % TIDE;
		// add week
		if (this.weekmap.get(day).containsKey(keydate)) {
			long val = this.weekmap.get(day).get(keydate);
			val += 1;
			this.weekmap.get(day).put(keydate, new Long(val));
		}
		else {
			this.weekmap.get(day).put(keydate, new Long(1));
		}
		// add hour
		if (this.hourmap.get(hour).containsKey(keydate)) {
			long val = this.hourmap.get(hour).get(keydate);
			val += 1;
			this.hourmap.get(hour).put(keydate, new Long(val));
		}
		else {
			this.hourmap.get(hour).put(keydate, new Long(1));
		}
		// add tide
		if (this.tidemap.get(tide).containsKey(keydate)) {
			long val = this.tidemap.get(tide).get(keydate);
			val += 1;
			this.tidemap.get(tide).put(keydate, new Long(val));
		}
		else {
			this.tidemap.get(tide).put(keydate, new Long(1));
		}
		this.addDate(keydate);
	}

	public BoxPlot[] calculateResult() {
		BoxPlot[] week = new BoxPlot[WEEK];
		BoxPlot[] hour = new BoxPlot[HOUR];
		BoxPlot[] tide = new BoxPlot[TIDE];
		final BoxPlot[] result = new BoxPlot[WEEK + HOUR + TIDE];
		week = this.calculateResultForWeek();
		hour = this.calculateResultForHour();
		tide = this.calculateResultForTide();
		for (int i = 0; i < WEEK; i++) {
			result[i] = week[i];
		}
		for (int i = WEEK; i < 31; i++) {
			result[i] = hour[i - WEEK];
		}
		for (int i = WEEK + HOUR; i < WEEK + HOUR + TIDE; i++) {
			result[i] = tide[i - (WEEK + HOUR)];
		}
		return result;
	}

	/**
	 * @return 7 boxplots with the result of a seven day week monday - sunday
	 */
	public BoxPlot[] calculateResultForWeek() {
		final BoxPlot[] resultList = new BoxPlot[WEEK];

		for (int i = 0; i < 7; i++) {
			Long[] days = null;
			final HashMap<Date, Long> currentday = this.weekmap.get(i);
			final Date[] dates = currentday.keySet().toArray(new Date[currentday.size()]);
			final Long[] sdays = currentday.values().toArray(new Long[currentday.size()]);
			// berechnen wie viele tage tatsächlich eingetragen werden müssen
			final long dayCount = currentday.size();
			if (dayCount <= 0) {
				final BoxPlot bp = new BoxPlot();
				bp.setName(this.getNameForDay(i));
				resultList[i] = bp;
				continue;
			}
			Arrays.sort(dates);
			final Date firstDay = dates[0];
			long diff = this.weekDiff(firstDay, this.max) + 1;
			diff = diff - dayCount;
			int addCounter = 0;
			if (diff >= 1) {
				days = new Long[(int) (currentday.size() + diff)];
				for (int j = 0; j < diff; j++) {
					days[j] = 0L;
					addCounter++;
				}
			}
			else {
				days = new Long[currentday.size()];
			}
			// vollständige liste mit allen daten und den leertagen
			for (int j = 0; j < currentday.size(); j++) {
				days[j + addCounter] = sdays[j];
			}
			final BoxPlot bp = this.calcBox(days);
			bp.setName(this.getNameForDay(i));
			resultList[i] = bp;
		}
		return resultList;
	}

	/**
	 * @return list with boxplots for the hours 0 - 24
	 */
	public BoxPlot[] calculateResultForHour() {
		final BoxPlot[] resultList = new BoxPlot[HOUR];

		for (int i = 0; i < HOUR; i++) {
			Long[] hours = null;
			final HashMap<Date, Long> currenthour = this.hourmap.get(i);
			final Date[] dates = currenthour.keySet().toArray(new Date[currenthour.size()]);
			final Long[] shours = currenthour.values().toArray(new Long[currenthour.size()]);
			// berechnen wie viele tatsächlich tatsächlich eingetragen werden müssen
			final long hourCount = currenthour.size();
			if (hourCount <= 0) {
				final BoxPlot bp = new BoxPlot();
				bp.setName(Integer.toString(i));
				resultList[i] = bp;
				continue;
			}
			Arrays.sort(dates);
			final Date firstDay = dates[0];
			long diff = this.hourDiff(firstDay, this.max) + 1;
			diff = diff - hourCount;
			int addCounter = 0;
			if (diff >= 1) {
				hours = new Long[(int) (currenthour.size() + diff)];
				for (int j = 0; j < diff; j++) {
					hours[j] = 0L;
					addCounter++;
				}
			}
			else {
				hours = new Long[currenthour.size()];
			}
			// vollständige liste mit allen daten und den leertagen
			for (int j = 0; j < currenthour.size(); j++) {
				hours[j + addCounter] = shours[j];
			}
			final BoxPlot bp = this.calcBox(hours);
			bp.setName(Integer.toString(i));
			resultList[i] = bp;
		}
		return resultList;
	}
	
	/**
	 * @return list with boxplots for the tides 0 - 28
	 */
	public BoxPlot[] calculateResultForTide() {
		final BoxPlot[] resultList = new BoxPlot[TIDE];

		for (int i = 0; i < TIDE; i++) {
			Long[] tides = null;
			final HashMap<Date, Long> currenttide = this.tidemap.get(i);
			final Date[] dates = currenttide.keySet().toArray(new Date[currenttide.size()]);
			final Long[] shours = currenttide.values().toArray(new Long[currenttide.size()]);
			// berechnen wie viele tatsächlich tatsächlich eingetragen werden müssen
			final long tideCount = currenttide.size();
			if (tideCount <= 0) {
				Long[] ar = new Long[1];
				ar[0] = 0L;
				final BoxPlot bp = this.calcBox(ar);
				bp.setName(Integer.toString(i));
				resultList[i] = bp;
				continue;
			}
			Arrays.sort(dates);
			final Date firstDay = dates[0];
			long diff = this.tideDiff(firstDay, this.max) + 1;
			diff = diff - tideCount;
			int addCounter = 0;
			if (diff >= 1) {
				tides = new Long[(int) (currenttide.size() + diff)];
				for (int j = 0; j < diff; j++) {
					tides[j] = 0L;
					addCounter++;
				}
			}
			else {
				tides = new Long[currenttide.size()];
			}
			// vollständige liste mit allen daten und den leertagen
			for (int j = 0; j < currenttide.size(); j++) {
				tides[j + addCounter] = shours[j];
			}
			final BoxPlot bp = this.calcBox(tides);
			bp.setName(Integer.toString(i));
			resultList[i] = bp;
		}
		return resultList;
	}

	// berechnen der boxplot werte
	private BoxPlot calcBox(final Long[] list) {
		final BoxPlot result = new BoxPlot();
		// ---SORTIEREN
		java.util.Arrays.sort(list);
		// ---MEDIAN
		// gerade oder ungerade
		if ((list.length % 2) == 0) {
			// gerade
			int uw, ow;
			uw = (list.length / 2) - 1;
			ow = uw + 1;
			Double m = new Double((list[uw] + list[ow]));
			m = m / 2;
			result.setMedian(m);
		}
		else {
			// ungerade
			result.setMedian(list[(list.length / 2)].doubleValue());
		}
		// ---QUARTILE
		// 1 & 2Quartile
		long q1, q2;
		if (list.length == 1) {
			q1 = 1;
			q2 = 1;
		}
		else {
			q1 = Math.round(0.25 * ((list.length) + 1));
			q2 = Math.round(0.75 * ((list.length) + 1));
		}
		final Long i1 = new Long(q1 - 1);
		final Long i2 = new Long(q2 - 1);
		result.setLowerQuartil(list[i1.intValue()].doubleValue());
		result.setUpperQuartil(list[i2.intValue()].doubleValue());
		result.setUpperWhisker(list[list.length - 1].doubleValue());
		result.setLowerWhisker(list[0].doubleValue());

		return result;
	}

	// für das aktuallisieren des min und max dates
	private void addDate(final Date date) {
		if (this.min == null) {
			this.min = date;
		}
		if (this.max == null) {
			this.max = date;
		}
		if (date.after(this.max)) {
			this.max = date;
		}
		if (date.before(this.min)) {
			this.min = date;
		}
		this.dates.add(date);
	}

	private long weekDiff(final Date first, final Date last) {
		long diff = 0;
		final Calendar cal1 = new GregorianCalendar();
		final Calendar cal2 = new GregorianCalendar();
		cal1.setTime(first);
		cal2.setTime(last);
		// Differenz in ms
		final long time = cal2.getTime().getTime() - cal1.getTime().getTime();
		 // Differenz in Tagen
		final long days = Math.round(time / (24. * 60. * 60. * 1000.));
		diff = Math.round(days / WEEK);
		return diff;
	}

	private long hourDiff(final Date first, final Date last) {
		long diff = 0;
		final Calendar cal1 = new GregorianCalendar();
		final Calendar cal2 = new GregorianCalendar();
		cal1.setTime(first);
		cal2.setTime(last);
		// Differenz in ms
		final long time = cal2.getTime().getTime() - cal1.getTime().getTime();
		// Differenz in Tagen
		final long days = Math.round(time / (24. * 60. * 60. * 1000.));
		diff = Math.round(days);
		return diff;
	}
	
	private long tideDiff(final Date first, final Date last) {
		long diff = 0;
		final Calendar cal1 = new GregorianCalendar();
		final Calendar cal2 = new GregorianCalendar();
		cal1.setTime(first);
		cal2.setTime(last);
		// Differenz in ms
		final long time = cal2.getTime().getTime() - cal1.getTime().getTime();
		// Differenz in Tagen
		final long days = Math.round(time / (24. * 60. * 60. * 1000.));
		diff = Math.round(days / WEEK);
		return diff;
	}

	private String getNameForDay(final int day) {
		String name = "";
		switch (day) {
			case 0:
				name = "monday";
				break;
			case 1:
				name = "tuesday";
				break;
			case 2:
				name = "wednesday";
				break;
			case 3:
				name = "thursday";
				break;
			case 4:
				name = "friday";
				break;
			case 5:
				name = "saturday";
				break;
			case 6:
				name = "sunday";
				break;
			default:
				name = "";
		}
		return name;
	}
}
