package Util;

import Util.AccessManagerUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class AccessManagerUtilsTest {
    @Test
    public void should_return_equal_or_greater_than_30_days_for_given_dates(){
        Date date1 = null, date2 = null;
        try {
            date1 = new SimpleDateFormat(AccessManagerUtils.DATE_PATTERN).parse("01-01-2020");
            date2 = new SimpleDateFormat(AccessManagerUtils.DATE_PATTERN).parse("02-01-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long output = AccessManagerUtils.getDaysBetweenDates(date1, date2);

        assertTrue("Number of dates should equal or greater than 30 between dates",output >= 30);
    }

    @Test
    public void should_return_lower_than_30_days_for_given_dates(){
        Date date1 = null, date2 = null;
        try {
            date1 = new SimpleDateFormat(AccessManagerUtils.DATE_PATTERN).parse("01-01-2020");
            date2 = new SimpleDateFormat(AccessManagerUtils.DATE_PATTERN).parse("01-29-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long output = AccessManagerUtils.getDaysBetweenDates(date1, date2);

        assertTrue("Number of dates should lower than 30 between dates",output < 30);
    }
}
