package seedu.duke.ui;

import seedu.duke.constants.MessageConstants;
import seedu.duke.constants.UIConstants;
import seedu.duke.entries.Entry;

import java.text.DecimalFormat;
import java.util.List;

public class UI {
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

    /**
     * Returns a string based on the details of the Entry object and entryID entered.
     *
     * @param entry   entry Object to be formatted
     * @param entryID ID of the entry Object
     * @return String of details about the entry
     */
    private String formatViewEntries(Entry entry, int entryID) {
        String description = entry.getDescription();
        double price = entry.getAmount();
        String category = entry.getCategoryString();
        return "<" + Integer.toString(entryID) + ">: " + description +
                " (" + category + ") - $" + formatPrice(price);
    }

    /**
     * Combines all individual entry strings into a list of entries and prints the list, along with an acknowledgement
     * message.
     *
     * @param entryList List of entry objects with the user-specified length
     * @param category String denoting the category requested by the user
     */
    public void printEntriesToBeViewed(List<Entry> entryList, String category) {
        StringBuilder finalString = new StringBuilder();
        finalString.append("These are the latest " + entryList.size() + " entries from the " + category +
                " category.\n");

        for(int index = 0; index < entryList.size(); index ++){
            String formattedEntry = formatViewEntries(entryList.get(index),index+1);
            finalString.append(formattedEntry + "\n");
        }
        System.out.print(finalString.toString());
    }

    public void print(String output) {
        System.out.print(output);
    }

    public void printLine() {
        print(UIConstants.LINE);
    }

    public void printAwaitUserInput() {
        print(UIConstants.USER_INPUT_PROMPT);
    }

    public void printExit() {
        print(MessageConstants.MESSAGE_EXIT);
        printLine();
    }

    // TODO: Add expenditure edited
    public void printExpenditureAdded(String description, double priceDouble, String category) {
        print(MessageConstants.MESSAGE_EXPENDITURE_ADDED
                + formatExpenditure(description, priceDouble, category));
        printLine();
    }

    public void printExpenditureDeleted(String description, double priceDouble, String category) {
        print(MessageConstants.MESSAGE_EXPENDITURE_DELETED
                + formatExpenditure(description, priceDouble, category));
        printLine();
    }

    public void printHelp() {
        print(MessageConstants.MESSAGE_HELP);
        printLine();
    }

    public void printWelcome() {
        print(MessageConstants.MESSAGE_WELCOME);
        printLine();
    }
}
