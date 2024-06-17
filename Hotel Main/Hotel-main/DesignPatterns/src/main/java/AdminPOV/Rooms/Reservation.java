package AdminPOV.Rooms;

import java.sql.Date;
import javafx.beans.property.*;

public class Reservation {
    private final IntegerProperty reservationID;
    private final StringProperty guestName;
    private final StringProperty roomType;
    private final IntegerProperty roomNumber;
    private final ObjectProperty<Date> startDate;
    private final ObjectProperty<Date> endDate;

    public Reservation(int reservationID, String guestName, String roomType, int roomNumber, Date startDate, Date endDate) {
        this.reservationID = new SimpleIntegerProperty(reservationID);
        this.guestName = new SimpleStringProperty(guestName);
        this.roomType = new SimpleStringProperty(roomType);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
    }

    public IntegerProperty reservationIDProperty() {
        return reservationID;
    }

    public StringProperty guestNameProperty() {
        return guestName;
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public IntegerProperty roomNumberProperty() {
        return roomNumber;
    }

    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDate;
    }
}
