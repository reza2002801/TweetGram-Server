package models;

public class message extends Notification {
    public String PictureLink;
    private String contactname="";

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getPictureLink() {
        return PictureLink;
    }

    public void setPictureLink(String pictureLink) {
        PictureLink = pictureLink;
    }

    public message(String username, String message) {
        super(username, message);
        this.PictureLink = "";
    }
}
