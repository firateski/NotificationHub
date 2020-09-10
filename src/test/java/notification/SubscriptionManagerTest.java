package notification;

import model.Company;
import model.Subscription;
import notification.channel.email.Email;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SubscriptionManagerTest {
    @Test
    public void should_return_true_when_company_blacklisted() {
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

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, email);

        boolean output = subscriptionManager.isCompanyBlacklisted();

        assertTrue(output);
    }

    @Test
    public void should_return_false_when_company_not_blacklisted_yet() {
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -59);
        Date sixtyDaysAgoFromNow = calendar.getTime();

        Subscription subscription = new Subscription();
        subscription.setChannel(new Email());
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);

        Company company = new Company();
        company.addSubscription(subscription);

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, new Email());

        boolean output = subscriptionManager.isCompanyBlacklisted();

        assertFalse(output);
    }

    @Test
    public void should_return_true_if_quota_exceed(){
        Notification email = new Email();

        Subscription subscription = new Subscription();
        subscription.setChannel(email);
        subscription.setQuota(1000);
        subscription.setTotalUsage(1000);

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, email);
        boolean output = subscriptionManager.isQuotaExceed();
        assertTrue(output);
    }

    @Test
    public void should_return_false_if_quota_not_exceed(){
        Notification email = new Email();

        Subscription subscription = new Subscription();
        subscription.setChannel(email);
        subscription.setQuota(1000);
        subscription.setTotalUsage(999);

        Company company = new Company();
        company.addSubscription(subscription);

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, email);
        boolean output = subscriptionManager.isQuotaExceed();
        assertFalse(output);
    }

    @Test
    public void should_return_true_if_subscribed(){
        Subscription subscription = new Subscription();
        Notification email = new Email();
        subscription.setChannel(email);

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, email);
        boolean output = subscriptionManager.isSubscribed();
        assertTrue(output);
    }

    @Test
    public void should_return_false_if_not_subscribed(){
        Notification email = new Email();

        Company company = new Company();
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, email);
        boolean output = subscriptionManager.isSubscribed();
        assertFalse(output);
    }
}
