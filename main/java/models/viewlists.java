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

public class viewlists {
    private static Logger logger = LogManager.getLogger(viewlists.class);

    public static void main(String[] args) throws IOException {
        logger.debug("in main from viewlist class ");

        System.out.println("view list(back/followers/followings/blacklist/see_profile)");
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();

        Scanner scan=new Scanner(System.in);
        System.out.println("which list u wanna see ?");
        String input=scan.nextLine();
        while(true){
            if(input.equals("followers")&& MainPage.acc_info(e, login.UserName).getFollowers()!=null){
                logger.info("view followers");
                User a= MainPage.acc_info(e, login.UserName);
                for (int i=0;i<a.getFollowers().size();i++){
                    if (MainPage.acc_info(e,a.getFollowers().get(i)).getIsactive()){
                        System.out.println("user name :"+ a.getFollowers().get(i));
                    }

//                            System.out.println("FullName :"+ a.getFollowers().get(i).getFirstname()+a.getFollowers().get(i).getLastname());
//                            System.out.println("Email :"+ a.getFollowers().get(i).getEmail());
                }
            }else if(input.equals("followings")&& MainPage.acc_info(e, login.UserName).getFollowings()!=null){
                logger.info("view followings");
                User a= MainPage.acc_info(e, login.UserName);
                for (int i=0;i<a.getFollowings().size();i++){
                    if (MainPage.acc_info(e,a.getFollowings().get(i)).getIsactive()){
                        System.out.println("user name :"+ a.getFollowings().get(i));
                    }

//                            System.out.println("FullName :"+ a.getFollowings().get(i).getFirstname()+a.getFollowings().get(i).getLastname());
//                            System.out.println("Email :"+ a.getFollowings().get(i).getEmail());
                }
            }else if(input.equals("blacklist")&& MainPage.acc_info(e, login.UserName).getBlackList()!=null){
                logger.info("view blacklist");
                User a= MainPage.acc_info(e, login.UserName);
                for (int i=0;i<a.getBlackList().size();i++){
                    if (MainPage.acc_info(e,a.getBlackList().get(i)).getIsactive()){
                        System.out.println("user name :"+ a.getBlackList().get(i));
                    }

//                            System.out.println("FullName :"+ a.getFollowings().get(i).getFirstname()+a.getFollowings().get(i).getLastname());
//                            System.out.println("Email :"+ a.getFollowings().get(i).getEmail());
                }
            }else if(input.equals("back")){
                break;
            }else if(input.equals("see_profile")){
                System.out.println("enter username :");
                input=scan.nextLine();
                while (true){
                    if (input.equals("back")){
                        break;
                    }
                    else if(MainPage.acc_info(e,input)!=null && MainPage.acc_info(e,input).getIsactive()==true){
                        logger.debug("call opr_on_profile from MainPage class on "+ MainPage.acc_info(e,input));

                        Profile.opr_on_profile(MainPage.acc_info(e,input));
                    }
                    else{
                        System.out.println("the username you've just enter is invalid:");
                    }

                    System.out.println("enter username :");
                    input=scan.nextLine();
                }
            }
            input=scan.nextLine();
            Writer w=new FileWriter("Accounts.json ");
            g.toJson(e,w);
            w.close();
            logger.info("write to file ");
        }
    }
}
