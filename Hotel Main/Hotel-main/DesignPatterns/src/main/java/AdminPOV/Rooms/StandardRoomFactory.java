package AdminPOV.Rooms;

import AdminPOV.Rooms.Room;
import AdminPOV.Rooms.RoomFactory;
import AdminPOV.Rooms.StandardRoom;

public class StandardRoomFactory implements RoomFactory {
    @Override
    public Room createRoom() {
        return new StandardRoom();
    }
}
