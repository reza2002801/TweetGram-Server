package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class client {
    private static Logger logger = LogManager.getLogger(client.class);
    public static void main(String[] args) throws IOException {
        Scanner scan=new Scanner(System.in);
        logger.debug("program started class:client");
        logger.debug("call main from login class");
        login.main(args);



        while (true){
            System.out.println("MainPage/quit/setting/expelor/TimeLine/ChatPage");
            String input=scan.nextLine();
            if(input.equals("MainPage")){
                logger.debug("call main from MAinPage class ");
                MainPage.main(args);
            }else if(input.equals("quit")){
                System.out.println("Are You Sure You Wanna quit(Y/N)? ");
                input=scan.nextLine();
                if(input.equals("Y")){
                    logger.info("exit from program.");
                    break;
                }


            }else if(input.equals("setting")){
                System.out.println("setting(logout/DeleteAccount/privacy)");
                input=scan.nextLine();
                if (input.equalsIgnoreCase("DeleteAccount")){
                    logger.debug("call del_acc from class setting on"+ login.UserName);
                    setting.del_acc(login.UserName);
                }else if(input.equals("logout")){
                    System.out.println("Are you sure u want to logout ?");
                    input=scan.nextLine();
                    if(input.equals("Y")){
                        System.out.println("logedout successfully!");
                        logger.debug("call main from class setting ");
                        client.main(args);
                        break;
                    }
                }else if(input.equals("privacy")){
                    System.out.println("privacy part(LastSeenSetting/DeactivationSetting/ChangePassword/PublicPrivateSetting)");
                    input=scan.nextLine();
                    if(input.equals("LastSeenSetting")){
                        logger.debug("call lastseen_enable_disable from class setting on"+ login.UserName);
                        setting.lastseen_enable_disable(login.UserName);
                    }else if(input.equals("DeactivationSetting")){
                        logger.debug("call avtive_deactive from class setting on"+ login.UserName);
                        setting.avtive_deactive(login.UserName);
                    }else if (input.equals("ChangePassword")){
                        logger.debug("call changepass from class setting on"+ login.UserName);
                        setting.changepass(login.UserName);
                    }else if (input.equals("PublicPrivateSetting")){
                        logger.debug("call public_private from class setting on"+ login.UserName);
                        setting.public_private(login.UserName);
                    }
                }
            }else if(input.equals("expelor")){
                System.out.println("expelor(search/trendingtweets)");
                input=scan.nextLine();
                if(input.equals("search")){
                    logger.debug("call search from expelor class ");
                    expelor.search();
                }else if(input.equals("trendingtweets")){
                    logger.debug("call trendingtweets from expelor class ");
                    expelor.trendingtweets();

                }
            }else if(input.equals("TimeLine")){
                logger.debug("call stg from TimeLine class ");

                TimeLine.stg();
            }else if(input.equals("ChatPage")){
                logger.debug("call show_chats from chatpage class ");
                ChatPage.show_chats();
                System.out.println("chatpage(personalmessaging/groupmessaging)");
                String input2=scan.nextLine();
                if (input2.equals("personalmessaging")){
                    logger.debug("call personal_msg from chatpage class ");

                    ChatPage.personal_msg();
                }else if(input2.equals("groupmessaging")){
                    System.out.println("enter your message;");
                    String mess=scan.nextLine();
                    ChatPage.group_message(mess);
                }

            }


        }
    }
}
