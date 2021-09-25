package utils;

import models.GroupChats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ChatMethods {
    private static Logger logger = LogManager.getLogger(ChatMethods.class);
    public static GroupChats findChat(List<GroupChats> groups,String groupName) throws IOException {
        logger.debug("in findChat from ChatMethods class on values"+groups+"and"+groupName);
//        List<GroupChats> groups=new Filemethods().loadFromFileGroup();
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).getGroupName().equals(groupName)){
                return groups.get(i);
            }
        }
        return null;
    }
}
