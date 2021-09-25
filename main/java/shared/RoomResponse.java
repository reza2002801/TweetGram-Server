package shared;

import models.Chat;
//import server.model.Models.Room;

import java.util.LinkedList;

public class RoomResponse {
    LinkedList<Chat> rooms;

    public RoomResponse(LinkedList<Chat> rooms){
        this.rooms = rooms;
    }

    public LinkedList<Chat> getRooms() {
        return rooms;
    }
}
