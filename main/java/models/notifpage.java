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

public class notifpage {
    private static Logger logger = LogManager.getLogger(notifpage.class);

    public static void notif_find(User a, User b){
        logger.debug("in notif_find from notifpage class on "+a+"and"+b);

        LinkedList<Notification> g=a.getSystemnotif();
        for (int i=0;i<g.size();i++){
            if(g.get(i).getUsername().equals(b.getUsername())){
                g.get(i).setMessage("your request has been declined");
            }
        }
    }
    public static void notif_find1(User a, User b){
        logger.debug("in notif_find1 from notifpage class on "+a+"and"+b);
        LinkedList<Notification> g=a.getUsernotif();
        for (int i=0;i<g.size();i++){
            if(g.get(i).getUsername().equals(b.getUsername())){
                g.get(i).setMessage("your request has been Accepted");
            }
        }
    }
    public static void main(String[] args) throws IOException {
        logger.debug("in main from notifpage class ");
        System.out.println("notification(back/usernotifs/systemnotifications)");
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        Scanner scan=new Scanner(System.in);

        while (true){
            String input=scan.nextLine();
            if(input.equals("back")){
                System.out.println("Menu(back/view_lists/show_my_tweet/write_tweet/info/Change_Personal_Information/notifications/)");
                break;
            }else if(input.equals("usernotifs")&& MainPage.acc_info(e, login.UserName).getUsernotif()!=null){
                int i=0;
                logger.debug("in acc_info from MainPage class on "+e+"and"+ login.UserName);
                LinkedList<Notification> n= MainPage.acc_info(e, login.UserName).getUsernotif();
                while (MainPage.acc_info(e, login.UserName).getUsernotif().size()!=0 && MainPage.acc_info(e, login.UserName).getUsernotif()!=null){
                    System.out.println(n.get(i).getDatetime());
                    System.out.println(n.get(i).getUsername()+n.get(i).getMessage());

                    if (n.get(i).getMessage().equals("has requested to follow you ")){
                        System.out.println("u have three choice accept/ignore/ignorewithoutnotification");
                        String input1=scan.nextLine();
                        if(input1.equals("accept")){
                                logger.debug("call acc_info from MainPage class on "+e+"and"+ login.UserName);
                                MainPage.acc_info(e, login.UserName).getFollowers().add(n.get(i).getUsername());
                                logger.debug("call acc_info from MainPage class on "+e+"and"+ login.UserName);
                                MainPage.acc_info(e,n.get(i).getUsername()).getFollowings().add(login.UserName);
                                notif_find1(MainPage.acc_info(e,n.get(i).getUsername()), MainPage.acc_info(e, login.UserName));
                                Profile.transfertweets(MainPage.acc_info(e, login.UserName), MainPage.acc_info(e,n.get(i).getUsername()));
                                MainPage.acc_info(e, login.UserName).getUsernotif().remove(n.get(i));

//                            MainPage.acc_info(e,login.UserName).getFollowers().add(n.get(i).getUsername());
//                            MainPage.acc_info(e,n.get(i).getUsername()).getFollowings().add(login.UserName);
//                            MainPage.acc_info(e,login.UserName).getUsernotif().remove(MainPage.acc_info(e,login.UserName).getUsernotif().get(i));
//                            notif_find1(MainPage.acc_info(e,n.get(i).getUsername()),MainPage.acc_info(e,login.UserName));
                        }else if(input1.equals("ignore")){
                            logger.debug("call notif_find from Notifpage class on "+ MainPage.acc_info(e,n.get(i).getUsername())+"and"+ MainPage.acc_info(e, login.UserName));
                            notif_find(MainPage.acc_info(e,n.get(i).getUsername()), MainPage.acc_info(e, login.UserName));
                            MainPage.acc_info(e, login.UserName).getUsernotif().remove(n.get(i));
                        }else if(input1.equals("ignorewithoutnotification")){
                            MainPage.acc_info(e, login.UserName).getUsernotif().remove(n.get(i));
                        }
                        Writer w=new FileWriter("Accounts.json ");
                        g.toJson(e,w);
                        w.close();
                    }else {
                        MainPage.acc_info(e, login.UserName).getUsernotif().remove(i);
                        Writer w=new FileWriter("Accounts.json ");
                        g.toJson(e,w);
                        w.close();
                    }
                    String input2=scan.nextLine();
                    if(input2.equals("back")){
                        System.out.println("done");
                        System.out.println("notification(back/usernotifs/systemnotifications)");
                        break;
                    }else if(input2.equals("next")){
                        i++;

                    }

                }
            }else if(input.equals("systemnotifications")){
                if(MainPage.acc_info(e, login.UserName).getSystemnotif()!=null&& MainPage.acc_info(e, login.UserName).getSystemnotif().size()!=0 ){
                    for (int i = 0; i< MainPage.acc_info(e, login.UserName).getSystemnotif().size(); i++){
                        System.out.println(MainPage.acc_info(e, login.UserName).getSystemnotif().get(i).getDatetime());
                        System.out.println(MainPage.acc_info(e, login.UserName).getSystemnotif().get(i).getUsername()+ MainPage.acc_info(e, login.UserName).getSystemnotif().get(i).getMessage());
                    }
                }else {
                    System.out.println("empty");
                }

            }
            Writer w=new FileWriter("Accounts.json ");
            g.toJson(e,w);
            w.close();
            logger.info("write to file");
        }
    }
}
