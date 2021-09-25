package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.GroupChats;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Filemethods {
//    Logger logger = Logger.getLogger(MyClass.class);
    static Logger logger=LogManager.getLogger(Filemethods.class);
//    private static Logger logger = LogManager.getLogger(Filemethods.class);
    public List<User> loadFromFile() throws IOException {
        logger.debug("in loadFromFile from Filemethods class on values");
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Accounts.json"));
        List<User> e = g.fromJson(r, new TypeToken<List<User>>() {}.getType());
        r.close();
        return e;
    }
    public void saveToFile(List<User> e) throws IOException {
        logger.debug("in saveToFile from Filemethods class on values"+e);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Writer w=new FileWriter("Accounts.json ");
        g.toJson(e,w);
        w.close();
    }
    public List<GroupChats> loadFromFileGroup() throws IOException {
        logger.debug("in loadFromFileGroup from Filemethods class on values");
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Reader r= Files.newBufferedReader(Paths.get("Groups.json"));
        List<GroupChats> e = g.fromJson(r, new TypeToken<List<GroupChats>>() {}.getType());
        r.close();
        return e;
    }
    public void saveToFileGroup(List<GroupChats> e) throws IOException {
        logger.debug("in saveToFileGroup from Filemethods class on values"+e);
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        Writer w=new FileWriter("Groups.json");
        g.toJson(e,w);
        w.close();
    }

}
