package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Comment extends Tweet {
    private static Logger logger = LogManager.getLogger(Comment.class);

    public Comment(String tweetphrase, String username) {
        super(tweetphrase, username);
        logger.debug("making a comment instance");

    }
}
