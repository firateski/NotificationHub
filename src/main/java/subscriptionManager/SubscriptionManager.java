package subscriptionManager;

import util.DateUtil;
import exceptions.NotSubscribedException;
import model.Company;
import model.Subscription;
import notification.Notification;

import java.util.Date;

public class SubscriptionManager {
    Company company;
    Notification notification;

    public SubscriptionManager(Company company, Notification notification) {
        this.company = company;
        this.notification = notification;
    }

    public Subscription getSubscription() {
        if(company.getSubscriptions() != null) {
            for (Subscription subscription : company.getSubscriptions()) {
                String currentNotificationType = subscription.getChannel().getClass().getName();
                String findNotificationType = notification.getClass().getName();

                if (currentNotificationType.equals(findNotificationType)) {
                    return subscription;
                }
            }
        }

        throw new NotSubscribedException("Not subscribed", 2);
    }

    public void applyOperationCost() {
        Subscription subscription = getSubscription();

        if (isQuotaExceed()) {
            switch (subscription.getSubscriptionType()) {
                case STANDARD:
                    renewSubscription();
                    break;
                case FLEX:
                    applyExceedCost();
                    break;
            }
        }

        subscription.setTotalUsage(subscription.getTotalUsage() + 1);
    }

    public boolean isCompanyBlacklisted() {
        Date paymentDate = getSubscription().getSubscriptionEndDate();
        Date today = new Date();
        boolean isPaymentDateExceed60Days = DateUtil.getDaysBetweenDates(paymentDate, today) >= 60;

        return !getSubscription().isPaid() && isPaymentDateExceed60Days;
    }

    public boolean isQuotaExceed() {
        return getSubscription().getTotalUsage() >= getSubscription().getQuota();
    }

    public boolean isSubscribed() {
        try {
            getSubscription();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void renewSubscription() {
        getSubscription().setTotalUsage(0);
        getSubscription().addBilledPrice(getSubscription().getSubscriptionPrice());
    }

    private void applyExceedCost() {
        getSubscription().addBilledPrice(getSubscription().getExceedPricePerOperation());
    }
}
