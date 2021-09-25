package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class Chat {
    private static Logger logger = LogManager.getLogger(Chat.class);

    String contactname;
    LinkedList<message> messages;
    String username;
    int unreadmessage;
    Boolean isunread;

    public int getUnreadmessage() {
        return unreadmessage;
    }

    public void setUnreadmessage(int unreadmessage) {
        this.unreadmessage = unreadmessage;
    }

    public Boolean getIsunread() {
        return isunread;
    }

    public void setIsunread(Boolean isunread) {
        this.isunread = isunread;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Chat(String contactname, String username) {
        logger.debug("making chat instance");
        this.unreadmessage=0;
        this.isunread=false;
        this.username=username;
        this.contactname = contactname;
        this.messages = new LinkedList<>();
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public LinkedList<message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<message> messages) {
        this.messages = messages;
    }
}
