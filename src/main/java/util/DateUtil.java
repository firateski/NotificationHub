package util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {
    public static String DATE_PATTERN = "MM-dd-yyyy";

    public static long getDaysBetweenDates(Date date1, Date date2){
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);

        LocalDate localDate1 = LocalDate.parse(dateFormatter.format(date1), dtf);
        LocalDate localDate2 = LocalDate.parse(dateFormatter.format(date2), dtf);

        return ChronoUnit.DAYS.between(localDate1, localDate2);
    }
}
