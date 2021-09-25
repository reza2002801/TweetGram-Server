package shared;

import models.Notification;
//import server.model.Models.Notif;

import java.util.LinkedList;

public class NotifResponse {
    LinkedList<Notification> notifs;

    public NotifResponse(LinkedList<Notification> notifs){
        this.notifs = notifs;
    }

    public LinkedList<Notification> getNotifs() {
        return notifs;
    }
}
