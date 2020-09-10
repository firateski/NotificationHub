package notification;

import java.util.List;

public interface Notification {
    void send(NotificationDTO notificationDTO);

    void sendMultiple(List<NotificationDTO> notificationDTOList);
}
