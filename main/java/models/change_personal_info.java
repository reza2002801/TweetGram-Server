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

public class change_personal_info{
    private static Logger logger = LogManager.getLogger(change_personal_info.class);
    public static void main(String[] args) throws IOException {
        logger.debug("change");
        System.out.println("change personal info(back/ChangeFirstName/ChangeLastName/setBirthday/setBio/setPhoneNumber)");
        Scanner scan=new Scanner(System.in);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
//        System.out.println("done");
        while(true){
            System.out.println("change personal info(back/ChangeFirstName/ChangeLastName/setBirthday/setBio/setPhoneNumber)");
            String input=scan.nextLine();
            if(input.equals("ChangeFirstName")){
                System.out.println("enter your new FirstName: ");
                input=scan.nextLine();
                MainPage.acc_info(e, login.UserName).setFirstname(input);
                System.out.println("Done! ");
            }else if(input.equals("ChangeLastName")){
                System.out.println("enter your new lastname: ");
                input=scan.nextLine();
                User a= MainPage.acc_info(e, login.UserName);
                a.setLastname(input);
                e.set(MainPage.index(e, login.UserName),a);
                System.out.println("Done! ");
            }else if(input.equals("setBirthday")){
                System.out.println("enter your Birthday like this: Year/Month/day like 2020/05/22");
                input=scan.nextLine();
                MainPage.acc_info(e, login.UserName).setBirthday(input);
                System.out.println("done");

            }else if(input.equals("setBio")){
                System.out.println("enter your bio");
                input=scan.nextLine();
                MainPage.acc_info(e, login.UserName).setBio(input);
                System.out.println("done!");
            }else if(input.equals("setPhoneNumber")){
                System.out.println("enter your phone number :");
                input=scan.nextLine();
                MainPage.acc_info(e, login.UserName).setPhonenumber(input);
                System.out.println("done!");
            }
            else if(input.equals("Change_UserName: ")){
                System.out.println("enter your new username: ");
                input=scan.nextLine();
                User a= MainPage.acc_info(e, login.UserName);
                int i= MainPage.index(e, login.UserName);
//                        a.setUsername(input);
                e.get(i).setUsername(input);
                login.UserName=input;
                System.out.println("Done! ");
            }else if(input.equals("back")){
                break;
            }
            Writer w=new FileWriter("Accounts.json ");
            g.toJson(e,w);
            w.close();
            logger.info("write to file");

        }
    }
}
