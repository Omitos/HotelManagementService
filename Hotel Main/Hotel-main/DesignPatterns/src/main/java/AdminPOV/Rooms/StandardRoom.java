package AdminPOV.Rooms;

public class StandardRoom extends Room {
    @Override
    public String getRoomType() {
        return "Standard";
    }

    @Override
    public int getRoomPrice() {
        return 8500;
    }
}
