package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TimeLine {
    private static Logger logger = LogManager.getLogger(TimeLine.class);

    public static void del_tweet(String s)throws IOException{
        logger.debug("in del_tweet from class TimeLine on"+s);

        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        int i=0;
        while(i< MainPage.acc_info(e, login.UserName).getFollowingstweet().size()){
            if(MainPage.acc_info(e, login.UserName).getFollowingstweet().get(i).getUsername().equals(s)){
                MainPage.acc_info(e, login.UserName).getFollowingstweet().remove(i);
            }else {
                i++;
            }
        }

    }
    public static void addlikedtweettofollowers(Tweet n)throws IOException{
        logger.debug("in addlikedtweettofollowers from class TimeLine on"+n);

        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        for(int i = 0; i< MainPage.acc_info(e, login.UserName).getFollowers().size(); i++){
            MainPage.acc_info(e, MainPage.acc_info(e, login.UserName).getFollowers().get(i)).getFollowingstweet().add(n);
        }
    }
    public static void commentviewer(Comment c) throws IOException{
        logger.debug("in commentviewer from class TimeLine on"+c);

        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        logger.info("read from file");
        if(c.getComments()==null||c.getComments().size()==0){
            System.out.println("nothing to show!");
        }else {
            int i=0;
            while (true){
                System.out.println(c.getComments().get(i).getDatetime());
                System.out.println(c.getComments().get(i).getTweetphrase());
                String input=scan.nextLine();
                if(input.equals("back")){
                    break;
                }else if(input.equals("next")){
                    if(i==c.getComments().size()-1){
                        System.out.println("no more comment");
                    }else {
                        i++;
                    }
                }else if(input.equals("previous")){
                    if(i==0){
                        System.out.println("no more comment");
                    }else {
                        i--;
                    }
                }else if(input.equals("comments")){
                    logger.info("show comments");
                    commentviewer(c.getComments().get(i));
                }else if(input.equals("WriteComment")){
                    System.out.println("enter your comment:");
                    String input1=scan.nextLine();
                    Comment n=new Comment(input1, login.UserName);
                    c.getComments().get(i).getComments().add(n);
                }
                Writer w=new FileWriter("Accounts.json ");
                g.toJson(e,w);
                w.close();
                logger.info("write to file");
            }
        }

    }
    public static void updater(Tweet a)throws IOException{
        logger.debug("in updater from class TimeLine on"+a);

        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        logger.info("read from file");
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
        Writer w=new FileWriter("Accounts.json ");
        g.toJson(e,w);
        w.close();
    }
    public static void stg()throws IOException{
        logger.debug("in stg from class TimeLine on");

        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        logger.info("read to file");
//        String d=scan.nextLine();
        LinkedList<Tweet> otherstweet= MainPage.acc_info(e, login.UserName).getFollowingstweet();
        int i=0;
        System.out.println("Your tweets are as follows!");
        String o="";
//        d=scan.nextLine();
        if(otherstweet!=null && otherstweet.size()!=0){

            while(otherstweet!=null && !o.equals("back")){
                g=new GsonBuilder().setPrettyPrinting().create();
                r= Files.newBufferedReader(Paths.get("Accounts.json"));
                e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
                r.close();
                otherstweet= MainPage.acc_info(e, login.UserName).getFollowingstweet();
                if(MainPage.acc_info(e,otherstweet.get(i).getUsername()).getIsactive()){
                    if(otherstweet.get(i).getIsretweet()){
                        System.out.println(otherstweet.get(i).getDatetime());
                        System.out.println(otherstweet.get(i).getUsername()+" retweetedfrom "+otherstweet.get(i).getRetweetuser());
                        System.out.println(otherstweet.get(i).getTweetphrase());
                    }else {
                        System.out.println(otherstweet.get(i).getDatetime());
                        System.out.println(otherstweet.get(i).getUsername());
                        System.out.println(otherstweet.get(i).getTweetphrase());
                    }
                }else {
                    System.out.println("user has deactive its account you cant see this twit");
                }

                while (!o.equals("back")){
                    System.out.println("back/Next/Previous/WriteComment/ViewComments/ReTweet/Forward/Mute/SeeProfile/Report/like/showlikeduser");
                    String input=scan.nextLine();
                    if(input.equals("back")){
                        o="back";
                        break;
                    }else if(input.equals("Report")){
                        MainPage.acc_info(e, login.UserName).getReportedtweets().add(otherstweet.get(i));
                        System.out.println("Done !");
                    }
                    else if(input.equals("Next")){
                        if(i>=otherstweet.size()-1){
                            System.out.println("no more tweet!");
                        }else {
                            i++;
                            break;
                        }
                    }else if (input.equals("Previous")){
                        if(i==0){
                            System.out.println("no more tweet!");
                        }else {
                            i--;
                            break;
                        }
                    }else if (input.equals("showlikeduser") ){
                        if (otherstweet.get(i).getLikeuser()!=null&&otherstweet.get(i).getLikeuser().size()!=0){
                            for (int t=0;t<otherstweet.get(i).getLikeuser().size();t++){
                                System.out.println(otherstweet.get(i).getLikeuser().get(t));
                            }
                        }else {
                            System.out.println("empty liked user list");
                        }
                    }
                    else if(input.equals("WriteComment")){
                        System.out.println("Write Your Comment :");
                        String input1=scan.nextLine();
                        Comment newcomment=new Comment(input1, login.UserName);
//                        LinkedList<comment> p=MainPage.acc_info(e,login.UserName).getFollowingstweet().get(i).getComments();
//                        p.add(newcomment);
//                        System.out.println(p);
                        MainPage.acc_info(e, login.UserName).getFollowingstweet().get(i).getComments().add(newcomment);
                        System.out.println("Done!");
//                        Writer w=new FileWriter("Accounts.json ");
//                        g.toJson(e,w);
//                        w.close();
                    }else if(input.equals("ReTweet")){
                        if(otherstweet.get(i).isretweet){
                            Tweet a=new Tweet(otherstweet.get(i).getTweetphrase(), login.UserName);
                            a.setIsretweet(true);
                            a.setRetweetuser(otherstweet.get(i).retweetuser);
                            MainPage.acc_info(e, login.UserName).getUsertweets().add(a);
                            MainPage.acc_info(e, login.UserName).getRetweet().add(a);
                            MainPage.add_com_to_follower(e,a, MainPage.acc_info(e, login.UserName));
                        }else {
                            Tweet a=new Tweet(otherstweet.get(i).getTweetphrase(), login.UserName);
                            a.setIsretweet(true);
                            a.setRetweetuser(otherstweet.get(i).getUsername());
                            MainPage.acc_info(e, login.UserName).getUsertweets().add(a);
                            MainPage.acc_info(e, login.UserName).getRetweet().add(a);
                            MainPage.add_com_to_follower(e,a, MainPage.acc_info(e, login.UserName));

                        }

                    }else if(input.equals("Mute")){
                        MainPage.acc_info(e, login.UserName).getMuteduser().add(otherstweet.get(i).getUsername());
                        del_tweet(otherstweet.get(i).getUsername());
                        System.out.println("Done !");
                        g=new GsonBuilder().setPrettyPrinting().create();
                        r= Files.newBufferedReader(Paths.get("Accounts.json"));
                        e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
                        r.close();
                    }else if(input.equals("like")){
                        MainPage.acc_info(e, login.UserName).getLikedtweet().add(otherstweet.get(i));
                        addlikedtweettofollowers(otherstweet.get(i));
                        MainPage.acc_info(e, login.UserName).getFollowingstweet().get(i).getLikeuser().add(login.UserName);
                        System.out.println("done!");
                    }else if (input.equals("SeeProfile")){
                        Profile.opr_on_profile(MainPage.acc_info(e,otherstweet.get(i).getUsername()));
                    }else if (input.equals("Forward")){
                        String mess=otherstweet.get(i).getTweetphrase();
                        ChatPage.group_message(mess);
                        g=new GsonBuilder().setPrettyPrinting().create();
                        r= Files.newBufferedReader(Paths.get("Accounts.json"));
                        e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
                        r.close();
                    }

                    else if(input.equals("ViewComments")){
                        int j=0;
                        while (true){
                            System.out.println("back/next/previous/comments/WriteComment");
                            System.out.println(otherstweet.get(i).getComments().get(j).getDatetime());
                            System.out.println(otherstweet.get(i).getComments().get(j).getTweetphrase());
                            String input2=scan.nextLine();
                            if(input2.equals("back")){
                                break ;
                            }else if(input2.equals("next")){
                                if(j==otherstweet.get(i).getComments().size()-1){
                                    System.out.println("no more comment");
                                }else {
                                    j++;
                                }
                            }else if(input2.equals("previous")){
                                if(j==0){
                                    System.out.println("no more comment");
                                }else {
                                    j--;
                                }
                            }
                            else if(input2.equals("comments")){
                                commentviewer(otherstweet.get(i).getComments().get(j));
                            }else if(input2.equals("WriteComment")){
                                System.out.println("enter your comment:");
                                String input1=scan.nextLine();
                                Comment n=new Comment(input1, login.UserName);
                                MainPage.acc_info(e, login.UserName).getFollowingstweet().get(i).getComments().get(j).getComments().add(n);
                                System.out.println("done!");
                            }

                            Writer w=new FileWriter("Accounts.json ");
                            g.toJson(e,w);
                            w.close();
                            updater(MainPage.acc_info(e, login.UserName).followingstweet.get(i));
                        }

                    }

                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                    updater(MainPage.acc_info(e, login.UserName).followingstweet.get(i));
                }



            }

        }else{
            System.out.println("You have no tweet");
        }
        Writer w=new FileWriter("Accounts.json ");
        g.toJson(e,w);
        w.close();
        logger.info("write to file");

    }
}
