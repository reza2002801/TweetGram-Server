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

public class ChatPage {
    private static Logger logger = LogManager.getLogger(ChatPage.class);

    public static void group_message(String mess) throws IOException{
        logger.debug("in group_message from class chatpage on"+mess);

        Scanner  scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        logger.info("read from file");
        System.out.println("groupMessaging(choosecategories/makecategoriy/deletecategory/viewYourcategories/sendtoall/choosemanual)");
        String inp=scan.nextLine();
        if(inp.equals("choosecategories")){
            if (MainPage.acc_info(e, login.UserName).getCategories()!=null&& MainPage.acc_info(e, login.UserName).getCategories().size()!=0){
                System.out.println("enter your category name: ");
                inp=scan.nextLine();
                for (int i=0;i<find_category(e,inp).getMembers().size();i++){
                    send_message(e,find_category(e,inp).getMembers().get(i),mess);
                }
            }else {
                System.out.println("You have no categories !");
            }
        }else if (inp.equals("deletecategory")){
            System.out.println("ente name of category");
            inp=scan.nextLine();
            MainPage.acc_info(e, login.UserName).getCategories().remove(find_category(e,inp));
            System.out.println("done ");
        }


        else if(inp.equals("sendtoall")){
            for(int i=0;i<e.size();i++){
                send_message(e,e.get(i).getUsername(),mess);
            }
        }else if(inp.equals("makecategoriy")){
            System.out.println("enter category name");
            inp=scan.nextLine();
            Category newcategory=new Category(inp);
            while (true){
                System.out.println("adduser/end");
                inp=scan.nextLine();
                if(inp.equals("end")){
                    break;
                }else if(inp.equals("adduser")){
                    System.out.println("enter username");
                    inp=scan.nextLine();
                    System.out.println(newcategory.getMembers());
                    newcategory.getMembers().add(inp);
                    System.out.println("done!");
                    MainPage.acc_info(e, login.UserName).getCategories().add(newcategory);
                }
                Writer w=new FileWriter("Accounts.json ");
                g.toJson(e,w);
                w.close();
                logger.info("write to file");
            }


        }else if(inp.equals("choosemanual")){
            LinkedList<String> usermanula=new LinkedList<>();
            while (true){
                System.out.println("end/adduser");
                inp=scan.nextLine();
                if (inp.equals("end")){
                    break;
                }else if (inp.equals("adduser")){
                    System.out.println("enter user name");
                    inp=scan.nextLine();
                    usermanula.add(inp);
                }
            }

            for (int i=0;i<usermanula.size();i++){
                send_message(e,usermanula.get(i),mess);
            }
        }else if (inp.equals("viewYourcategories")&& MainPage.acc_info(e, login.UserName).getCategories()!=null&& MainPage.acc_info(e, login.UserName).getCategories().size()!=0 ){
            for (int i = 0; i< MainPage.acc_info(e, login.UserName).getCategories().size(); i++){
                System.out.println(MainPage.acc_info(e, login.UserName).getCategories().get(i).getCatname()+"\n");
                for (int j = 0; j< MainPage.acc_info(e, login.UserName).getCategories().get(i).getMembers().size(); j++){
                    System.out.println(MainPage.acc_info(e, login.UserName).getCategories().get(i).getMembers().get(j));
                }
            }
        }
        Writer w=new FileWriter("Accounts.json ");
        g.toJson(e,w);
        w.close();
        logger.info("write to file");
    }
    public static Category find_category(List<User> e, String catname){
        logger.debug("in find_category from class chatpage on"+e+" ,"+ catname);

        for(int i = 0; i< MainPage.acc_info(e, login.UserName).getCategories().size(); i++){
            if(MainPage.acc_info(e, login.UserName).getCategories().get(i).getCatname().equals(catname)){
                return MainPage.acc_info(e, login.UserName).getCategories().get(i);
            }
        }
        return null;
    }
    public static void personal_msg()throws IOException{
        logger.debug("in personal_msg from class chatpage ");

        Scanner  scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        System.out.println("enter username:");
        String input2=scan.nextLine();
        Profile.opr_on_profile(MainPage.acc_info(e,input2));

    }
    public static void show_specific_chat(List<User> e, String contactname) throws IOException{
        logger.debug("in show_specific_chat from class chatpage on"+e+" ,"+ contactname);


//        Gson g=new GsonBuilder().setPrettyPrinting().create();
//        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
//        List<user> e = g.fromJson(r, new TypeToken<List<user>>() {}.getType());
//        r.close();
        if(find_chat(e,contactname, login.UserName)!=null &&find_chat(e,contactname, login.UserName).getMessages().size()!=0){
            for (int i = 0; i<find_chat(e,contactname, login.UserName).getMessages().size(); i++){
                System.out.println(find_chat(e,contactname, login.UserName).getMessages().get(i).getDatetime());
                System.out.println(find_chat(e,contactname, login.UserName).getMessages().get(i).getUsername());
                System.out.println(find_chat(e,contactname, login.UserName).getMessages().get(i).getMessage());
            }
            find_chat(e,contactname, login.UserName).setUnreadmessage(0);
            find_chat(e,contactname, login.UserName).setIsunread(false);
//            Writer w=new FileWriter("Accounts.json ");
//            g.toJson(e,w);
//            w.close();

        }else {
            System.out.println("no chat here");
        }


    }
    public static void show_chats()throws IOException{
        logger.debug("in show_chats from class chatpage");

        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        if(MainPage.acc_info(e, login.UserName).getUserchats().size()!=0){
            for (int i = 0; i< MainPage.acc_info(e, login.UserName).getUserchats().size(); i++){
                if(MainPage.acc_info(e, login.UserName).getUserchats().get(i).getIsunread()){
                    System.out.println(MainPage.acc_info(e, login.UserName).getUserchats().get(i).getContactname());
                    System.out.println(MainPage.acc_info(e, login.UserName).getUserchats().get(i).getUnreadmessage()+"unread message(s)");
                }
            }for (int i = 0; i< MainPage.acc_info(e, login.UserName).getUserchats().size(); i++){
                if(!MainPage.acc_info(e, login.UserName).getUserchats().get(i).getIsunread()){
                    System.out.println(MainPage.acc_info(e, login.UserName).getUserchats().get(i));
                }
            }
        }else {
            System.out.println("no chat here");
        }
    }
    public static void send_message(List<User>e, String contactname, String input) throws IOException{
        logger.debug("in send_message from class chatpage on"+e+" ,"+ contactname+" ,"+input);

        Scanner scan=new Scanner(System.in);
//        Gson g=new GsonBuilder().setPrettyPrinting().create();
//        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
//        List<user> e = g.fromJson(r, new TypeToken<List<user>>() {}.getType());
//        r.close();
            if(login.UserName.equals(contactname)|| MainPage.acc_info(e, login.UserName).getFollowers().contains(contactname)|| MainPage.acc_info(e, login.UserName).getFollowings().contains(contactname) ){
            message newmessage=new message(login.UserName,input);
            if(existchat(e,contactname)){
                find_chat(e, login.UserName,contactname).setIsunread(true);
                find_chat(e, login.UserName,contactname).setUnreadmessage(find_chat(e, login.UserName,contactname).getUnreadmessage()+1);
                find_chat(e,contactname, login.UserName).getMessages().add(newmessage);
                find_chat(e, login.UserName,contactname).getMessages().add(newmessage);
                System.out.println("done!");
            }else{
                    System.out.println("else");
                    Chat newchat=new Chat(contactname, login.UserName);
                    Chat newchat2=new Chat(login.UserName,contactname);
                    newchat.getMessages().add(newmessage);
                    newchat2.getMessages().add(newmessage);
                    MainPage.acc_info(e, login.UserName).getUserchats().add(newchat);
                    MainPage.acc_info(e,contactname).getUserchats().add(newchat2);
    //            System.out.println(MainPage.acc_info(e,contactname).getUserchats());
                    find_chat(e, login.UserName,contactname).setIsunread(true);
                    find_chat(e, login.UserName,contactname).setUnreadmessage(find_chat(e, login.UserName,contactname).getUnreadmessage()+1);
                    System.out.println("done! ");
            }
//        Writer w=new FileWriter("Accounts.json ");
//        g.toJson(e,w);
//        w.close();
        }else {
            System.out.println("In order to message at least one of You should follow otherone!");
        }



    }
    public static Chat find_chat(List<User> e, String contactname, String userName){
        logger.debug("in find_chat from class chatpage on"+e+" ,"+ contactname+" ,"+userName);

        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.get(i).getUserchats().size();j++){
                if(e.get(i).getUserchats().get(j).getContactname().equals(contactname)&&e.get(i).getUserchats().get(j).getUsername().equals(userName)){
                    return e.get(i).getUserchats().get(j);
                }
            }

        }
        return null;

    }
    public static Boolean existchat(List<User> e, String contactname){
        logger.debug("in existchat from class chatpage on"+e+" ,"+ contactname);

        if (MainPage.acc_info(e, login.UserName).getUserchats().size()==0){
            return false;
        }else{
            for (int i = 0; i< MainPage.acc_info(e, login.UserName).getUserchats().size(); i++){
                if (MainPage.acc_info(e, login.UserName).getUserchats().get(i).getContactname().equals(contactname)){
                    return true;
                }
            }
        }
        return false;
    }
}
