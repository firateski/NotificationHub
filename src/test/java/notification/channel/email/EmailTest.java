package notification.channel.email;

import exceptions.CompanyBlacklistedException;
import exceptions.NotSubscribedException;
import model.Company;
import model.Subscription;
import notification.Notification;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class EmailTest {
    @Test
    public void should_throw_error_if_company_blacklisted() {
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -60);
        Date sixtyDaysAgoFromNow = calendar.getTime();

        Notification email = new Email();

        Subscription subscription = new Subscription();
        subscription.setChannel(email);
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);

        Company company = new Company();
        company.addSubscription(subscription);

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setCompany(company);

        CompanyBlacklistedException ex = assertThrows(CompanyBlacklistedException.class, () -> {
            email.send(emailDTO);
        });

        int output = ex.getErrorCode();

        assertEquals(1, output);
    }

    @Test
    public void should_throw_error_if_company_not_subscribed() {
        Notification email = new Email();

        Company company = new Company();

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setCompany(company);

        NotSubscribedException ex = assertThrows(NotSubscribedException.class, () -> {
            email.send(emailDTO);
        });

        int output = ex.getErrorCode();

        assertEquals(2, output);
    }
}
