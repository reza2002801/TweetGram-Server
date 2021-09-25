package utils;

import models.Tweet;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static utils.AccountsMethods.findAccount;
import static utils.AccountsMethods.findAccount;

public class TweetMethods {
    private static Logger logger = LogManager.getLogger(TweetMethods.class);
    public static void addTweetToUserFollwers(Tweet newTweet, LinkedList<String> followers) throws IOException {
        logger.debug("in addTweetToUserFollwers from TweetMethods class on values"+newTweet+"and"+followers);
        List<User> accounts=new Filemethods().loadFromFile();
        for (int i=0; i <followers.size();i++){
            findAccount(accounts,followers.get(i)).getFollowingstweet().add(newTweet);
        }
        new Filemethods().saveToFile(accounts);
    }
    public static int indexOfTweet(Tweet t, LinkedList<Tweet> tweetlist){
        logger.debug("in indexOfTweet from TweetMethods class on values"+t+"and"+tweetlist);
        for (int i=0;i<tweetlist.size();i++){
            if(tweetlist.get(i).getDatetime().equals(t.getDatetime())){
                return i;
            }
        }
        return -1;
    }
    public static void addLikedTweetToFollowers(Tweet n,String UserName)throws IOException{
        logger.debug("in addLikedTweetToFollowers from TweetMethods class on values"+n);
//        logger.debug("in addlikedtweettofollowers from class TimeLine on"+n);

//        Scanner scan=new Scanner(System.in);
//        Gson g=new GsonBuilder().setPrettyPrinting().create();
//        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
//        List<user> e = g.fromJson(r, new TypeToken<List<user>>() {}.getType());
//        r.close();
        List<User> a=new Filemethods().loadFromFile();
        for(int i = 0; i< findAccount(a,UserName).getFollowers().size(); i++){
            findAccount(a, findAccount(a,UserName).getFollowers().get(i)).getFollowingstweet().add(n);
//            MainPage.acc_info(e, AccountsMethods.findAccount(a,LoginController.USERNAME).getFollowers().get(i)).getFollowingstweet().add(n);
        }

    }
    public static void updateTweet(Tweet a)throws IOException {
        logger.debug("in updateTweet from TweetMethods class on values"+a);

//        logger.debug("in updater from class TimeLine on"+a);

        List<User> e=new Filemethods().loadFromFile();
//        logger.info("read from file");
        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.get(i).getUsertweets().size();j++){
                if( a.getTweetphrase().equals(e.get(i).getUsertweets().get(j).getTweetphrase())){
                    e.get(i).getUsertweets().remove(j);
                    e.get(i).getUsertweets().add(j,a);
                }
            }
        }
        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.get(i).getFollowingstweet().size();j++){
                if( a.getTweetphrase().equals(e.get(i).getFollowingstweet().get(j).getTweetphrase())){
                    e.get(i).getFollowingstweet().remove(j);
                    e.get(i).getFollowingstweet().add(j,a);
                }
            }
        }
        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.get(i).getLikedtweet().size();j++){
                if( a.getTweetphrase().equals(e.get(i).getLikedtweet().get(j).getTweetphrase())){
                    e.get(i).getLikedtweet().remove(j);
                    e.get(i).getLikedtweet().add(j,a);
                }
            }
        }
        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.get(i).getRetweet().size();j++){
                if( a.getTweetphrase().equals(e.get(i).getRetweet().get(j).getTweetphrase())){
                    e.get(i).getRetweet().remove(j);
                    e.get(i).getRetweet().add(j,a);
                }
            }
        }
        new Filemethods().saveToFile(e);
    }
    public static void addRetweetToFollowers(List<User> e, Tweet t, User b){
        logger.debug("in addRetweetToFollowers from TweetMethods class on values"+e+"and"+b);
//        logger.debug("in add_com_to_follower from MainPage class on values"+e+"and"+t+"and"+b);

        LinkedList<String> f=b.getFollowers();
        for(int i=0;i<f.size();i++){
            findAccount(e,f.get(i)).getFollowingstweet().add(t);
//            e.get(index(e,f.get(i))).addfollowingstweet(t);
        }
    }

}
