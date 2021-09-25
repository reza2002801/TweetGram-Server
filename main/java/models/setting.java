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
import java.util.List;
import java.util.Scanner;

public class setting {
    private static Logger logger = LogManager.getLogger(setting.class);

    public static void del_from_followers(String username)throws IOException{
        logger.debug("in del_from_followers from class setting on"+username);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        for (int i=0;i<e.size();i++){
            if(e.get(i).getFollowers()!=null&&e.get(i).getFollowers().contains(username)){
                e.get(i).getFollowers().remove(username);
            }
        }
    }
    public static void del_from_followings(String username)throws IOException{
        logger.debug("in del_from_followings from class setting on"+username);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        for (int i=0;i<e.size();i++){
            if(e.get(i)!=null&&e.get(i).getFollowings().contains(username)){
                e.get(i).getFollowings().remove(username);
            }
        }

    }
    public static void del_from_tweets(String username)throws IOException{
        logger.debug("in del_from_tweets from class setting on"+username);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.size();j++){
                if(e.get(i).getFollowingstweet()!=null&&e.get(i).getFollowingstweet().get(j).getUsername().equals(username)){
                    e.get(i).getFollowingstweet().remove(j);
                }
            }

        }

    }

//    public static void log_out()throws IOException{
//        login.main(args);
//    }
    public static void public_private(String s)throws IOException{
        logger.debug("in public_private from class setting on"+s);
        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        if(MainPage.acc_info(e,s).getPublicaccount()){
            System.out.println("Your Account is public !");

            while (true){
                System.out.println("Do y ou want to set private?");
                String input=scan.nextLine();
                if(input.equalsIgnoreCase("y")){
                    MainPage.acc_info(e,s).setPublicaccount(false);
                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                    break;
                }else if (input.equalsIgnoreCase("n")){
                    break;
                }else {
                    System.out.println("invalid command!");
                }

            }

        }else {
            System.out.println("Your Account is private !");

            while (true){
                String input=scan.nextLine();
                System.out.println("Do y ou want to set public ?");
                if(input.equalsIgnoreCase("y")){
                    MainPage.acc_info(e,s).setPublicaccount(true);
                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                }else if(input.equalsIgnoreCase("n")){
                    break;
                }else{
                    System.out.println("invalid command");
                }
            }

        }
    }
    public static void changepass(String s)throws IOException{
        logger.debug("in changepass from class setting on"+s);
        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();

        System.out.println("enter your older password:");
        String input=scan.nextLine();
        while (true){
            if(login.pass_finder(e,s).equals(input)){
                System.out.println("enter your new password :");
                input=scan.nextLine();
                MainPage.acc_info(e,s).setPassword(input);
                System.out.println("Done! ");
                Writer w=new FileWriter("Accounts.json ");
                g.toJson(e,w);
                w.close();
                break;
            }
            else{
                System.out.println("your password is wrong :");

            }
            System.out.println("enter your older password:");
            input=scan.nextLine();

        }


    }
    public static void avtive_deactive(String s)throws IOException{
        logger.debug("in avtive_deactive from class setting on"+s);
        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        if(MainPage.acc_info(e,s).getIsactive()){
            System.out.println("Your Account is Active !");

            while (true){
                System.out.println("Do y ou want to set deactive?");
                String input=scan.nextLine();
                if(input.equalsIgnoreCase("y")){
                    MainPage.acc_info(e,s).setIsactive(false);
                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                }else if (input.equalsIgnoreCase("n")){
                    break;
                }else {
                    System.out.println("invalid command!");
                }

            }

        }else {
            System.out.println("Your Account is deactive !");
            while (true){
                System.out.println("Do y ou want to set active?");
                String input=scan.nextLine();
                if(input.equalsIgnoreCase("y")){
                    MainPage.acc_info(e,s).setIsactive(true);
                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                }else if(input.equalsIgnoreCase("n")){
                    break;
                }else{
                    System.out.println("invalid command");
                }
            }

        }
    }
    public static void lastseen_enable_disable(String s)throws IOException{
        logger.debug("in lastseen_enable_disable from class setting on"+s);

        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        if(MainPage.acc_info(e,s).getIslastseen()){
            System.out.println("your statue : last seen date");
            while (true){
                System.out.println("Do y ou want to set last seen recently?");
                String input=scan.nextLine();
                if(input.equalsIgnoreCase("y")){
                    MainPage.acc_info(e,s).setIslastseen(false);
                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                }else if (input.equalsIgnoreCase("n")){
                    break;
                }else {
                    System.out.println("invalid command!");
                }

            }

        }else {
            System.out.println("your statue :last seen recently !");
            while (true){
                System.out.println("Do y ou want to set last seen date?");
                String input=scan.nextLine();
                if(input.equalsIgnoreCase("y")){
                    MainPage.acc_info(e,s).setIslastseen(true);
                    Writer w=new FileWriter("Accounts.json ");
                    g.toJson(e,w);
                    w.close();
                }else if(input.equalsIgnoreCase("n")){
                    break;
                }else{
                    System.out.println("invalid command");
                }
            }

        }
    }

    public static void main(String[] args) throws IOException{
        logger.debug("call main from class login ");
        login.main(args);
    }
    public static void del_acc(String s) throws IOException {
        logger.debug("in del_acc from class setting on"+s);
        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        System.out.println("Are you sure u want to delete your account ?");
        String input=scan.nextLine();
        boolean l=true;
        while (l){
            if(input.equalsIgnoreCase("Y")){
                System.out.println("enter your older password:");
                input=scan.nextLine();
                while (true){
                    if(MainPage.acc_info(e,s).getPassword().equals(input)){
                        e.remove(MainPage.acc_info(e,s));
                        del_from_followings(s);
                        del_from_followers(s);
                        del_from_tweets(s);
                        Writer w=new FileWriter("Accounts.json ");
                        g.toJson(e,w);
                        w.close();
                        l=false;
                        break;
                    }else if(input.equals("back")){
                        break;
                    }
                    else{
                        System.out.println("your password is wrong :");

                    }
                    System.out.println("enter your older password:");
                    input=scan.nextLine();

                }
                break;
            }else if(input.equalsIgnoreCase("back")){
                break;
            }else{
                System.out.println("invalid command! ");
            }
        }
    }
}
