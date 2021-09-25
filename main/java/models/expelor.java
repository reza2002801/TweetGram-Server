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
import java.util.Random;
import java.util.Scanner;

public class expelor {
    private static Logger logger = LogManager.getLogger(expelor.class);
    public static Tweet find_tweet(List<User>e, Tweet a){
        logger.debug("in find_tweet from class expelor on"+e+"and"+a);
        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.get(i).getUsertweets().size();j++){
                if(e.get(i).getUsertweets().get(j).getUsername().equals(a.getUsername())&&e.get(i).getUsertweets().get(j).getTweetphrase().equals(a.getTweetphrase())){
                    return e.get(i).getUsertweets().get(j);
                }
            }
        }
        return null;
    }
    public static void search()throws IOException{
        logger.debug("in search from class expelor ");
        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        logger.info("read from file");
        System.out.println("enter username :");
        String input=scan.nextLine();
        while (true){
            if (input.equals("back")){
                break;
            }
            else if(MainPage.acc_info(e,input)!=null && MainPage.acc_info(e,input).getIsactive()==true){
                logger.debug("in opr_on_profile from class profile on"+ MainPage.acc_info(e,input));
                Profile.opr_on_profile(MainPage.acc_info(e,input));
            }
            else{
                System.out.println("the username you've just enter is invalid:");
            }
            System.out.println("enter username :");
            input=scan.nextLine();
        }
    }
    public static void trendingtweets()throws IOException{
        logger.debug("in trendingtweets from class expelor ");

        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        logger.info("read from file");
        LinkedList<Tweet> a=new LinkedList<>();
        for (int i=0;i<e.size();i++){
            if(e.get(i).getPublicaccount()&&e.get(i).getUsertweets()!=null &&e.get(i).getUsertweets().size()!=0){
                for (int j=0;j<e.get(i).getUsertweets().size();j++){
                    a.add(e.get(i).getUsertweets().get(j));
                }

            }
        }
        String o="";
        logger.info("call random class");
        Random rnd=new Random();
        int i=0;
        if(a.size()!=0){
            i=rnd.nextInt(a.size());
        }

        int l=0;
        if(a!=null && a.size()!=0){
            while(a!=null && !o.equals("back")){
                if(a.get(i).getIsretweet()){
                    System.out.println(a.get(i).getDatetime());
                    System.out.println(a.get(i).getUsername()+"retweetedfrom"+a.get(i).getRetweetuser());
                    System.out.println(a.get(i).getTweetphrase());
                }else {
                    System.out.println(a.get(i).getDatetime());
                    System.out.println(a.get(i).getUsername());
                    System.out.println(a.get(i).getTweetphrase());
                }
                while (!o.equals("back")){
                    System.out.println("back/Next/Previous/WriteComment/ViewComments/ReTweet/Forward/Mute/SeeProfile/Report/like");
                    String input=scan.nextLine();
                    if(input.equals("back")){
                        o="back";
                        break;
                    }else if(input.equals("Next")){
                        l=i;
                        i=rnd.nextInt(a.size());
                        break;
                    }else if (input.equals("Previous")){
                        i=l;
                        break;
                    }else if(input.equals("report")){
                        MainPage.acc_info(e, login.UserName).getReportedtweets().add(a.get(i));
                        System.out.println("Done !");
                    }
                    else if(input.equals("WriteComment")){
                        logger.info("Writing comment");
                        System.out.println("Write Your Comment :");
                        String input1=scan.nextLine();
                        Comment newcomment=new Comment(input1, login.UserName);
//                        LinkedList<comment> p=MainPage.acc_info(e,login.UserName).getFollowingstweet().get(i).getComments();
//                        p.add(newcomment);
//                        System.out.println(p);
                        find_tweet(e,a.get(i)).getComments().add(newcomment);
                        System.out.println("Done!");
//                        Writer w=new FileWriter("Accounts.json ");
//                        g.toJson(e,w);
//                        w.close();
                    }else if(input.equals("ReTweet")){
                        logger.info("ReTweet");
                        if(a.get(i).isretweet){
                            Tweet b=new Tweet(a.get(i).getTweetphrase(), login.UserName);
                            b.setIsretweet(true);
                            b.setRetweetuser(a.get(i).retweetuser);
                            MainPage.acc_info(e, login.UserName).getUsertweets().add(b);
                            MainPage.acc_info(e, login.UserName).getRetweet().add(b);
                            MainPage.add_com_to_follower(e,b, MainPage.acc_info(e, login.UserName));
                        }else {
                            Tweet b=new Tweet(a.get(i).getTweetphrase(), login.UserName);
                            b.setIsretweet(true);
                            b.setRetweetuser(a.get(i).getUsername());
                            MainPage.acc_info(e, login.UserName).getUsertweets().add(b);
                            MainPage.acc_info(e, login.UserName).getRetweet().add(b);
                            MainPage.add_com_to_follower(e,b, MainPage.acc_info(e, login.UserName));
                        }

                    }else if(input.equals("Mute")){
                        logger.info("Mute user");

                        MainPage.acc_info(e, login.UserName).getMuteduser().add(a.get(i).getUsername());
                        TimeLine.del_tweet(a.get(i).getUsername());
                        System.out.println("Done !");
                        g=new GsonBuilder().setPrettyPrinting().create();
                        r= Files.newBufferedReader(Paths.get("Accounts.json"));
                        e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
                        r.close();
                    }else if(input.equals("like")){
                        logger.info("like");

                        MainPage.acc_info(e, login.UserName).getLikedtweet().add(a.get(i));
                        TimeLine.addlikedtweettofollowers(a.get(i));
                        a.get(i).getLikeuser().add(login.UserName);
                        System.out.println("done!");
                    }else if (input.equals("SeeProfile")){
                        logger.info("SeeProfile");

                        Profile.opr_on_profile(MainPage.acc_info(e,a.get(i).getUsername()));
                    }else if (input.equals("Forward")){
                        logger.info("Forward");

                        String mess=a.get(i).getTweetphrase();
                        ChatPage.group_message(mess);
                        g=new GsonBuilder().setPrettyPrinting().create();
                        r= Files.newBufferedReader(Paths.get("Accounts.json"));
                        e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
                        r.close();
                    }

                    else if(input.equals("ViewComments")){
                        logger.info("ViewComments");

                        int j=0;
                        if(a.get(i).getComments().size()!=0 && a.get(i).getComments()!=null){
                            while (a.get(i).getComments().size()!=0 && a.get(i).getComments()!=null){
                                System.out.println("back/next/previous/comments/WriteComment");
                                g=new GsonBuilder().setPrettyPrinting().create();
                                r= Files.newBufferedReader(Paths.get("Accounts.json"));
                                e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
                                r.close();
                                System.out.println(find_tweet(e,a.get(i)).getComments().get(j).getDatetime());
                                System.out.println(find_tweet(e,a.get(i)).getComments().get(j).getTweetphrase());
                                String input2=scan.nextLine();
                                if(input2.equals("back")){
                                    break ;
                                }else if(input2.equals("next")){
                                    if(j==a.get(i).getComments().size()-1){
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
                                    TimeLine.commentviewer(a.get(i).getComments().get(j));
                                }else if(input2.equals("WriteComment")){
                                    System.out.println("enter your comment:");
                                    String input1=scan.nextLine();
                                    Comment n=new Comment(input1, login.UserName);
                                    find_tweet(e,a.get(i)).getComments().get(j).getComments().add(n);
                                    System.out.println("done!");
                                }else if(input2.equals("Report")){
                                    System.out.println("constructing!");
                                }

                                Writer w=new FileWriter("Accounts.json ");
                                g.toJson(e,w);
                                w.close();
                                TimeLine.updater(find_tweet(e,a.get(i)));
                            }
                        }else {
                            System.out.println("no tweet");
                        }

                    }

                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                    TimeLine.updater(find_tweet(e,a.get(i)));
                    logger.info("write to file");
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
