package notification.channel.sms;

import exceptions.CompanyBlacklistedException;
import exceptions.NotSubscribedException;
import model.Company;
import notification.Notification;
import notification.NotificationDTO;
import SubscriptionManager.SubscriptionManager;

import java.util.List;

public class Sms implements Notification {
    @Override
    public void send(NotificationDTO notificationDTO) {
        SmsDTO smsDTO = (SmsDTO) notificationDTO;

        Company company = smsDTO.getCompany();

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, this);

        if (subscriptionManager.isCompanyBlacklisted()) throw new CompanyBlacklistedException("Company blacklisted", 1);
        if (!subscriptionManager.isSubscribed()) throw new NotSubscribedException("Not subscribed", 2);

        subscriptionManager.applyOperationCost();

        System.out.printf("SMS sent to %s: %s\n",
                smsDTO.getPhoneNumber(),
                smsDTO.getMessage());
    }

    @Override
    public void sendMultiple(List<NotificationDTO> notificationDTOList) {
        notificationDTOList.forEach(this::send);
    }
}

