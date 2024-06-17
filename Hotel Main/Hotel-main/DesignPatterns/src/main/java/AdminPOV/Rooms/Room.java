package AdminPOV.Rooms;

public abstract class Room {
    public int RoomNumber;
    public boolean isOccupied;
    public RoomStatus roomStatus;
    public abstract String getRoomType();
    public abstract int getRoomPrice();
    public void setRoomNumber(int roomNumber) {
        RoomNumber = roomNumber;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

}
