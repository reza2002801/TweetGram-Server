package models;

public class Groupmessage {
    private String message;
    private String GroupName;
    private String UserName;
    private String PictureLink;

    public Groupmessage(String message, String groupName, String userName, String pictureLink) {
        this.message = message;
        GroupName = groupName;
        UserName = userName;
        PictureLink = pictureLink;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPictureLink() {
        return PictureLink;
    }

    public void setPictureLink(String pictureLink) {
        PictureLink = pictureLink;
    }
}
