package utils;

import models.Chat;
import models.User;
import models.message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class MessageMethods {
    private static Logger logger = LogManager.getLogger(MessageMethods.class);
    public static void editMessageForUser(message mess,String UserName) throws IOException {
        logger.debug("in editMessageForUser from MessageMethods class on values"+mess+"and"+UserName);
        List<User> accounts=new Filemethods().loadFromFile();
        User currentuser=AccountsMethods.findAccount(accounts,UserName);
        for (int i=0;i<currentuser.getUserchats().size();i++){
            for(int j=0;j<currentuser.getUserchats().get(i).getMessages().size();j++){
                if(currentuser.getUserchats().get(i).getMessages().get(j).getDatetime().equals(mess.getDatetime())&&currentuser.getUserchats().get(i).getMessages().get(j).getUsername().equals(mess.getUsername())){
                    AccountsMethods.findAccount(accounts,UserName).getUserchats().get(i).getMessages().get(j).setMessage(mess.getMessage());
                    new Filemethods().saveToFile(accounts);
                    editMessageForContact(mess,currentuser.getUserchats().get(i).getContactname());
                }
            }
        }

    }
    public static void editMessageForContact(message mess,String UserName) throws IOException {
        logger.debug("in editMessageForContact from MessageMethods class on values"+mess+"and"+UserName);
        List<User> accounts=new Filemethods().loadFromFile();
        User currentuser=AccountsMethods.findAccount(accounts,UserName);
        for (int i=0;i<currentuser.getUserchats().size();i++){
            for(int j=0;j<currentuser.getUserchats().get(i).getMessages().size();j++){
                if(currentuser.getUserchats().get(i).getMessages().get(j).getDatetime().equals(mess.getDatetime())&&currentuser.getUserchats().get(i).getMessages().get(j).getUsername().equals(mess.getUsername())){
                    AccountsMethods.findAccount(accounts,UserName).getUserchats().get(i).getMessages().get(j).setMessage(mess.getMessage());
                }
            }
        }
        new Filemethods().saveToFile(accounts);
    }
    public static void sendMessage(message mess,String UserName,String LoginUserName) throws IOException {
        logger.debug("in sendMessage from MessageMethods class on values"+mess+"and"+UserName);
        List<User> accounts=new Filemethods().loadFromFile();
        User currentuser=AccountsMethods.findAccount(accounts,LoginUserName);
        for(int i=0;i<currentuser.getUserchats().size();i++){
            if(currentuser.getUserchats().get(i).getContactname().equals(UserName)){
                System.out.println(currentuser.getUserchats().get(i).getContactname());
                AccountsMethods.findAccount(accounts,LoginUserName).getUserchats().get(i).getMessages().add(mess);
            }
        }
        User currentuser2=AccountsMethods.findAccount(accounts,UserName);
        for(int i=0;i<currentuser2.getUserchats().size();i++){
            if(currentuser2.getUserchats().get(i).getUsername().equals(UserName)){
                AccountsMethods.findAccount(accounts,UserName).getUserchats().get(i).getMessages().add(mess);
            }
        }
        new Filemethods().saveToFile(accounts);
    }
    public static Boolean existchat(List<User> e, String contactname,String LoginUserName) throws IOException {
        logger.debug("in existchat from MessageMethods class on values"+e+"and"+contactname);
//        logger.debug("in existchat from class chatpage on"+e+" ,"+ contactname);
//        List<user> accounts=new Filemethods().loadFromFile();
        if (AccountsMethods.findAccount(e,LoginUserName).getUserchats().size()==0){
            return false;
        }else{
            for (int i = 0; i< AccountsMethods.findAccount(e,LoginUserName).getUserchats().size(); i++){
                if (AccountsMethods.findAccount(e,LoginUserName).getUserchats().get(i).getContactname().equals(contactname)){
                    return true;
                }
            }
        }
        return false;
    }
    public static Chat findChat(List<User> e, String contactname, String userName){
        logger.debug("in findChat from MessageMethods class on values"+e+"and"+contactname+"and"+userName);
//        logger.debug("in find_chat from class chatpage on"+e+" ,"+ contactname+" ,"+userName);

        for (int i=0;i<e.size();i++){
            for (int j=0;j<e.get(i).getUserchats().size();j++){
                if(e.get(i).getUserchats().get(j).getContactname().equals(contactname)&&e.get(i).getUserchats().get(j).getUsername().equals(userName)){
                    return e.get(i).getUserchats().get(j);
                }
            }

        }
        return null;

    }
}
