package models;

import java.util.LinkedList;

public class Category {
    String catname;
    LinkedList<String> members;

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.members=new LinkedList<>();
        this.catname = catname;
    }

    public LinkedList<String> getMembers() {
        return members;
    }

    public void setMembers(LinkedList<String> members) {
        this.members = members;
    }

    public Category(String catname) {
        this.members=new LinkedList<>();
        this.catname = catname;
    }
}
