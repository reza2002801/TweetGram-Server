package shared;

public class MiniProfileResponse {
    private String UserName;
    private String FirstName;
    private String LastName;
    private String PicUrl;

    public MiniProfileResponse(String userName, String firstName, String lastName, String picUrl) {
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        PicUrl = picUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
