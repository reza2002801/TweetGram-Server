package models;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Tweet {
    String tweetphrase;
    String username;
    LocalDateTime d;
    String datetime;
    Boolean isretweet;
    String retweetuser;
    LinkedList<Comment> comments;
    LinkedList<String> likeuser;

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    String pictureLink;

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    int reportNumber;

    public LinkedList<Comment> getComments() {
        return comments;
    }

    public void setComments(LinkedList<Comment> comments) {
        this.comments = comments;
    }

    public String getRetweetuser() {
        return retweetuser;
    }

    public void setRetweetuser(String retweetuser) {
        this.retweetuser = retweetuser;
    }

    public Boolean getIsretweet() {
        return isretweet;
    }

    public void setIsretweet(Boolean isretweet) {
        this.isretweet = isretweet;
    }

    public LinkedList<String> getLikeuser() {
        return likeuser;
    }

    public void setLikeuser(LinkedList<String> likeuser) {
        this.likeuser = likeuser;
    }

    public Tweet() {
    }

    public Tweet(String tweetphrase, String username) {
        this.pictureLink="";
        this.reportNumber=0;
        this.retweetuser="";
        this.isretweet=false;
        this.likeuser=new LinkedList<>();
        this.datetime=dateformat(LocalDateTime.now());
        this.tweetphrase = tweetphrase;
        this.username=username;
        this.comments=new LinkedList<>();
    }
    public String dateformat(LocalDateTime d){
        return d.getYear()+"/"+d.getMonthValue()+"/"+d.getDayOfMonth()+":"+d.getHour()+":"+d.getMinute()+":"+d.getSecond();
    }



    public String getTweetphrase() {
        return tweetphrase;
    }


    public void setTweetphrase(String tweetphrase) {
        this.tweetphrase = tweetphrase;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUsername() {
        return username;
    }

    public String getDatetime() {
        return datetime;
    }
}
