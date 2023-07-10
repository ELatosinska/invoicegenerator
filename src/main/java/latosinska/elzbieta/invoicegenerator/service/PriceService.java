package latosinska.elzbieta.invoicegenerator.service;

import java.util.Formatter;

public class PriceService {

    public static Double roundPrice(Double price) {
        return Math.ceil(price*100)/100;
    }
}
