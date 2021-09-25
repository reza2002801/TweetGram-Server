package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gsontest  {
    private static Logger logger = LogManager.getLogger(Gsontest.class);

    public static void main(String[] args) {
        logger.warn("jj");
        System.out.println("fuck");
        logger.error("eee");
    }
//        Gson g=new Gson();
//
//        user a=new user("reza","alvandi","rez","er","efe");
//        user b=new user("rezdsrfa","alvawefndi","rergez","errg","efedfh");
////        Writer w=new FileWriter("Accounts.json");
//        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
////        g.toJson(a,w);
////        w.close();
//        List<user> u=new ArrayList<>();
////        u.add(a);
////        u.add(b);
////        g.toJson(u,w);
////        w.close();
//        List<user> e = g.fromJson(r, new TypeToken<List<user>>() {}.getType());
//        r.close();
//        System.out.println(e.get(0).getEmail());
//
////
//        tweet t=new tweet();
//        LocalDateTime d=LocalDateTime.now();
//        String c=t.dateformat(d);
////        System.out.println(c);
//        Scanner scan=new Scanner(System.in);
//        String input=scan.nextLine();
//        comment n=new comment(input,login.UserName);
//        System.out.println(n.getn.getTweetphrase());


//        Scanner scan=new Scanner(System.in);
//        Gson g=new GsonBuilder().setPrettyPrinting().create();
//        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
//        List<user> e = g.fromJson(r, new TypeToken<List<user>>() {}.getType());
//        r.close();
//        chatpage.find_chat(e,"mamad","reza").setIsunread(false);
//        Writer w=new FileWriter("Accounts.json ");
//        g.toJson(e,w);
//        w.close();


//        File q=new File("gg.json");
//        String g=scan.nextLine();
    }


