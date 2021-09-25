package models;

import java.util.LinkedList;

public class GroupChats {
    public LinkedList<String> members;
    public LinkedList<Groupmessage> chats;
    public String groupName;

    public LinkedList<String> getMembers() {
        return members;
    }

    public void setMembers(LinkedList<String> members) {
        this.members = members;
    }

    public LinkedList<Groupmessage> getChats() {
        return chats;
    }

    public void setChats(LinkedList<Groupmessage> chats) {
        this.chats = chats;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public GroupChats(String groupName) {
        this.groupName = groupName;
        this.members=new LinkedList<>();
        this.chats=new LinkedList<>();
    }
}