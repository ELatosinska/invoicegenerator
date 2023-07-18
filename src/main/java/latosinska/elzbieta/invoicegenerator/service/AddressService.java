package latosinska.elzbieta.invoicegenerator.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressService {
    private static Pattern numbersPattern = Pattern.compile("\\d+[a-z]?");

    public static boolean isValidNumber(String number) {
        Matcher matcher = numbersPattern.matcher(number);
        return matcher.matches();
    }
}
