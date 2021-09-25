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

public class MainPage {
    private static Logger logger = LogManager.getLogger(MainPage.class);

    public static User acc_info(List<User> w, String username){
        logger.debug("in acc_info from MainPage class on values"+w+"and"+username);
        for(int i=0;i<w.size();i++){
            if(w.get(i).getUsername().equals(username)){
                return w.get(i);
            }
        }
        return null;
    }
    public static int index(List<User> w, String username){
        logger.debug("in index from MainPage class on values"+w+"and"+username);
        for(int i=0;i<w.size();i++){
            if(w.get(i).getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }
    public static void add_com_to_follower(List<User> e, Tweet t, User b){
        logger.debug("in add_com_to_follower from MainPage class on values"+e+"and"+t+"and"+b);

        LinkedList<String> f=b.getFollowers();
        for(int i=0;i<f.size();i++){
            e.get(index(e,f.get(i))).addfollowingstweet(t);
        }
    }
    public static void printcomment(List<User> b, String s){
        logger.debug("in printcomment from MainPage class on values"+b+"and"+s);

        User c=acc_info(b,s);
        for (int i=0;i<c.getUsertweets().size();i++){
            System.out.println(c.getUsertweets().get(i).datetime);
            System.out.println(c.getUsertweets().get(i).tweetphrase);
            System.out.println("\n");
        }
    }
    public static void main(String[] args) throws IOException {
        logger.debug("in main from MainPage class ");

        System.out.println("Menu(back/view_lists/show_my_tweet/write_tweet/info/Change_Personal_Information/notifications/)");
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();

        Scanner scan=new Scanner(System.in);
        while(true){
            String input=scan.nextLine();
            if(input.equals("back")){
                break;
            }
            else if(input.equals("view_lists")){
                logger.debug("call main from viewlists class ");
                viewlists.main(args);

            }
            else if(input.equals("show_my_tweet")){
                logger.debug("call acc_info from MainPage class on values"+e+"and"+ login.UserName);

                if(acc_info(e, login.UserName).getUsertweets()!=null){

                    for (int i = 0; i<acc_info(e, login.UserName).getUsertweets().size(); i++){
                        if(acc_info(e, login.UserName).getUsertweets().get(i).getIsretweet()){
                            System.out.println(acc_info(e, login.UserName).getUsertweets().get(i).getDatetime());
                            System.out.println(acc_info(e, login.UserName).getUsertweets().get(i).getUsername()+" retweetedfrom "+acc_info(e, login.UserName).getUsertweets().get(i).getRetweetuser());
                            System.out.println(acc_info(e, login.UserName).getUsertweets().get(i).getTweetphrase());
                        }else {
                            System.out.println(acc_info(e, login.UserName).getUsertweets().get(i).getDatetime());
                            System.out.println(acc_info(e, login.UserName).getUsertweets().get(i).getUsername());
                            System.out.println(acc_info(e, login.UserName).getUsertweets().get(i).getTweetphrase());
                        }
//                        System.out.println(acc_info(e,login.UserName).getUsertweets().get(i).getDatetime());
//                        System.out.println(acc_info(e,login.UserName).getUsertweets().get(i).getTweetphrase());
                    }
                    System.out.println("Done !");
                }else {
                    System.out.println("You have no tweet!");
                }

            }
            else if(input.equals("write_tweet")){
                System.out.println("enter your tweet");
                String tweet=scan.nextLine();
                Tweet newtweet=new Tweet(tweet, login.UserName);
                logger.debug("call acc_info from MainPage class on values"+e+"and"+ login.UserName);

                acc_info(e, login.UserName).addusertweet(newtweet);
                logger.debug("call add_com_to_follower from MainPage class on values"+e+"and"+newtweet+","+acc_info(e, login.UserName));
                add_com_to_follower(e,newtweet,acc_info(e, login.UserName));
                System.out.println("done!");
            }
            else if(input.equals("info")){
                String usrname= login.UserName;
                User a=acc_info(e,usrname);
                System.out.println("UserName :"+a.getUsername());
                System.out.println("Full Name :"+a.getFirstname()+" "+a.getLastname());
                System.out.println("Email Address :"+a.getEmail());
                System.out.println("bio :"+a.getBio());
                System.out.println("phonenumber :"+a.getPhonenumber());
                System.out.println("birthday :"+a.getBirthday());
            }

            else if(input.equals("Change_Personal_Information")){
                logger.debug("call main from change_personal_info class ");
                change_personal_info.main(args);
                g=new GsonBuilder().setPrettyPrinting().create();
                r= Files.newBufferedReader(Paths.get("Accounts.json"));
                e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
                r.close();
            }


            else if(input.equals("notifications")){
                logger.debug("call main from notifpage class ");
                notifpage.main(args);
            }


            else if(input.equals("back")){
                break;
            }


            Writer w=new FileWriter("Accounts.json ");
            g.toJson(e,w);
            w.close();
            logger.info("write to file");
        }
    }
}
