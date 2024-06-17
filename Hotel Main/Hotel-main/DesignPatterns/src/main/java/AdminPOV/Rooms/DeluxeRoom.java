package AdminPOV.Rooms;

public class DeluxeRoom extends Room {
    @Override
    public String getRoomType() {
        return "Deluxe";
    }
    @Override
    public int getRoomPrice() {
        return 15000;
    }
}
