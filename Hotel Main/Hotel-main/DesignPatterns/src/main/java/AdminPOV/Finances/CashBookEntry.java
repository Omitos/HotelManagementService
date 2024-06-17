package AdminPOV.Finances;
import javafx.beans.property.*;

public class CashBookEntry {
    private final IntegerProperty id;
    private final StringProperty date;
    private final StringProperty guestName;
    private final IntegerProperty payment;

    public CashBookEntry(int id, String date, String guestName, int payment) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.guestName = new SimpleStringProperty(guestName);
        this.payment = new SimpleIntegerProperty(payment);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty guestNameProperty() {
        return guestName;
    }

    public IntegerProperty paymentProperty() {
        return payment;
    }
}
