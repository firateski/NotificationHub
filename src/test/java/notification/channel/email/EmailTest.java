package notification.channel.email;

import exceptions.CompanyBlacklistedException;
import exceptions.NotSubscribedException;
import model.Company;
import model.Subscription;
import notification.Notification;
import notification.NotificationDTO;
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
        subscription.setNotificationType(Email.class.getTypeName());
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

    @Test
    public void should_throw_error_if_company_blacklisted_when_multiple_messaging() {
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -60);
        Date sixtyDaysAgoFromNow = calendar.getTime();

        Notification email = new Email();

        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);

        Company company = new Company();
        company.addSubscription(subscription);

        List<NotificationDTO> emailDTOList = new ArrayList<>();
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setCompany(company);

        emailDTOList.add(emailDTO);
        emailDTOList.add(emailDTO);
        emailDTOList.add(emailDTO);

        CompanyBlacklistedException ex = assertThrows(CompanyBlacklistedException.class, () -> {
            email.sendMultiple(emailDTOList);
        });

        int output = ex.getErrorCode();

        assertEquals(1, output);
    }

    @Test
    public void should_throw_error_if_company_not_subscribed_when_multiple_messaging() {
        Notification email = new Email();

        Company company = new Company();

        List<NotificationDTO> emailDTOList = new ArrayList<>();
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setCompany(company);

        emailDTOList.add(emailDTO);
        emailDTOList.add(emailDTO);
        emailDTOList.add(emailDTO);

        NotSubscribedException ex = assertThrows(NotSubscribedException.class, () -> {
            email.sendMultiple(emailDTOList);
        });

        int output = ex.getErrorCode();

        assertEquals(2, output);
    }
}
