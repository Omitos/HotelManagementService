package AdminPOV.Rooms;

public class DeluxeRoomFactory implements RoomFactory {
    @Override
    public Room createRoom() {
        return new DeluxeRoom();
    }
}
