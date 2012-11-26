package de.lemo.dms.processing;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.processing.resulttype.BoxPlot;

public class BoxPlotGeneratorForDates {
	private HashMap<Integer, HashMap<Date, Long>> weekmap;
	private HashMap<Integer, HashMap<Date, Long>> hourmap;
	private Set<Date> dates;
	private Date min = null, max = null;
	private int[] weekdays;
	
	public BoxPlotGeneratorForDates() {
		//initialisieren
		weekmap = new HashMap<Integer, HashMap<Date, Long>>();
		hourmap = new HashMap<Integer, HashMap<Date, Long>>();
		dates = new HashSet<Date>();
		weekdays = new int[7];
	
		//wochen
		for(int i = 0; i < 7; i++) {
			weekmap.put(new Integer(i), new HashMap<Date, Long>());
			weekdays[i] = 0;
		}
		//stunden
		for(int i = 0; i < 24; i++) {
			hourmap.put(new Integer(i), new HashMap<Date, Long>());
		}
	}
	
	/**
	 * add new day to the calculation list
	 * @param timestamp timestamp of the data access
	 */
	@SuppressWarnings("deprecation")
	public void addAccess(long timestamp) {
		//datum filtern
		Calendar cal = GregorianCalendar.getInstance();
		//TODO Länge des TimeStamps prüfen
		Date date = new Date(timestamp * 1000);
		cal.setTime(date);
		//generate key for day
		Date keydate = new Date(timestamp * 1000);
		keydate.setHours(0);
		keydate.setMinutes(0);
		keydate.setSeconds(0);
		//tag und stunde des zugriffs ermitteln
		int day = 0, hour = 0;
		//stunde
		hour = cal.get(Calendar.HOUR_OF_DAY);
		//tag
		int cday = cal.get(Calendar.DAY_OF_WEEK);
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
		//add week
		if(weekmap.get(day).containsKey(keydate)) {
			long val = weekmap.get(day).get(keydate);
			val +=1;
			weekmap.get(day).put(keydate, new Long(val));
		}
		else {
			weekmap.get(day).put(keydate, new Long(1));
		}
		//add hour
		if(hourmap.get(hour).containsKey(keydate)) {
			long val = hourmap.get(hour).get(keydate);
			val +=1;
			hourmap.get(hour).put(keydate, new Long(val));
		}
		else {
			hourmap.get(hour).put(keydate, new Long(1));
		}
		addDate(keydate);
	}
	
	public BoxPlot[] calculateResult() {
		BoxPlot[] week = new BoxPlot[7];
		BoxPlot[] hour = new BoxPlot[24];
		BoxPlot[] result = new BoxPlot[7+24];
		week = calculateResultForWeek();
		hour = calculateResultForHour();
		for(int i = 0; i < 7; i++) {
			result[i] = week[i];
		}
		for(int i = 7; i < 31; i++) {
			result[i] = hour[i-7];
		}
		return result;
	}
	
	/**
	 * 
	 * @return 7 boxplots with the result of a seven day week monday - sunday
	 */
	public BoxPlot[] calculateResultForWeek() {
		BoxPlot[] resultList = new BoxPlot[7];
		
		for(int i = 0; i< 7; i++) {
			Long[] days = null;
			HashMap<Date, Long> currentday = weekmap.get(i);
			Date[] dates = currentday.keySet().toArray(new Date[currentday.size()]);
			Long[] sdays = currentday.values().toArray(new Long[currentday.size()]);
			//berechnen wie viele tage tatsächlich eingetragen werden müssen
			long dayCount = (long)currentday.size();
			if(dayCount <= 0) {
				BoxPlot bp = new BoxPlot();
				bp.setName(getNameForDay(i));
				resultList[i] = bp;
				continue;
			}
			Arrays.sort(dates);
			Date firstDay = dates[0];
			long diff = weekDiff(firstDay, max) + 1;
			diff = diff - dayCount;
			int addCounter = 0;
			if(diff >= 1) {
				days = new Long[(int) (currentday.size()+diff)];
				for(int j = 0; j < diff; j++) {
					days[j] = 0L;
					addCounter++;
				}
			}
			else {
				days = new Long[currentday.size()];
			}
			//vollständige liste mit allen daten und den leertagen
			for(int j = 0; j < currentday.size(); j++) {
				days[j+addCounter] = sdays[j];
			}
			BoxPlot bp = calcBox(days);
			bp.setName(getNameForDay(i));
			System.out.println("BoxPlotGenerator: Calculation results: "+getNameForDay(i)+
															" LQ: "+bp.getLowerQuartil()+
															" LW: "+bp.getLowerWhisker()+
															" UQ: "+bp.getUpperQuartil()+
															" UW: "+bp.getUpperWhisker()+
															" MED: "+bp.getMedian()
													);
			resultList[i] = bp;
		}
		return resultList;
	}
	
	/**
	 * 
	 * @return list with boxplots for the hours 0 - 24
	 */
	public BoxPlot[] calculateResultForHour() {
		BoxPlot[] resultList = new BoxPlot[24];
		
		for(int i = 0; i < 24; i++) {
			Long[] hours = null;
			HashMap<Date, Long> currenthour = hourmap.get(i);
			Date[] dates = currenthour.keySet().toArray(new Date[currenthour.size()]);
			Long[] shours = currenthour.values().toArray(new Long[currenthour.size()]);
			//berechnen wie viele tatsächlich tatsächlich eingetragen werden müssen
			long hourCount = (long)currenthour.size();
			if(hourCount <= 0) {
				BoxPlot bp = new BoxPlot();
				bp.setName(Integer.toString(i));
				resultList[i] = bp;
				continue;
			}
			Arrays.sort(dates);
			Date firstDay = dates[0];
			long diff = hourDiff(firstDay, max) + 1;
			diff = diff - hourCount;
			int addCounter = 0;
			if(diff >= 1) {
				hours = new Long[(int) (currenthour.size()+diff)];
				for(int j = 0; j < diff; j++) {
					hours[j] = 0L;
					addCounter++;
				}
			}
			else {
				hours = new Long[currenthour.size()];
			}
			//vollständige liste mit allen daten und den leertagen
			for(int j = 0; j < currenthour.size(); j++) {
				hours[j+addCounter] = shours[j];
			}
			BoxPlot bp = calcBox(hours);
			bp.setName(Integer.toString(i));
			resultList[i] = bp;
		}
		return resultList;
	}
	
	//berechnen der boxplot werte
	private BoxPlot calcBox(Long[] list) {
		BoxPlot result = new BoxPlot();
		//---SORTIEREN
		java.util.Arrays.sort(list);
		//---MEDIAN
		//gerade oder ungerade
		if(list.length%2 == 0) {
			//gerade
			int uw, ow;
			uw = (list.length/2)-1;
			ow = uw +1;
			Double m = new Double((list[uw] + list[ow]));
			m = m/2;
			result.setMedian(m);
		}
		else {
			//ungerade
			result.setMedian(list[(list.length/2)].doubleValue());
		}
		//---QUARTILE
		//1 & 2Quartile
		long q1, q2;
		if(list.length == 1) {
			q1 = 1;
			q2 = 1;
		}
		else {
			q1 = Math.round(0.25*(long)((list.length)+1));
			q2 = Math.round(0.75*(long)((list.length)+1));
		}
		Long i1 = new Long(q1-1);
		Long i2 = new Long(q2-1);
		result.setLowerQuartil(list[i1.intValue()].doubleValue());
		result.setUpperQuartil(list[i2.intValue()].doubleValue());
		result.setUpperWhisker(list[list.length-1].doubleValue());
		result.setLowerWhisker(list[0].doubleValue());
		
		return result;
	}
	
	//für das aktuallisieren des min und max dates
	private void addDate(Date date) {
		if(min == null) {
			min = date;
		}
		if(max == null) {
			max = date;
		}
		if(date.after(max)) {
			max = date;
		}
		if(date.before(min)) {
			min = date;
		}
		dates.add(date);
	}
	
	private long weekDiff(Date first, Date last) {
		long diff = 0;
		Calendar cal_1 = new GregorianCalendar();
		Calendar cal_2 = new GregorianCalendar();
		cal_1.setTime(first);                      // erster Zeitpunkt
		cal_2.setTime(last);                      // zweiter Zeitpunkt
		long time = cal_2.getTime().getTime() - cal_1.getTime().getTime();  // Differenz in ms
		long days = Math.round( (double)time / (24. * 60.*60.*1000.) );     // Differenz in Tagen
		diff = Math.round(days/7);
		return diff;
	}
	
	private long hourDiff(Date first, Date last) {
		//TODO BUG Hier irgendwo
		long diff = 0;
		Calendar cal_1 = new GregorianCalendar();
		Calendar cal_2 = new GregorianCalendar();
		cal_1.setTime(first);                      // erster Zeitpunkt
		cal_2.setTime(last);                      // zweiter Zeitpunkt
		long time = cal_2.getTime().getTime() - cal_1.getTime().getTime();  // Differenz in ms
		long days = Math.round( (double)time / (24. * 60.*60.*1000.) );     // Differenz in Tagen
		diff = Math.round(days);
		return diff;
	}
	
	private String getNameForDay(int day) {
		String name = "";
		switch(day) {
			case 0: name = "monday"; break;
			case 1: name = "tuesday"; break;
			case 2: name = "wednesday"; break;
			case 3: name = "thursday"; break;
			case 4: name = "friday"; break;
			case 5: name = "saturday"; break;
			case 6: name = "sunday"; break;
		default: name = "";
		}
		return name;
	}
}
