package AdminPOV.Rooms;

public class ExecutiveRoom extends Room{
    @Override
    public String getRoomType() {
        return "Executive";
    }
    @Override
    public int getRoomPrice() {
        return 25000;
    }
}
