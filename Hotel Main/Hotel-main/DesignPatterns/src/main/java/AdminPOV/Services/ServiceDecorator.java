package AdminPOV.Services;

public class ServiceDecorator implements AdditionalService{

    @Override
    public int calculatePrice() {
        return 100;
    }
}
