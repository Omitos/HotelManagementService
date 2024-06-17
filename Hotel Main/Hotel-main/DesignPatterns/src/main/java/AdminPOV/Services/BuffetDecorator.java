package AdminPOV.Services;

public class BuffetDecorator extends ServiceDecorator{
    AdditionalService additionalService;

    public BuffetDecorator(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }
    @Override
    public int calculatePrice() {
        return 3000 + additionalService.calculatePrice();
    }
}
