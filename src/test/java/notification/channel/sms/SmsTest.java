package notification.channel.sms;

import exceptions.CompanyBlacklistedException;
import exceptions.NotSubscribedException;
import model.Company;
import model.Subscription;
import notification.Notification;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SmsTest {
    @Test
    public void should_throw_error_if_company_blacklisted(){
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -60);
        Date sixtyDaysAgoFromNow = calendar.getTime();

        List<Subscription> subscriptionList = new ArrayList<>();

        Notification sms = new Sms();

        Subscription subscription = new Subscription();
        subscription.setChannel(sms);
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);
        subscriptionList.add(subscription);

        Company company = new Company();
        company.setSubscriptions(subscriptionList);

        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setCompany(company);

        CompanyBlacklistedException ex = assertThrows(CompanyBlacklistedException.class, () -> {
            sms.send(smsDTO);
        });

        int output = ex.getErrorCode();

        assertEquals(1, output);
    }

    @Test
    public void should_throw_error_if_company_not_subscribed(){
        Notification sms = new Sms();

        Company company = new Company();

        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setCompany(company);

        NotSubscribedException ex = assertThrows(NotSubscribedException.class, () -> {
            sms.send(smsDTO);
        });

        int output = ex.getErrorCode();

        assertEquals(2, output);
    }
}
