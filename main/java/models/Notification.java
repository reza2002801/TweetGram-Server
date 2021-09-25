package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Notification {
    private static Logger logger = LogManager.getLogger(Notification.class);
    String message;
    LocalDateTime d;
    String datetime;
    String username;

    public Notification() {
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
    public String dateformat(LocalDateTime d){
        return d.getYear()+"/"+d.getMonthValue()+"/"+d.getDayOfMonth()+":"+d.getHour()+":"+d.getMinute()+":"+d.getSecond();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public Notification(String username, String message) {
        logger.debug("creating notification on"+username+","+message);
        datetime=dateformat(LocalDateTime.now());
        this.username=username;
        this.message = message;
    }
}
