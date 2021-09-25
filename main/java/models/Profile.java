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

public class Profile {
    private static Logger logger = LogManager.getLogger(Profile.class);
    public static void transfertweets(User a, User b){
        logger.debug("in transfertweets from class profile on"+a+"and"+b);
        LinkedList<Tweet> f=a.getUsertweets();
        LinkedList<Tweet> h=a.getLikedtweet();
        for (int i=0;i<f.size();i++){
            b.getFollowingstweet().add(f.get(i));
        }
        for (int i=0;i<h.size();i++){
            b.getFollowingstweet().add(h.get(i));
        }


    }
    public static void delete_tweet_blacklist(LinkedList<Tweet> t, String username){
        logger.debug("in delete_tweet_blacklist from class profile on"+t+"and"+username);
        for(int i=0;i<t.size();i++){
            if(t.get(i).getUsername().equals(username)){
                t.remove(t.get(i));
            }
        }
    }
    public static void opr_on_profile(User a) throws IOException {
        logger.debug("in opr_on_profile from class profile on"+a);

        System.out.println("profile(back/follow/unfollow/move_to_blacklist/out_from_blacklist/report/showchat)");
        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();

        System.out.println("username: "+ a.getUsername());
        System.out.println("fullName: "+a.getFirstname()+a.getLastname());
        if(MainPage.acc_info(e, login.UserName).getBlackList().contains(a.getUsername())){
            System.out.println("You have blocked this user");
        }

        if(MainPage.acc_info(e, login.UserName).getFollowings().contains(a.getUsername())){
            System.out.println("You have already follow this user");
            if(MainPage.acc_info(e,a.getUsername()).getIslastseen()){
                System.out.println( "status : online at "+a.getLastseen());
            }else{
                System.out.println("last seen recently");
            }

        }
        else{
            System.out.println("You have not followed this user");
            System.out.println("status :last seen recently");
        }if(MainPage.acc_info(e, login.UserName).getFollowings().contains(a.getUsername())){
            if(a.getPublicaccount()){
                System.out.println("Account mode :public");
            }
            else {
                System.out.println("Account mode :private");
            }

        }else{
            if(a.getPublicaccount()){
                System.out.println("Account mode :public");
            }
            else {
                System.out.println("Account mode :private");
            }
        }

        String input=scan.nextLine();
        while (true){
            if(input.equals("back")){
                break;
            }
            else if(input.equals("follow")&& !MainPage.acc_info(e, login.UserName).getFollowings().contains(a.getUsername())){
                logger.info("follow user");
                if(!a.getBlackList().contains(login.UserName)){
                    if(a.getPublicaccount()){
                        MainPage.acc_info(e,a.getUsername()).getFollowers().add(login.UserName);
                        MainPage.acc_info(e, login.UserName).getFollowings().add(a.getUsername());
                        transfertweets(a,MainPage.acc_info(e, login.UserName));
                        Notification n=new Notification(login.UserName,"has started to follow you ");
                        MainPage.acc_info(e,a.getUsername()).getSystemnotif().add(n);
                        System.out.println("done!");
                    }else{
                        Notification n=new Notification(login.UserName,"has requested to follow you ");
                        Notification m=new Notification(a.getUsername(),"You have sent a follow request for ");
                        MainPage.acc_info(e,a.getUsername()).getUsernotif().add(n);
                        MainPage.acc_info(e, login.UserName).getUsernotif().add(m);
                    }

                }else{
                    System.out.println("You are in his/her blacklist !");
                }

            }else if(input.equals("unfollow")&& MainPage.acc_info(e, login.UserName).getFollowings().contains(a.getUsername())){
                logger.info("unfollow user");
                MainPage.acc_info(e, login.UserName).getFollowings().remove(a.getUsername());
                MainPage.acc_info(e,a.getUsername()).getFollowers().remove(login.UserName);
                delete_tweet_blacklist(MainPage.acc_info(e, login.UserName).getFollowingstweet(),a.getUsername());
                Notification n=new Notification(login.UserName,"has stopped following you ");
                MainPage.acc_info(e,a.getUsername()).getSystemnotif().add(n);
                System.out.println("done");
            }else if(input.equals("move_to_blacklist")){
                logger.info("block user");
                MainPage.acc_info(e, login.UserName).getFollowings().remove(a.getUsername());
                MainPage.acc_info(e, login.UserName).getFollowers().remove(a.getUsername());
                MainPage.acc_info(e, login.UserName).getBlackList().add(a.getUsername());
                MainPage.acc_info(e,a.getUsername()).getFollowers().remove(a.getUsername());
                MainPage.acc_info(e,a.getUsername()).getFollowings().remove(a.getUsername());
                delete_tweet_blacklist(MainPage.acc_info(e, login.UserName).getFollowingstweet(),a.getUsername());
                System.out.println("done !");
            }else if(input.equals("report")){
                MainPage.acc_info(e, login.UserName).getReportedusers().add(a.getUsername());
                System.out.println("Done !");
            }else if(input.equals("out_from_blacklist")){
                logger.info("unblock user");
                MainPage.acc_info(e, login.UserName).getBlackList().remove(a.getUsername());
                System.out.println("done !");
            }else if(input.equals("unmute")){
                logger.info("unmute user");
                MainPage.acc_info(e, login.UserName).getMuteduser().remove(a.getUsername());
                transfertweets(a,MainPage.acc_info(e, login.UserName));
            }
            else if(input.equals("showchat")){
                logger.info("show chat with user");
                ChatPage.show_specific_chat(e,a.getUsername());
                System.out.println("writemessage/back");
                input=scan.nextLine();
                if (input.equals("writemessage")){
                    System.out.println("enter your message :");
                    input=scan.nextLine();
                    ChatPage.send_message(e,a.getUsername(),input);
//                    g=new GsonBuilder().setPrettyPrinting().create();
//                    r= Files.newBufferedReader(Paths.get("Accounts.json"));
//                    e = g.fromJson(r, new TypeToken<List<user>>() {}.getType());
//                    r.close();

                }
            }
            Writer w=new FileWriter("Accounts.json ");
            g.toJson(e,w);
            w.close();
            input=scan.nextLine();
            logger.info("write to file");




        }
    }
}
