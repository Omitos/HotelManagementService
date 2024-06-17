package AdminPOV.Services;

public class SPADecorator extends ServiceDecorator{
    AdditionalService additionalService;

    public SPADecorator(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }
    @Override
    public int calculatePrice() {
        return 3000 + additionalService.calculatePrice();
    }
}

