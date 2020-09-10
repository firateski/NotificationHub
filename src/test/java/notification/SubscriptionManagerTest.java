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

        List<Subscription> subscriptionList = new ArrayList<>();

        Notification email = new Email();

        Subscription subscription = new Subscription();
        subscription.setChannel(email);
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);
        subscriptionList.add(subscription);

        Company company = new Company();
        company.setSubscriptions(subscriptionList);

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

        List<Subscription> subscriptionList = new ArrayList<>();

        Subscription subscription = new Subscription();
        subscription.setChannel(new Email());
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);
        subscriptionList.add(subscription);

        Company company = new Company();
        company.setSubscriptions(subscriptionList);

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

        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription);

        Company company = new Company();
        company.setSubscriptions(subscriptionList);
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

        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription);

        Company company = new Company();
        company.setSubscriptions(subscriptionList);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, email);
        boolean output = subscriptionManager.isQuotaExceed();
        assertFalse(output);
    }

    @Test
    public void should_return_true_if_subscribed(){
        List<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription = new Subscription();
        Notification email = new Email();
        subscription.setChannel(email);
        subscriptionList.add(subscription);

        Company company = new Company();
        company.setSubscriptions(subscriptionList);
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
