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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class login {
    private static Logger logger = LogManager.getLogger(client.class);
    public static String UserName;
    public static Boolean same_account(List<User> a, String username){
        logger.debug("in same account from class login on values"+ a+"and"+username );
        if(a==null){
            return false;
        }else{
            for(int i=0 ;i<a.size();i++){
                if(a.get(i).getUsername().equalsIgnoreCase(username)){
                    return true;
                }
            }

        }
        return false;
    }
    public static Boolean same_account_email(List<User> a, String email){
        logger.debug("in same_account_email from class login on values"+a+"and"+email);

        if(a==null){
            return false;
        }else{
            for(int i=0 ;i<a.size();i++){
                if(a.get(i).getEmail().equalsIgnoreCase(email)){
                    return true;
                }
            }

        }
        return false;
    }
    public static String pass_finder(List<User> e, String username){
        logger.debug("in pass_finder from class login on values"+e+"and"+username);

        for(int i=0;i<e.size();i++){
            if (e.get(i).getUsername().equals(username)) {
                return e.get(i).getPassword();
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        logger.debug("in main from class login");

        Gson g=new GsonBuilder().setPrettyPrinting().create();
        List<User> e;
        try {
            Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
            e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
            r.close();
            logger.info("file created successfully");
        }catch (Exception f){
            e=new ArrayList<>();
            logger.error("there was problem in login class making file");

        }



//        List<user> e = g.fromJson(r, new TypeToken<List<user>>() {}.getType());

        Scanner scan =new Scanner(System.in);
        System.out.println("Already hava an account(Y/N): ");
        String input=scan.nextLine();
        if(input.equals("Y")){
            System.out.println("enter user name :");
            String username=scan.nextLine();
            while (true){
                logger.debug("call same account from class login on values"+ e+"and"+username );
                if(same_account(e,username)){
                    break;
                }else{
                    System.out.println("your user name has not been found try again !");
                }
                System.out.println("enter user name :");
                username=scan.nextLine();

            }
            logger.debug("call pass_finder from class login on values"+ e+"and"+username );
            String password=pass_finder(e,username);
            System.out.println("enter your password :");
            String pass=scan.nextLine();
            while(true){
                if(password.equals(pass)){
                    break;
                }else {
                    System.out.println("your pass is incorrect try again!!");
                    logger.info("wrong pass!");
                }
                System.out.println("enter your password :");
                pass=scan.nextLine();
            }
            logger.info("loged in successfully!");
            System.out.println("you loged in successfully!");
            UserName=username;

        }else if(input.equals("N")){
            System.out.println("enter username ");
            String username=scan.nextLine();
            while(true){
                logger.debug("call same_account from login class on values"+e+"and"+username);
                if (same_account(e,username)){
                    System.out.println("this account with this user alresdy exist !");
                }else{
                    break;
                }
                System.out.println("enter username ");
                username=scan.nextLine();
            }
            System.out.println("enter your email :");
            String email=scan.nextLine();
            while(true){
                logger.debug("call same_account_email from login class on values"+e+"and"+email);

                if (same_account_email(e,email)){
                    System.out.println("this account with this email alresdy exist");
                }
                else{
                    break;
                }
                System.out.println("enter your email :");
                email=scan.nextLine();
            }
            System.out.println("enter your firstname:");
            String firstname=scan.nextLine();
            System.out.println("enter your last name:");
            String lastname=scan.nextLine();
            System.out.println("enter your password:");
            String password=scan.nextLine();
            logger.debug("making a new user using values"+username+","+firstname+","+","+lastname+","+email+","+password);
            User newuser=new User(username,firstname,lastname,email,password);
//            System.out.println(e);
            if(e==null){
                e=new ArrayList<>();
            }
            e.add(newuser);
//            System.out.println(e);
            Writer w=new FileWriter("Accounts.json ");
            g.toJson(e,w);
            w.close();
            System.out.println("Your account has been created successfully!");
            logger.info("Your account has been created successfully!");
            UserName=username;

        }
    }


}
