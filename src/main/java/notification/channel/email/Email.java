package notification.channel.email;

import exceptions.CompanyBlacklistedException;
import exceptions.NotSubscribedException;
import model.Company;
import notification.Notification;
import notification.NotificationDTO;
import notification.SubscriptionManager;

import java.util.List;

public class Email implements Notification {
    @Override
    public void send(NotificationDTO notificationDTO) {
        EmailDTO emailDTO = (EmailDTO) notificationDTO;
        Company company = emailDTO.getCompany();

        SubscriptionManager subscriptionManager = new SubscriptionManager(company, this);

        if (subscriptionManager.isCompanyBlacklisted()) throw new CompanyBlacklistedException("Company blacklisted", 1);
        if (!subscriptionManager.isSubscribed()) throw new NotSubscribedException("Not subscribed", 2);

        subscriptionManager.applyProcessCost();

        System.out.printf("E-Mail sent to %s from %s with subject %s: %s\n",
                emailDTO.getEmailTo(),
                emailDTO.getEmailFrom(),
                emailDTO.getSubject(),
                emailDTO.getMessage());
    }

    @Override
    public void sendMultiple(List<NotificationDTO> notificationDTOList) {
        notificationDTOList.forEach(this::send);
    }
}

