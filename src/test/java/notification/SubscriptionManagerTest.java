package notification;

import subscriptionManager.SubscriptionManager;
import model.Company;
import model.Subscription;
import model.SubscriptionType;
import notification.channel.email.Email;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.*;

public class SubscriptionManagerTest {
    @Test
    public void should_return_true_when_company_blacklisted() {
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -60);
        Date sixtyDaysAgoFromNow = calendar.getTime();

        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);

        Company company = new Company();
        company.addSubscription(subscription);

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());

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
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setPaid(false);
        subscription.setGetSubscriptionEndDate(sixtyDaysAgoFromNow);

        Company company = new Company();
        company.addSubscription(subscription);

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());

        boolean output = subscriptionManager.isCompanyBlacklisted();

        assertFalse(output);
    }

    @Test
    public void should_return_true_if_quota_exceed(){
        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setQuota(1000);
        subscription.setTotalUsage(1000);

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        boolean output = subscriptionManager.isQuotaExceed();
        assertTrue(output);
    }

    @Test
    public void should_return_false_if_quota_not_exceed(){
        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setQuota(1000);
        subscription.setTotalUsage(999);

        Company company = new Company();
        company.addSubscription(subscription);

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        boolean output = subscriptionManager.isQuotaExceed();
        assertFalse(output);
    }

    @Test
    public void should_return_true_if_subscribed(){
        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        boolean output = subscriptionManager.isSubscribed();
        assertTrue(output);
    }

    @Test
    public void should_return_false_if_not_subscribed(){
        Company company = new Company();
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        boolean output = subscriptionManager.isSubscribed();
        assertFalse(output);
    }

    @Test
    public void should_apply_operation_cost_on_standard_subscription(){
        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setSubscriptionType(SubscriptionType.STANDARD);
        subscription.setSubscriptionPrice(10);
        subscription.setQuota(100);
        subscription.setTotalUsage(0);

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        subscriptionManager.applyOperationCost();

        int output = subscriptionManager.getSubscription().getTotalUsage();

        assertEquals(1, output);
    }

    @Test
    public void should_apply_operation_cost_on_flex_subscription(){
        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setSubscriptionType(SubscriptionType.FLEX);
        subscription.setSubscriptionPrice(10);
        subscription.setQuota(100);
        subscription.setTotalUsage(0);

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        subscriptionManager.applyOperationCost();

        int output = subscriptionManager.getSubscription().getTotalUsage();

        assertEquals(1, output);
    }

    @Test
    public void should_renew_subscription_when_quota_exceed_on_standard_subscription(){
        double SUBSCRIPTION_PRICE = 10;

        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setSubscriptionType(SubscriptionType.STANDARD);
        subscription.setSubscriptionPrice(SUBSCRIPTION_PRICE);
        subscription.setQuota(100);
        subscription.setTotalUsage(100);

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        subscriptionManager.applyOperationCost();

        int outputTotalUsage = subscriptionManager.getSubscription().getTotalUsage();
        double outputBilledPrice = subscriptionManager.getSubscription().getBilledPrice();

        assertEquals(1, outputTotalUsage);
        assertEquals(SUBSCRIPTION_PRICE, outputBilledPrice);
    }

    @Test
    public void should_renew_subscription_when_quota_exceed_on_flex_subscription(){
        double SUBSCRIPTION_QUOTA_EXCEED_PRICE = 0.10d;

        Subscription subscription = new Subscription();
        subscription.setNotificationType(Email.class.getTypeName());
        subscription.setSubscriptionType(SubscriptionType.FLEX);
        subscription.setExceedPricePerOperation(SUBSCRIPTION_QUOTA_EXCEED_PRICE);
        subscription.setQuota(100);
        subscription.setTotalUsage(100);

        Company company = new Company();
        company.addSubscription(subscription);
        SubscriptionManager subscriptionManager = new SubscriptionManager(company, Email.class.getTypeName());
        subscriptionManager.applyOperationCost();

        int outputTotalUsage = subscriptionManager.getSubscription().getTotalUsage();
        double outputBilledPrice = subscriptionManager.getSubscription().getBilledPrice();

        assertEquals(101, outputTotalUsage);
        assertEquals(SUBSCRIPTION_QUOTA_EXCEED_PRICE, outputBilledPrice);
    }
}
