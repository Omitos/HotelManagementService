package AdminPOV.Rooms;

public class ExecutiveRoomFactory implements RoomFactory{
    @Override
    public Room createRoom() {
        return new ExecutiveRoom();
    }
}
