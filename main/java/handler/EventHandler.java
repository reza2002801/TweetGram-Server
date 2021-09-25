package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.Config;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import models.*;
import shared.*;
import utils.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;

import static models.Profile.delete_tweet_blacklist;
import static models.Profile.transfertweets;
import static models.notifpage.notif_find;
import static models.notifpage.notif_find1;

public class EventHandler extends  Thread {
    public static String USERNAME="";
    User user;
    Gson gson;
    int Time;
    private String AuthToken;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public EventHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.Time = 1;
        this.gson = new Gson();
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void start() {
        super.start();
    }

    public void run() {
        while (true) {
            try {
                String s=dataInputStream.readUTF();
                if(!(s.equals("NOTIF1")||s.equals("MSG"))){
                System.out.println(s);}
//                System.out.println(s);
                if(s.equals(EventsDirections.SIGN_IN)){
                    signIn();
                }
                else if(s.equals("ISCONNECT")){
//                    dataOutputStream.writeUTF("Yes");
                }
                else if(s.equals("changes")){
                    System.out.println("jh");
                    Change change=gson.fromJson(dataInputStream.readUTF(),Change.class);
                    List<User> accounts=new Filemethods().loadFromFile();
                    AccountsMethods.findAccount(accounts,USERNAME).setPassword(change.getNewPassword());
                    AccountsMethods.findAccount(accounts,USERNAME).setIsactive(change.getActive());
                    AccountsMethods.findAccount(accounts,USERNAME).setIslastseen(change.getLastSeen());
                    AccountsMethods.findAccount(accounts,USERNAME).setPublicaccount(change.getPublic());


                }
                else if (s.equals(EventsDirections.SIGN_UP)){
                    signUp();
                }
                else if(s.equals("TWEET")){
                    System.out.println("dfh");
                    tweet();
                }

                else if(s.equals("SETTING")){
                    setting();
                }
                else if(s.equals(EventsDirections.NOTIF)){
                    notif();
                }
                else if(s.equals(EventsDirections.MSG)){
                    messages();
                }
                else if(s.equals("PI")){
                    changePersonalInfo();
                }
                else if(s.equals("NOTIF")){
                    System.out.println("rc");
                    notification();
                }
                else if(s.equals("User")){
                    user();
                }
                else if(s.equals("SEARCH")){
                    search();
                }
                else if(s.equals("CHAT")){
                    chat();
                }
                else if(s.equals("IMAGE")){
                    image();
                }
                else if(s.equals("Message")){
                    message();
                }
                else if(s.equals("GROUP")){
                    group();

                }
                else if(s.equals("fu")){
                    fu();
                }
//                switch (dataInputStream.readUTF()) {
//                    case EventsDirections.SIGN_IN -> signIn();
//                    case EventsDirections.SIGN_UP -> signUp();
////                    case EventsDirections.HOME -> home();
//                    case "GET_USER" -> specifiedUser();
//                    case "GET_CHAT" -> specifiedChat();
//                    case "GET_ROOM" -> specifiedRoom();
//                    case EventsDirections.EXPLORE -> explore();
//                    case "TWEET" ->tweet();
//                    case "SETTING" -> setting();
//                    case EventsDirections.NOTIF -> notif();
//                    case EventsDirections.MSG -> messages();
//                    case  "PI" -> changePersonalInfo();
//                    case "NOTIF"->notification();
//                    case EventsDirections.PROFILE -> profile();
//                    case "CHATS" -> chats();
//                    case "TWEETS" -> tweets();
//                    case "TIME" -> time();
//                    case "USERS" -> users();
//                    case "DO_NOTIF" -> doNotif();
//                }
            } catch (Exception e) {
            }
        }
    }
    public void addToMembersChat(List<User> accounts, LinkedList<String> members, String GroupName){
//        logger.debug("in addToMembersChat from CreateGroupTabController class on values"+accounts+"and"+members);
        for (int i=0;i<accounts.size();i++){
            if(members.contains(accounts.get(i).getUsername())){
                accounts.get(i).getGroupChats().add(GroupName);
            }
        }
    }
    private void group() throws IOException {
        String f=dataInputStream.readUTF();
        if(f.equals("writeMessage")){
            Groupmessage newMessage=gson.fromJson(dataInputStream.readUTF(), Groupmessage.class);
            String GroupName=dataInputStream.readUTF();
            List<GroupChats> groups=new Filemethods().loadFromFileGroup();
            ChatMethods.findChat(groups,GroupName).getChats().add(newMessage);
            new Filemethods().saveToFileGroup(groups);
        }
        else if(f.equals("createGroup")){
            GroupChats newGroupChats = gson.fromJson(dataInputStream.readUTF(),GroupChats.class);
            LinkedList<String> members=gson.fromJson(dataInputStream.readUTF(),LinkedList.class);
            String gpn=dataInputStream.readUTF();
            List<GroupChats> groups=new Filemethods().loadFromFileGroup();
            groups.add(newGroupChats);
            List<User> accounts=new Filemethods().loadFromFile();
            addToMembersChat(accounts,members,gpn);
            new Filemethods().saveToFileGroup(groups);
            new Filemethods().saveToFile(accounts);

        }
        else if(f.equals("addUserToGroup")){
            String UserName=dataInputStream.readUTF();
            String GroupName=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            if(AccountsMethods.findAccount(accounts,USERNAME).getFollowings().contains(UserName)||AccountsMethods.findAccount(accounts,USERNAME).getFollowers().contains(UserName)){
                List<GroupChats> groups=new Filemethods().loadFromFileGroup();
                ChatMethods.findChat(groups,GroupName).getMembers().add(UserName);
                new Filemethods().saveToFileGroup(groups);

                AccountsMethods.findAccount(accounts,UserName).getGroupChats().add(GroupName);
                new Filemethods().saveToFile(accounts);
                dataOutputStream.writeUTF("Yes");

//                updateSurfacePage(ChatMethods.findChat(groups,this.GroupName));
            }
            else {
                dataOutputStream.writeUTF("No");
            }
        }
    }
    private void message() throws IOException {
        String f=dataInputStream.readUTF();
        if(f.equals("getUrl")){
            String User=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            String s=AccountsMethods.findAccount(accounts,User).getProfilepicUrl();
            dataOutputStream.writeUTF(s);
        }
        else if(f.equals("editMessage")){
            String newText=dataInputStream.readUTF();
            message m=gson.fromJson(dataInputStream.readUTF(),message.class);
            if(newText.equals("")){
                m.setMessage("DeletedMessage");
                MessageMethods.editMessageForUser(m,USERNAME);
            }else{
                m.setMessage(newText);
                MessageMethods.editMessageForUser(m,USERNAME);
            }



        }
    }
    private void image() throws IOException, ClassNotFoundException {
        String f=dataInputStream.readUTF();
        if(f.equals("sendImage")){
            String name=dataInputStream.readUTF();
            File fout = new File("C:\\Users\\NoteBook TANDIS\\Documents\\Tweetgram2\\untitled16\\src\\main\\resources\\images\\"+name);
            String a;
            String send="";
            while((a=dataInputStream.readUTF()) != "end"){

                if(a.equals("end")){

                    break;

                }
                send+=a;
            }

            recieveFile(fout,send);
        }
        else if(f.equals("getImage")){
            String name=dataInputStream.readUTF();
            File fout = new File("C:\\Users\\NoteBook TANDIS\\Documents\\Tweetgram2\\untitled16\\src\\main\\resources\\images\\"+name);

            String send=encoder(fout,"jpg");
            String s="";
            int m=send.length();
            int k=0;
            while (m!=0 ){
                if(m>=1000){
                    s=send.substring(k*1000,(k+1)*1000);
                    dataOutputStream.writeUTF(s);
                    k+=1;
                    m-=1000;
                }
                if(m<1000){
                    s=send.substring(k*1000);
                    dataOutputStream.writeUTF(s);
                    m=0;
                }
            }
            dataOutputStream.writeUTF("end");

        }


    }

    public  void recieveFile(File outputfile,String encoded) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(encoded);
        InputStream inputStream = new ByteArrayInputStream(bytes);
//        return ImageIO.read(inputStream);
        BufferedImage bufferedImage=ImageIO.read(inputStream);
        ImageIO.write(bufferedImage, "png", outputfile);


    }
    public String encoder (File img,String format) throws IOException {
        BufferedImage bufferedImage =ImageIO.read(img);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, byteArrayOutputStream);
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    private void chat() throws IOException {
        String f=dataInputStream.readUTF();
        if(f.equals("updateChatIcon")){

        }
        else if(f.equals("writeMessage")){
            message messages=gson.fromJson(dataInputStream.readUTF(),message.class);
            Chat chat=gson.fromJson(dataInputStream.readUTF(),Chat.class);

            chat.setUnreadmessage(chat.getUnreadmessage()+1);
            MessageMethods.sendMessage(messages,chat.getContactname(),USERNAME);
            List<User> accounts=new Filemethods().loadFromFile();
        }
        else if(f.equals("sendGroupMessage1")){
            Category category=gson.fromJson(dataInputStream.readUTF(),Category.class);
            message newmessage =gson.fromJson(dataInputStream.readUTF(),message.class);
            for(int i=0;i<category.getMembers().size();i++){
//                message newmessage=new message(LoginController.USERNAME,Messgetxt.getText());
                MessageMethods.sendMessage(newmessage,category.getMembers().get(i),USERNAME);
                List<User> accounts=new Filemethods().loadFromFile();
//            loadChatMessages(MessageMethods.findChat(accounts,this.category.getMembers().get(i),this.chat.getUsername()));
            }
        }
        else if(f.equals("saveCategory")){
            Category cat=gson.fromJson(dataInputStream.readUTF(),Category.class);

            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).getCategories().add(cat);
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("sendManual")){
            List<User> accounts=new Filemethods().loadFromFile();
            String pictureLink=dataInputStream.readUTF();
            LinkedList<String> contactlist=gson.fromJson(dataInputStream.readUTF(),new TypeToken<LinkedList<String>>() {}.getType());
            System.out.println(contactlist);
            String messagetxt=dataInputStream.readUTF();
            System.out.println(messagetxt);

            System.out.println(pictureLink);
            if(pictureLink.equals("MSG")){
                pictureLink="";
            }

            for (int i=0;i<contactlist.size();i++){
                if(MessageMethods.existchat(accounts,contactlist.get(i),USERNAME)){
                    message newmessage=new message(USERNAME,messagetxt);
                    if(!pictureLink.equals("")){
                        newmessage.setPictureLink(pictureLink);

                    }
                    MessageMethods.sendMessage(newmessage,contactlist.get(i),USERNAME);
//                List<user> accounts=new Filemethods().loadFromFile();
                }else {
                    if(USERNAME.equals(contactlist.get(i))|| AccountsMethods.findAccount(accounts,USERNAME).getFollowers().contains(contactlist.get(i))|| AccountsMethods.findAccount(accounts,USERNAME).getFollowings().contains(contactlist.get(i)) ){
//                System.out.println("else");
                        message newmessage=new message(USERNAME,messagetxt);
                        if(!pictureLink.equals("")){
                            newmessage.setPictureLink(pictureLink);

                        }
                        Chat newchat=new Chat(contactlist.get(i), USERNAME);
                        Chat newchat2=new Chat(USERNAME,contactlist.get(i));
                        newchat.getMessages().add(newmessage);
                        newchat2.getMessages().add(newmessage);
                        AccountsMethods.findAccount(accounts,USERNAME).getUserchats().add(newchat);
                        AccountsMethods.findAccount(accounts,contactlist.get(i)).getUserchats().add(newchat2);
//            System.out.println(MainPage.acc_info(e,contactname).getUserchats());
                        MessageMethods.findChat(accounts, USERNAME,contactlist.get(i)).setIsunread(true);
                        MessageMethods.findChat(accounts, USERNAME,contactlist.get(i)).setUnreadmessage(MessageMethods.findChat(accounts, USERNAME,contactlist.get(i)).getUnreadmessage()+1);
//                System.out.println("done! ");
//                    new PersonalChatTabController().loadChatSurfForProfile(MessageMethods.findChat(accounts,accounts.get(i).getUsername(),LoginController.USERNAME),ap);
                        new Filemethods().saveToFile(accounts);
                    }

                }
            }
        }
        else if(f.equals("sendMessageToAll")){
            String messagetxt=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            for (int i=0;i<accounts.size();i++){
                if(MessageMethods.existchat(accounts,accounts.get(i).getUsername(),USERNAME)){
//                System.out.println(accounts.get(i).getUsername());
                    message newmessage=new message(USERNAME,messagetxt);
                    MessageMethods.sendMessage(newmessage,accounts.get(i).getUsername(),USERNAME);
//                List<user> accounts=new Filemethods().loadFromFile();
                }else {
                    if(USERNAME.equals(accounts.get(i).getUsername())|| AccountsMethods.findAccount(accounts,USERNAME).getFollowers().contains(accounts.get(i).getUsername())|| AccountsMethods.findAccount(accounts,USERNAME).getFollowings().contains(accounts.get(i).getUsername()) ){
//                System.out.println("else");
                        message newmessage=new message(USERNAME,messagetxt);
                        Chat newchat=new Chat(accounts.get(i).getUsername(), USERNAME);
                        Chat newchat2=new Chat(USERNAME,accounts.get(i).getUsername());
                        newchat.getMessages().add(newmessage);
                        newchat2.getMessages().add(newmessage);
                        AccountsMethods.findAccount(accounts,USERNAME).getUserchats().add(newchat);
                        AccountsMethods.findAccount(accounts,accounts.get(i).getUsername()).getUserchats().add(newchat2);
//            System.out.println(MainPage.acc_info(e,contactname).getUserchats());
                        MessageMethods.findChat(accounts, USERNAME,accounts.get(i).getUsername()).setIsunread(true);
                        MessageMethods.findChat(accounts, USERNAME,accounts.get(i).getUsername()).setUnreadmessage(MessageMethods.findChat(accounts, USERNAME,accounts.get(i).getUsername()).getUnreadmessage()+1);
//                System.out.println("done! ");
//                    new PersonalChatTabController().loadChatSurfForProfile(MessageMethods.findChat(accounts,accounts.get(i).getUsername(),LoginController.USERNAME),ap);
                        new Filemethods().saveToFile(accounts);
                    }

                }
            }
        }

    }
    private void search() throws IOException {
        String f=dataInputStream.readUTF();
        if(f.equals("loadAc")){
            System.out.println("gh");
            String user=dataInputStream.readUTF();
            List<User> a=new Filemethods().loadFromFile();
            if(AccountsMethods.findAccount(a,user)!=(null)){
                System.out.println("Yes");
                dataOutputStream.writeUTF("Yes");
//                FXMLLoader loader = new FXMLLoader(getClass().getResource(config.getProperty("UserMiniProfileComponent")));
//
//                try {
//                    gp.add((Node) loader.load(),0,1);
//                    UserMiniProfileComponentController childController=loader.getController();
//                    childController.updateMiniProfile(Searchtxt.getText());
//                    childController.setSearchTabController(this);
//
//                }catch (Exception e){
//                    System.out.println("");
//                }
            }else {
                dataOutputStream.writeUTF("No");
//                gp.getChildren().clear();
            }
        }
    }
    private void user() throws IOException {
        String f=dataInputStream.readUTF();
        System.out.println(f);
        if(f.equals("updateMiniProfile")){
            String user=dataInputStream.readUTF();
            List<User> a=new Filemethods().loadFromFile();
            User ourUser=AccountsMethods.findAccount(a,user);

            MiniProfileResponse MiniProfileResponse=new MiniProfileResponse(ourUser.getUsername(),ourUser.getFirstname(),ourUser.getLastname(), ourUser.getProfilepicUrl());
            System.out.println(MiniProfileResponse);
            dataOutputStream.writeUTF(gson.toJson(MiniProfileResponse));
        }
        else if(f.equals("goToProfile")){
            String user=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            User Ouruser=AccountsMethods.findAccount(accounts,user);
            ProfileResponse profileResponse=new ProfileResponse(Ouruser.getUsername(),Ouruser.getFirstname(),Ouruser.getLastname(),Ouruser.getBio(),Ouruser.getPhonenumber(),Ouruser.getLastseen(),Ouruser.getBirthday(),Ouruser.getProfilepicUrl(),Ouruser.getUsertweets());
            profileResponse.setIsLastSeen(Ouruser.getIslastseen());
            dataOutputStream.writeUTF(gson.toJson(profileResponse));
        }
        else if(f.equals("follow")){
            Config config2=Config.getConfig("other");
            String User = dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            User currentuser = AccountsMethods.findAccount(accounts,User);
            User me=AccountsMethods.findAccount(accounts,USERNAME);
            if(!me.getFollowings().contains(USERNAME)){
                if(!currentuser.getBlackList().contains(USERNAME)){
                    if(currentuser.getPublicaccount()){
                        AccountsMethods.findAccount(accounts,currentuser.getUsername()).getFollowers().add(USERNAME);
                        AccountsMethods.findAccount(accounts,USERNAME).getFollowings().add(currentuser.getUsername());
                        transfertweets(currentuser,AccountsMethods.findAccount(accounts,USERNAME));
                        Notification n=new Notification(USERNAME,config2.getProperty("StartFollowingMessage"));
                        AccountsMethods.findAccount(accounts,currentuser.getUsername()).getSystemnotif().add(n);

                    }else{
                        Notification n=new Notification(USERNAME,config2.getProperty("followRequestMessage"));
                        Notification m=new Notification(currentuser.getUsername(),config2.getProperty("YourRequest"));
                        AccountsMethods.findAccount(accounts,currentuser.getUsername()).getUsernotif().add(n);
                        AccountsMethods.findAccount(accounts,USERNAME).getSystemnotif().add(m);
                    }

                }
            }
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("blockUser")){
            String User=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).getFollowings().remove(User);
            AccountsMethods.findAccount(accounts,USERNAME).getFollowers().remove(User);
            AccountsMethods.findAccount(accounts,USERNAME).getBlackList().add(User);
            AccountsMethods.findAccount(accounts,User).getFollowings().remove(USERNAME);
            AccountsMethods.findAccount(accounts,User).getFollowers().remove(USERNAME);
            delete_tweet_blacklist(AccountsMethods.findAccount(accounts,USERNAME).getFollowingstweet(),User);
            new Filemethods().saveToFile(accounts);
        }
        else if (f.equals("unblockUser")){
            String User=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).getBlackList().remove(User);
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("unMute")){
            String User=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).getMuteduser().remove(User);
            new Filemethods().saveToFile(accounts);
        }else if (f.equals("showBiggerProfile")){
            String User=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            dataOutputStream.writeUTF(AccountsMethods.findAccount(accounts,User).getProfilepicUrl());
        }
        else if(f.equals("viewChatPage")){
            String User=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            if(MessageMethods.existchat(accounts,User,USERNAME)){
                System.out.println("h");
                dataOutputStream.writeUTF("YesChat");
//                new PersonalChatTabController().loadChatSurfForProfile(MessageMethods.findChat(accounts,this.Name,LoginController.USERNAME),ap);
            }else {
                dataOutputStream.writeUTF("NoChat");
                System.out.println("Noch");
                if(USERNAME.equals(User)|| AccountsMethods.findAccount(accounts,USERNAME).getFollowers().contains(User)|| AccountsMethods.findAccount(accounts,USERNAME).getFollowings().contains(User) ){
//                System.out.println("else");
                    dataOutputStream.writeUTF("f");

                    Chat newchat=new Chat(User, USERNAME);
                    Chat newchat2=new Chat(USERNAME,User);
//                newchat.getMessages().add(newmessage);
//                newchat2.getMessages().add(newmessage);
                    AccountsMethods.findAccount(accounts,USERNAME).getUserchats().add(newchat);
                    AccountsMethods.findAccount(accounts,User).getUserchats().add(newchat2);
//            System.out.println(MainPage.acc_info(e,contactname).getUserchats());
                    MessageMethods.findChat(accounts,USERNAME,User).setIsunread(true);
                    MessageMethods.findChat(accounts,USERNAME,User).setUnreadmessage(MessageMethods.findChat(accounts, USERNAME,User).getUnreadmessage()+1);
//                System.out.println("done! ");
                    dataOutputStream.writeUTF(gson.toJson(AccountsMethods.findAccount(accounts,USERNAME)));
                    new Filemethods().saveToFile(accounts);
                }
                else {
                    dataOutputStream.writeUTF("d");
                }

            }
        }


    }

    private void notification() throws IOException {
        String f=dataInputStream.readUTF();
        if(f.equals("CLEAR")){
            System.out.println("here");
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).getUsernotif().clear();
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("updateNotification")){
            List<User> a=new Filemethods().loadFromFile();
            String s=AccountsMethods.findAccount(a,dataInputStream.readUTF()).getProfilepicUrl();
            //SEND PHOTO
//            .
//            .
//            .
            //
        }
        else if(f.equals("acceptFollowRequest")){
            List<User> a=new Filemethods().loadFromFile();

            User currentUser=AccountsMethods.findAccount(a,USERNAME);
            String notifUserName=dataInputStream.readUTF();
            Notification notification=gson.fromJson(dataInputStream.readUTF(),Notification.class);

            AccountsMethods.findAccount(a,USERNAME).getFollowers().add(notifUserName);

            AccountsMethods.findAccount(a,notifUserName).getFollowings().add(USERNAME);
            //announce your request accepted
            notif_find1(AccountsMethods.findAccount(a,notifUserName), AccountsMethods.findAccount(a,USERNAME));
            Profile.transfertweets(AccountsMethods.findAccount(a,USERNAME), AccountsMethods.findAccount(a,notifUserName));
            AccountsMethods.findAccount(a,USERNAME).getUsernotif().remove(notification);
            new Filemethods().saveToFile(a);
        }
        else if(f.equals("rejectFollowRequest")){
            List<User> a=new Filemethods().loadFromFile();
            String notifUserName=dataInputStream.readUTF();
            Notification notification=gson.fromJson(dataInputStream.readUTF(),Notification.class);
            User currentUser=AccountsMethods.findAccount(a,USERNAME);
            notif_find(AccountsMethods.findAccount(a,notifUserName), AccountsMethods.findAccount(a,USERNAME));
            AccountsMethods.findAccount(a,USERNAME).getUsernotif().remove(notification);
            new Filemethods().saveToFile(a);
        }
    }

    private void changePersonalInfo() throws IOException {
        String f=dataInputStream.readUTF();
        if(f.equals("changeBio")){
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).setBio(dataInputStream.readUTF());
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("changeFirstName")){
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).setFirstname(dataInputStream.readUTF());
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("changeLastName")){
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).setLastname(dataInputStream.readUTF());
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("changeEmail")){
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).setEmail(dataInputStream.readUTF());
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("changePhoneNumber")){
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).setPhonenumber(dataInputStream.readUTF());
            new Filemethods().saveToFile(accounts);
        }else if(f.equals("changeDateTime")){
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).setBirthday(dataInputStream.readUTF().toString());
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("ChangeProfilePicture")){
            String g=dataInputStream.readUTF();
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).setProfilepicUrl(g+".jpg");
            new Filemethods().saveToFile(accounts);
        }

    }
    private void fu() throws IOException {
        List<User> a=new Filemethods().loadFromFile();
        Tweet newTweet=gson.fromJson(dataInputStream.readUTF(), Tweet.class);
//        String s=AccountsMethods.findAccount(a,LoginController.USERNAME).getProfilepicUrl();
        User currentUser=AccountsMethods.findAccount(a,USERNAME);
        AccountsMethods.findAccount(a,USERNAME).getUsertweets().add(newTweet);
        new Filemethods().saveToFile(a);

        TweetMethods.addTweetToUserFollwers(newTweet,currentUser.getFollowers());
    }


    private void tweet() throws  IOException{
//        Tweet tweet=new Tweet();

        Tweet tweet=gson.fromJson(dataInputStream.readUTF(),Tweet.class);
        String f=dataInputStream.readUTF();
        System.out.println(f);
        if(f.equals("likeTweet")){
            if(!tweet.getLikeuser().contains(USERNAME)){
                List<User> a=new Filemethods().loadFromFile();
//            System.out.println(this.tweet);
                tweet.getLikeuser().add(USERNAME);
                AccountsMethods.findAccount(a,USERNAME).getLikedtweet().add(tweet);
                TweetMethods.addLikedTweetToFollowers(tweet,USERNAME);
//            addlikedtweettofollowers(this.tweet);
                try {
                    int indexofTweet=TweetMethods.indexOfTweet(tweet,AccountsMethods.findAccount(a,USERNAME).getFollowingstweet());
                    AccountsMethods.findAccount(a,USERNAME).getFollowingstweet().get(indexofTweet).getLikeuser().add(USERNAME);
                }
                catch (Exception e){
                    System.out.println("");
                }
                new Filemethods().saveToFile(a);
                TweetMethods.updateTweet(tweet);
                dataOutputStream.writeUTF("Done");

            }else {
                dataOutputStream.writeUTF("UnDone");
            }
        }else if(f.equals("RetweetTweet")){
            List<User> accounts=new Filemethods().loadFromFile();
            Tweet a=new Tweet(tweet.getTweetphrase(),USERNAME);
            a.setIsretweet(true);
            if(tweet.getRetweetuser().equals("")){
                a.setRetweetuser(tweet.getUsername());
            }else {
                a.setRetweetuser(tweet.getRetweetuser());

            }
            AccountsMethods.findAccount(accounts,USERNAME).getUsertweets().add(a);
            AccountsMethods.findAccount(accounts,USERNAME).getRetweet().add(a);
//        MainPage.add_com_to_follower(e,a, MainPage.acc_info(e, login.UserName));
            TweetMethods.addRetweetToFollowers(accounts,a,AccountsMethods.findAccount(accounts,USERNAME));
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("reportTweet")){
            List<User> accounts=new Filemethods().loadFromFile();
            tweet.setReportNumber(tweet.getReportNumber()+1);
            try {
                int indexofTweet=TweetMethods.indexOfTweet(tweet,AccountsMethods.findAccount(accounts,USERNAME).getFollowingstweet());
                AccountsMethods.findAccount(accounts,USERNAME).getFollowingstweet().get(indexofTweet).getLikeuser().add(USERNAME);
            }catch (Exception e){
                System.out.println("");
            }
            TweetMethods.updateTweet(tweet);
        }
        else if(f.equals("muteUser")){
            List<User> accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(accounts,USERNAME).getMuteduser().add(tweet.getUsername());
//        TweetMethods.updateTweet(this.tweet);
            new Filemethods().saveToFile(accounts);
        }
        else if(f.equals("addComment")){
//            Tweet t=gson.fromJson(dataInputStream.readUTF(), Tweet.class);
//            System.out.println(t);
//            TweetMethods.updateTweet(t);
//            List<User> accounts=new Filemethods().loadFromFile();
//            for (int i=0;i<AccountsMethods.findAccount(accounts,USERNAME).getFollowingstweet().size();i++){
//                if(AccountsMethods.findAccount(accounts,USERNAME).getFollowingstweet().get(i).getDatetime().equals(t.getDatetime())){
//                    AccountsMethods.findAccount(accounts,USERNAME).getFollowingstweet().get(i).setComments(t.getComments());
//                }
//            }
//            new Filemethods().saveToFile(accounts);
            System.out.println("ffff");

        }
        else if(f.equals("f")){
            Tweet t=gson.fromJson(dataInputStream.readUTF(), Tweet.class);
            TweetMethods.updateTweet(t);
        }

    }
    private  void setting() throws IOException {
        String f=dataInputStream.readUTF();
        if(f.equals("setPublicAccountEnable")){
            List<User> Accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(Accounts,USERNAME).setPublicaccount(true);
            new Filemethods().saveToFile(Accounts);
        }else if(f.equals("setLastSeenEnable")){
            List<User> Accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(Accounts,USERNAME).setIslastseen(true);
            new Filemethods().saveToFile(Accounts);
        }
        else if(f.equals("setIsActiveEnable")){
            List<User> Accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(Accounts,USERNAME).setIsactive(true);
            new Filemethods().saveToFile(Accounts);
        }
        else if(f.equals("setLastSeenDisable")){
            List<User> Accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(Accounts,USERNAME).setIslastseen(false);
            new Filemethods().saveToFile(Accounts);
        }
        else if(f.equals("setPublicAccountDisable")){
            List<User> Accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(Accounts,USERNAME).setPublicaccount(false);
            new Filemethods().saveToFile(Accounts);
        }
        else if(f.equals("setIsActiveDisable")){
            List<User> Accounts=new Filemethods().loadFromFile();
            AccountsMethods.findAccount(Accounts,USERNAME).setIsactive(false);
            new Filemethods().saveToFile(Accounts);
        }
        else if(f.equals("deleteAccount")){
            String d=dataInputStream.readUTF();
            List<User> Accounts=new Filemethods().loadFromFile();
            if(d.equals(AccountsMethods.findAccount(Accounts,USERNAME).getPassword())){
                deleteAccount1();
                dataOutputStream.writeUTF("Done");
//                new ChangeScene().change_scene(event,config.getProperty("LoginPage"));
            }else {
//                deleteAccountLabel.setText(config2.getProperty("DeleteAccWrongPass"));
                dataOutputStream.writeUTF("No");
            }

        }
        else if(f.equals("changePasswod")){
            List<User> accounts=new Filemethods().loadFromFile();
            String olderpass=dataInputStream.readUTF();
            String newpass=dataInputStream.readUTF() ;
            String accPass=AccountsMethods.findAccount(accounts,USERNAME).getPassword();
            if(accPass.equals(olderpass)){
                if(newpass.equals(olderpass)){
                    dataOutputStream.writeUTF("PassRepeatWarning");
//                    changePasswordlbl.setText(config2.getProperty("PassRepeatWarning"));
                }
                else{
                    AccountsMethods.findAccount(accounts,USERNAME).setPassword(newpass);
                    dataOutputStream.writeUTF("PassChangeDone");
//                    changePasswordlbl.setText(config2.getProperty("PassChangeDone"));
                    new Filemethods().saveToFile(accounts);
                }
            }else {
                dataOutputStream.writeUTF("WrongPassWarn");
//                changePasswordlbl.setText(config2.getProperty("WrongPassWarn"));
            }

        }
    }
    public void deleteAccount1() throws IOException {
//        logger.debug("in deleteAccount1 from DeleteAccountSubPageController class on values");
//
        AccountsMethods.deleteFromFollowers(USERNAME);
        AccountsMethods.deleteFromFollowings(USERNAME);
        AccountsMethods.deleteFromTweets(USERNAME);
        List<User> Accounts=new Filemethods().loadFromFile();

        Accounts.remove(AccountsMethods.findAccount(Accounts,USERNAME));

//        del_from_followings(s);
//        del_from_followers(s);
//        del_from_tweets(s);
        new Filemethods().saveToFile(Accounts);
    }

    private void time() throws IOException {
        String a = dataInputStream.readUTF();
        Time = Integer.parseInt(a);
    }




    private void profile() {

    }


    private void notif() throws IOException {
        LinkedList<Notification> notifs = user.getSystemnotif();
        NotifResponse notifResponse = new NotifResponse(notifs);
        dataOutputStream.writeUTF(gson.toJson(notifResponse));

    }


    private void messages() throws IOException {
        String str = dataInputStream.readUTF();

            if(str.equals("DATA")){
//                System.out.println("hereere");
                this.user= AccountsMethods.findAccount(new Filemethods().loadFromFile(),USERNAME);
//                System.out.println(this.user);
//                LinkedList<Chat> rooms = ChatMethods.userRoom(user.getUsername());
                LinkedList<Chat> rooms=this.user.getUserchats();
//                System.out.println(rooms);
                RoomResponse rs = new RoomResponse(rooms);
                List<GroupChats> groupss=new Filemethods().loadFromFileGroup();
//                System.out.println(rs);
                dataOutputStream.writeUTF(gson.toJson(rs));
                dataOutputStream.writeUTF(gson.toJson(this.user));
                dataOutputStream.writeUTF(gson.toJson(groupss));
            }

        }



    private void explore() {
    }


    private void signUp() throws IOException {
        SignUpResponse res = gson.fromJson(dataInputStream.readUTF(), SignUpResponse.class);
        List<User> Accounts=new Filemethods().loadFromFile();
        if(new AccountsMethods().isThereAccount(Accounts,res.getUsername())){
            dataOutputStream.writeUTF("ExistAccount");
        }else {
            User NewUser=new User(res.getUsername(),res.getfName(),res.getlName(),res.getEmail(),res.getPassword());
            Accounts.add(NewUser);
            new Filemethods().saveToFile(Accounts);
            this.USERNAME=res.getUsername();
            user=NewUser;
            dataOutputStream.writeUTF("SignUp");

        }

    }

    private void signIn() throws IOException {

        SignInResponse res = gson.fromJson(dataInputStream.readUTF(), SignInResponse.class);
        List<User> accounts=new Filemethods().loadFromFile();


        if(AccountsMethods.findAccount(accounts,res.getUsername())!=(null)){
            User foundeduser=AccountsMethods.findAccount(accounts,res.getUsername());
            if(foundeduser.getPassword().equals(res.getPassword())){
                this.USERNAME=res.getUsername();
                dataOutputStream.writeUTF("Login");
                user=foundeduser;

            }else {
                dataOutputStream.writeUTF("WrongPassword");

            }

        }else {
            dataOutputStream.writeUTF("WrongUserName");
        }
    }

    public User getUser() {
        return user;
    }

}
