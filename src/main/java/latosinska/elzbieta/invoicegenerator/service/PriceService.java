package latosinska.elzbieta.invoicegenerator.service;

public class PriceService {

    public static Double roundPrice(Double price) {
        return Math.ceil(price*100)/100;
    }
}
