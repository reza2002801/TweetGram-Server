package shared;

import models.Tweet;

import java.util.LinkedList;

public class ProfileResponse {
    private String username;
    private String FirstName;
    private String lastName;
    private String Bio;
    private String phoneNumber;
    private String lastSeen;
    private String BirthDate;
    private String picUrl;
    private LinkedList<Tweet> UserTweet;
    private Boolean isLastSeen;

    public void setIsLastSeen(Boolean lastSeen) {
        isLastSeen = lastSeen;
    }

    public ProfileResponse(String username, String firstName, String lastName, String bio, String phoneNumber, String lastSeen, String birthDate, String picUrl, LinkedList<Tweet> userTweet) {
        this.username = username;
        FirstName = firstName;
        this.lastName = lastName;
        Bio = bio;
        this.phoneNumber = phoneNumber;
        this.lastSeen = lastSeen;
        BirthDate = birthDate;
        this.picUrl = picUrl;
        UserTweet = userTweet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public LinkedList<Tweet> getUserTweet() {
        return UserTweet;
    }

    public void setUserTweet(LinkedList<Tweet> userTweet) {
        UserTweet = userTweet;
    }
    //    private LinkedList<String>
}
