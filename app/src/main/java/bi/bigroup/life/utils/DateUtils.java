package bi.bigroup.life.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class DateUtils {
    public static final DateFormat BIRTHDATE_DISPLAY_FORMAT = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_T_Z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DD_MM_YYYY_HH_MM = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    public static String getFinishPostnatalDate(Date date) {
        return DATE_FORMAT_T_Z.format(date);
    }

    public static String getBirthDateString(Date date) {
        return BIRTHDATE_DISPLAY_FORMAT.format(date).toLowerCase();
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
}
