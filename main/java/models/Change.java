package models;

//import utils.CurrentClient;

import java.util.LinkedList;

public class Change {
    private Boolean isPublic;
    private Boolean isLastSeen;
    private Boolean isActive;
    private LinkedList<message> messages;
    private LinkedList<Groupmessage> groupmessages;
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Change(Boolean isPublic, Boolean isLastSeen, Boolean isActive, LinkedList<message> messages, LinkedList<Groupmessage> groupmessages, String newPassword) {
        this.isPublic = isPublic;
        this.isLastSeen = isLastSeen;
        this.isActive = isActive;
        this.messages = messages;
        this.groupmessages = groupmessages;
        this.newPassword = newPassword;
    }

    public LinkedList<Groupmessage> getGroupmessages() {
        return groupmessages;
    }

    public void setGroupmessages(LinkedList<Groupmessage> groupmessages) {
        this.groupmessages = groupmessages;
    }


    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getLastSeen() {
        return isLastSeen;
    }

    public void setLastSeen(Boolean lastSeen) {
        isLastSeen = lastSeen;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LinkedList<message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<message> messages) {
        this.messages = messages;
    }
}
