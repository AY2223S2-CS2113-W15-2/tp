package seedu.duke.util;

import seedu.duke.constants.MessageConstants;
import seedu.duke.constants.UIConstants;

import java.text.DecimalFormat;

public class UIUtil {
    /**
     * Converts double into string, adds decimal places if required.
     *
     * @param priceDouble price
     * @return String of price with two decimal places
     */
    public static String formatPrice(double priceDouble) {
        return new DecimalFormat(UIConstants.FORMAT_2DP)
                .format(priceDouble);
    }

    /**
     * Converts expenditure into readable format.
     *
     * @param description Description of expenditure
     * @param price       Price of expenditure
     * @param category    Category of expenditure
     * @return Formatted expenditure string
     */
    public static String formatExpenditure(String description, double price, String category) {
        return MessageConstants.DESCRIPTION +
                description +
                MessageConstants.NEWLINE +
                MessageConstants.PRICE +
                formatPrice(price) +
                MessageConstants.NEWLINE +
                MessageConstants.CATEGORY +
                category +
                MessageConstants.NEWLINE;
    }
}
