package bi.bigroup.life.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bi.bigroup.life.R;

import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class DateUtils {
    public static final DateFormat BIRTHDATE_DISPLAY_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private static final SimpleDateFormat FEED_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat SERVICES_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final DateFormat TASKS_SEVICES_DISPLAY_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    public static String getBirthDateStr(String birthDate) {
        Date origDate = null;
        try {
            origDate = FEED_DATE_FORMAT.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return BIRTHDATE_DISPLAY_FORMAT.format(origDate).toLowerCase();
    }

    public static String getTasksDate(String givenDate) {
        Date origDate = null;
        try {
            origDate = FEED_DATE_FORMAT.parse(givenDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TASKS_SEVICES_DISPLAY_FORMAT.format(origDate).toLowerCase();
    }

    public static String getServicesDate(String givenDate) {
        Date origDate = null;
        try {
            origDate = SERVICES_DATE_FORMAT.parse(givenDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return SERVICES_DATE_FORMAT.format(origDate).toLowerCase();
    }

    public static String dateToString(Date date, SimpleDateFormat format) {
        return date != null ? format.format(date) : EMPTY_STR;
    }

    public static boolean isAfterToday(Date dateSpecified) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        return dateSpecified.after(today);
    }

    public static boolean isTheSameDay(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        Calendar c1 = toCalendar(d1);
        Calendar c2 = toCalendar(d2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String getDisplayableTime(String origDateStr, Context context) {
        if (origDateStr.length() > 0) {
            try {
                Date origDate = FEED_DATE_FORMAT.parse(origDateStr);
                long origTmstmp = origDate.getTime();

                Calendar origCalendar = Calendar.getInstance();
                origCalendar.setTime(origDate);
                int origDay = origCalendar.get(Calendar.DAY_OF_MONTH);
                int origMonth = origCalendar.get(Calendar.MONTH);
                int origYear = origCalendar.get(Calendar.YEAR);
                int origHours = origCalendar.get(Calendar.HOUR_OF_DAY);
                int origMinutes = origCalendar.get(Calendar.MINUTE);
                origCalendar.get(Calendar.SECOND); //number of seconds


                long difference = 0;
                long mDate = java.lang.System.currentTimeMillis();
                Date currDate = new Date();
                Calendar currCalendar = Calendar.getInstance();
                currCalendar.setTime(currDate);
                int currDay = currCalendar.get(Calendar.DAY_OF_MONTH);
//		    int currMonth = currCalendar.get(Calendar.MONTH);
                int currYear = currCalendar.get(Calendar.YEAR);
//		    int currHours = currCalendar.get(Calendar.HOUR_OF_DAY);
//		    int currMinutes = currCalendar.get(Calendar.MINUTE);

                //Yesterday
                currCalendar.add(Calendar.DATE, -1);
                Date yesterdayDate = currCalendar.getTime();
                Calendar yesterdayCalendar = Calendar.getInstance();
                yesterdayCalendar.setTime(yesterdayDate);
                int yesterdayDay = currCalendar.get(Calendar.DAY_OF_MONTH);
//		    int yesterdayMonth = currCalendar.get(Calendar.MONTH);
                int yesterdayYear = currCalendar.get(Calendar.YEAR);
//		    int yesterdayHours = currCalendar.get(Calendar.HOUR_OF_DAY);
//		    int yesterdayMinutes = currCalendar.get(Calendar.MINUTE);

                if (mDate > origTmstmp) {
                    difference = mDate - origTmstmp;
                    final long seconds = difference / 1000;
                    final long minutes = seconds / 60;
                    final long hours = minutes / 60;
                    final long days = hours / 24;
                    final long months = days / 31;
                    final long years = days / 365;

                    if (seconds < 0)
                        return EMPTY_STR;

                    else if (seconds < 86400 && origDay == currDay
                            && origYear == currYear) {
                        return (origHours < 10 ? "0" : "") + origHours +
                                ":" +
                                (origMinutes < 10 ? "0" : "") + origMinutes;

                    } else if (seconds < 172800 && origDay == yesterdayDay
                            && origYear == yesterdayYear)
                        return context.getString(R.string.label_yesterday) + " " +
                                (origHours < 10 ? "0" : "") + origHours +
                                ":" +
                                (origMinutes < 10 ? "0" : "") + origMinutes;

                    else if (origYear != currYear)
                        return getDateStr(origDay, origMonth, origYear, origHours, origMinutes, true, context);
                    else
                        return getDateStr(origDay, origMonth, origYear, origHours, origMinutes, false, context);

                }
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getDateStr(int day, int month, int year, int hours, int minutes, boolean isContainYear, Context context) {
        String arrMonth[] = context.getResources().getStringArray(R.array.months_array_long);
        if (isContainYear)
            // Longer date with year
            return day + " " + arrMonth[month] + ". " + year + ", " +
                    (hours < 10 ? "0" : "") + hours +
                    ":" +
                    (minutes < 10 ? "0" : "") + minutes;

        else
            // Longer date without year
            return day + " " + arrMonth[month] + ". " +
                    (hours < 10 ? "0" : "") + hours +
                    ":" +
                    (minutes < 10 ? "0" : "") + minutes;

    }
}
