package notification;

import model.Company;

import java.util.ArrayList;
import java.util.List;

public interface Notification {
    void send(NotificationDTO notificationDTO);

    void sendMultiple(List<NotificationDTO> notificationDTOList);
}
