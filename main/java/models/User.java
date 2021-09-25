package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class User {
    private static Logger logger = LogManager.getLogger(client.class);
    static long id;
    String username;
    String firstname;
    String lastname;
    private String password;
    String email;
    LinkedList<String> GroupChats;

    public LinkedList<String> getGroupChats() {
        return GroupChats;
    }

    public void setGroupChats(LinkedList<String> groupChats) {
        GroupChats = groupChats;
    }

    LinkedList<String > reportedusers;
    LinkedList<Tweet> reportedtweets;
    LinkedList<String> followers;
    LinkedList<String> followings;
    LinkedList<Tweet> usertweets;
    LinkedList<Tweet> followingstweet;
    LinkedList<Tweet> Likedtweet;
    LinkedList<Tweet> retweet;
    LinkedList<String> blackList;
    LinkedList<Notification> usernotif;
    LinkedList<Notification> systemnotif;
    LinkedList<Chat> userchats;
    LinkedList<String> muteduser;
    LinkedList<Category> categories;
    Boolean publicaccount;
    Boolean isactive;
    Boolean islastseen;
    String lastseen;
    LocalDateTime d;
    String birthday;
    String bio;
    String phonenumber;
    String profilepicUrl;

    public LinkedList<String> getReportedusers() {
        return reportedusers;
    }

    public void setReportedusers(LinkedList<String> reportedusers) {
        this.reportedusers = reportedusers;
    }

    public LinkedList<Tweet> getReportedtweets() {
        return reportedtweets;
    }

    public void setReportedtweets(LinkedList<Tweet> reportedtweets) {
        this.reportedtweets = reportedtweets;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public LinkedList<Category> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<Category> categories) {
        this.categories = categories;
    }

    public LinkedList<Chat> getUserchats() {
        return userchats;
    }

    public void setUserchats(LinkedList<Chat> userchats) {
        this.userchats = userchats;
    }

    public LinkedList<String> getMuteduser() {
        return muteduser;
    }

    public void setMuteduser(LinkedList<String> muteduser) {
        this.muteduser = muteduser;
    }

    public LinkedList<Tweet> getLikedtweet() {
        return Likedtweet;
    }

    public void setLikedtweet(LinkedList<Tweet> likedtweet) {
        Likedtweet = likedtweet;
    }

    public LinkedList<Tweet> getRetweet() {
        return retweet;
    }

    public void setRetweet(LinkedList<Tweet> retweet) {
        this.retweet = retweet;
    }

    public Boolean getIslastseen() {
        return islastseen;
    }

    public void setIslastseen(Boolean islastseen) {
        this.islastseen = islastseen;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Boolean getPublicaccount() {
        return publicaccount;
    }

    public void setLastseen(String lastseen) {
        d=LocalDateTime.now();
        this.lastseen = dateformat(d);
    }

    public String getLastseen() {
        return lastseen;
    }

    public User() {
    }

    public static void setId(long id) {
        User.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    public LinkedList<Tweet> getUsertweets() {
        return usertweets;
    }

    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                '}';
    }

    public void setUsertweets(Tweet usertweets) {
        this.usertweets.add(usertweets);
    }

    public void setFollowingstweet(LinkedList<Tweet> followingstweet) {
        this.followingstweet = followingstweet;
    }

    public void setUsernotif(LinkedList<Notification> usernotif) {
        this.usernotif = usernotif;
    }

    public void setSystemnotif(LinkedList<Notification> systemnotif) {
        this.systemnotif = systemnotif;
    }

    public void setPublicaccount(Boolean publicaccount) {
        this.publicaccount = publicaccount;
    }

    public LinkedList<Tweet> getFollowingstweet() {
        return followingstweet;
    }

    public void addusertweet(Tweet a){
        this.usertweets.add(a);
    }
    public void addfollowingstweet(Tweet a){
        this.followingstweet.add(a);
    }
    public void private_acc(){
        this.publicaccount=false;
    }
    public void public_acc(){
        this.publicaccount=true;
    }
    public static String dateformat(LocalDateTime d){
        return d.getYear()+"/"+d.getMonthValue()+"/"+d.getDayOfMonth()+":"+d.getHour()+":"+d.getMinute()+":"+d.getSecond();
    }

    public void setFollowers(LinkedList<String> followers) {
        this.followers = followers;
    }

    public void setFollowings(LinkedList<String> followings) {
        this.followings = followings;
    }

    public void setUsertweets(LinkedList<Tweet> usertweets) {
        this.usertweets = usertweets;
    }

    public void setBlackList(LinkedList<String> blackList) {
        this.blackList = blackList;
    }

    public LinkedList<String> getFollowers() {
        return followers;
    }

    public LinkedList<String> getFollowings() {
        return followings;
    }

    public LinkedList<String> getBlackList() {
        return blackList;
    }

    public LinkedList<Notification> getUsernotif() {
        return usernotif;
    }

    public LinkedList<Notification> getSystemnotif() {
        return systemnotif;
    }

    public String getProfilepicUrl() {
        return profilepicUrl;
    }

    public void setProfilepicUrl(String profilepicUrl) {
        this.profilepicUrl = profilepicUrl;
    }

    public User(String username, String firstname, String lastname, String email, String password) {
        logger.debug("in user from class user to make an object instance ");
        this.GroupChats=new LinkedList<>();
        this.profilepicUrl="";
        this.reportedtweets=new LinkedList<>();
        this.reportedusers=new LinkedList<>();
        this.bio="";
        this.phonenumber="" ;
        this.birthday="";
        this.categories=new LinkedList<>();
        this.userchats=new LinkedList<>();
        this.muteduser=new LinkedList<>();
        this.Likedtweet=new LinkedList<>();
        this.retweet=new LinkedList<>();
        this.islastseen=true;
        this.isactive=true;
        this.systemnotif=new LinkedList<>();
        this.usernotif=new LinkedList<>();
        this.lastseen=dateformat(LocalDateTime.now());
        this.publicaccount=true;
        this.followings=new LinkedList<>();
        this.followers =new LinkedList<>();
        this.blackList=new LinkedList<>();
        this.usertweets=new LinkedList<>();
        this.followingstweet=new LinkedList<>();
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password=password;
        id++;
    }
    public static void main(String[] args) {

    }





}
