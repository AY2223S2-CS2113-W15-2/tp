package seedu.pocketpal.frontend.parser;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.pocketpal.frontend.commands.Command;
import seedu.pocketpal.frontend.commands.AddCommand;
import seedu.pocketpal.frontend.commands.DeleteCommand;
import seedu.pocketpal.frontend.commands.EditCommand;
import seedu.pocketpal.frontend.commands.ExitCommand;
import seedu.pocketpal.frontend.commands.HelpCommand;
import seedu.pocketpal.frontend.commands.ViewCommand;
import seedu.pocketpal.data.entry.Category;
import seedu.pocketpal.frontend.exceptions.InvalidArgumentsException;
import seedu.pocketpal.frontend.exceptions.InvalidCategoryException;
import seedu.pocketpal.frontend.exceptions.InvalidCommandException;
import seedu.pocketpal.frontend.exceptions.MissingArgumentsException;
import seedu.pocketpal.frontend.constants.MessageConstants;
import seedu.pocketpal.frontend.util.CategoryUtil;
import seedu.pocketpal.frontend.util.StringUtil;

public class Parser {

    private static final String COMMAND_ADD = "/add";
    private static final String COMMAND_VIEW = "/view";
    private static final String COMMAND_EDIT = "/edit";
    private static final String COMMAND_DELETE = "/delete";
    private static final String COMMAND_HELP = "/help";
    private static final String COMMAND_BYE = "/bye";
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /**
     * @param userInput The entire string of user input.
     * @throws InvalidCommandException   If command entered by user is not
     *                                   recognisable.
     * @throws InvalidArgumentsException If arguments following command is in
     *                                   incorrect format.
     * @throws MissingArgumentsException If required arguments are missing.
     */
    public Command parseUserInput(String userInput) throws
            InvalidCommandException, InvalidArgumentsException,
            MissingArgumentsException, InvalidCategoryException {
        logger.entering(Parser.class.getName(), "parseUserInput()");
        userInput = userInput.trim();
        if (userInput.isEmpty()) {
            logger.log(Level.WARNING, "User entered an empty string",
                    new MissingArgumentsException(MessageConstants.MESSAGE_EMPTY_INPUT));
            throw new MissingArgumentsException(MessageConstants.MESSAGE_EMPTY_INPUT);
        }
        String[] userInputArray = userInput.split(" ", 2);
        assert userInputArray.length >= 1 : "Input must contain at least one command";
        String command = userInputArray[0].toLowerCase();
        String arguments = userInput.replaceFirst(command, "").trim();
        logger.log(Level.INFO, "User input command: " + command);
        logger.log(Level.INFO, "User input arguments: " + arguments);
        switch (command) {
        case COMMAND_ADD:
            logger.exiting(Parser.class.getName(), "parseUserInput()");
            return parseAddCommand(arguments);
        case COMMAND_VIEW:
            logger.exiting(Parser.class.getName(), "parseUserInput()");
            return parseViewCommand(arguments);
        case COMMAND_EDIT:
            logger.exiting(Parser.class.getName(), "parseUserInput()");
            return parseEditCommand(arguments);
        case COMMAND_DELETE:
            logger.exiting(Parser.class.getName(), "parseUserInput()");
            return parseDeleteCommand(arguments);
        case COMMAND_HELP:
            logger.exiting(Parser.class.getName(), "parseUserInput()");
            return parseHelpCommand();
        case COMMAND_BYE:
            logger.exiting(Parser.class.getName(), "parseUserInput()");
            return parseByeCommand();
        default:
            logger.log(Level.WARNING, "User command is invalid");
            logger.exiting(Parser.class.getName(), "parseUserInput()");
            throw new InvalidCommandException(MessageConstants.MESSAGE_INVALID_COMMAND);
        }
    }

    /**
     * Returns a string array of length 3, containing the description, category and
     * price respectively.
     *
     * @param arguments User arguments entered after the add command.
     * @return String[] Array containing description, category and price respectively.
     */
    private static String[] parseAddArguments(String arguments) {
        logger.entering(Parser.class.getName(), "parseAddArguments()");
        String description = "";
        String category = "";
        String price = "";
        String[] argumentsArray = new String[3];
        Pattern descriptionPattern = Pattern.compile("(\\w+(\\s+\\w+)*)");
        Pattern categoryPattern = Pattern.compile("(-c|-category)\\s+(\\w+(\\s+\\w+)*)");
        Pattern pricePattern = Pattern.compile("(-p|-price)\\s+(\\S+)");

        Matcher matcher = descriptionPattern.matcher(arguments);
        if (matcher.find()) {
            description = matcher.group(0);
        }

        matcher = categoryPattern.matcher(arguments);
        if (matcher.find()) {
            category = matcher.group(2);
        }

        matcher = pricePattern.matcher(arguments);
        if (matcher.find()) {
            price = matcher.group(2);
        }

        argumentsArray[0] = description;
        argumentsArray[1] = category;
        argumentsArray[2] = price;
        logger.exiting(Parser.class.getName(), "parseAddArguments()");
        return argumentsArray;
    }

    /**
     * @param arguments User arguments entered after the command.
     * @throws MissingArgumentsException If user did not specify description,
     *                                   category and price.
     * @throws InvalidArgumentsException
     */
    private Command parseAddCommand(String arguments)
            throws MissingArgumentsException, InvalidArgumentsException, InvalidCategoryException {
        logger.entering(Parser.class.getName(), "parseAddCommand()");
        logger.info("Parsing add command with arguments: " + arguments);
        if (arguments.isEmpty()) {
            throw new MissingArgumentsException(MessageConstants.MESSAGE_MISSING_ARGS_ADD);
        }
        String[] argumentsArray = parseAddArguments(arguments);
        assert argumentsArray.length == 3 : "User input must contain description, category, and price";
        String description = argumentsArray[0];
        String category = argumentsArray[1];
        String price = argumentsArray[2];
        logger.info("User input description: " + description);
        logger.info("User input category: " + category);
        logger.info("User input price: " + price);
        if (description.isEmpty() || category.isEmpty() || price.isEmpty()) {
            logger.warning("Missing description/category/price: " + MessageConstants.MESSAGE_MISSING_ARGS_ADD);
            throw new MissingArgumentsException(MessageConstants.MESSAGE_MISSING_ARGS_ADD);
        }
        double priceDouble;
        try {
            priceDouble = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            logger.warning("Price not in numerical format: " + MessageConstants.MESSAGE_INVALID_PRICE);
            throw new InvalidArgumentsException(MessageConstants.MESSAGE_INVALID_PRICE);
        }
        logger.exiting(Parser.class.getName(), "parseAddCommand()");
        return new AddCommand(description, priceDouble, category);
    }

    private Command parseByeCommand() {
        // Print bye message
        logger.entering(Parser.class.getName(), "parseByeCommand()");
        logger.info("Program exiting.");
        logger.exiting(Parser.class.getName(), "parseByeCommand()");
        return new ExitCommand();
    }

    /**
     * @param arguments User arguments entered after the command.
     * @throws InvalidArgumentsException If ID entered is out of range, or not in
     *                                   integer format.
     * @throws MissingArgumentsException If required ID is not entered.
     */
    private Command parseDeleteCommand(String arguments)
            throws InvalidArgumentsException, MissingArgumentsException {
        logger.entering(Parser.class.getName(), "parseDeleteCommand()");
        logger.info("Parsing delete command with arguments: " + arguments);
        if (arguments.isEmpty()) {
            logger.log(Level.WARNING, "Index of the expense not specified");
            throw new MissingArgumentsException(MessageConstants.MESSAGE_INVALID_ID);
        }
        String[] argumentsArray = arguments.split(" ");
        assert argumentsArray.length >= 1 : "User input must contain at least one argument";
        // String expenseId = argumentsArray[0];
        int expenseIdInt;
        Integer[] expenseIds = new Integer[argumentsArray.length];
        try {
            for(int i = 0; i < argumentsArray.length; i++){
                expenseIdInt = Integer.parseInt(argumentsArray[i]);
                assert argumentsArray[i].matches("\\d+") : "Expense ID must be an integer";
                logger.log(Level.INFO, "Removing specified expense id {0} from list", argumentsArray[i]);
                expenseIds[i] = expenseIdInt;
            }
            // do something with taskId
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Index of the expense specified is not an integer");
            throw new InvalidArgumentsException(MessageConstants.MESSAGE_INVALID_ID);
        }
        logger.exiting(Parser.class.getName(), "parseDeleteCommand(arguments)");
        return new DeleteCommand(expenseIds);
    }

    private Command parseHelpCommand() {
        // Print help message
        logger.entering(Parser.class.getName(), "parseHelpCommand()");
        logger.info("Displaying help message.");
        logger.exiting(Parser.class.getName(), "parseHelpCommand()");
        return new HelpCommand();
    }

    /**
     * @param arguments User arguments entered after the edit command
     * @return String[] Array containing expense ID, description, category and price respectively.
     */
    private static String[] parseEditArguments(String arguments) {
        logger.entering(Parser.class.getName(), "parseEditArguments()");
        String expenseId = "";
        String description = "";
        String category = "";
        String price = "";
        String[] argumentsArray = new String[4];
        Pattern expenseIdPattern = Pattern.compile("(\\S+)");
        Pattern descriptionPattern = Pattern.compile("(-d|-description)\\s+(\\w+(\\s+\\w+)*)");
        Pattern categoryPattern = Pattern.compile("(-c|-category)\\s+(\\w+(\\s+\\w+)*)");
        Pattern pricePattern = Pattern.compile("(-p|-price)\\s+(\\S+)");

        Matcher matcher = expenseIdPattern.matcher(arguments);
        if (matcher.find()) {
            expenseId = matcher.group(0);
        }
        matcher = descriptionPattern.matcher(arguments);
        if (matcher.find()) {
            description = matcher.group(2);
        }
        matcher = categoryPattern.matcher(arguments);
        if (matcher.find()) {
            category = matcher.group(2);
        }

        matcher = pricePattern.matcher(arguments);
        if (matcher.find()) {
            price = matcher.group(2);
        }
        argumentsArray[0] = expenseId;
        argumentsArray[1] = description;
        argumentsArray[2] = category;
        argumentsArray[3] = price;

        logger.exiting(Parser.class.getName(), "parseEditArguments()");
        return argumentsArray;
    }

    /**
     * @param arguments User arguments entered after the command.
     * @throws MissingArgumentsException If required ID is not entered.
     * @throws InvalidArgumentsException If ID entered is out of range, or not in
     *                                   integer format.
     */
    private Command parseEditCommand(String arguments) throws MissingArgumentsException, InvalidArgumentsException {
        logger.entering(Parser.class.getName(), "parseEditCommand()");
        logger.info("Parsing arguments for edit command: " + arguments);
        if (arguments.isEmpty()) {
            logger.warning("Missing arguments for edit command: " + MessageConstants.MESSAGE_MISSING_ARGS_EDIT);
            throw new MissingArgumentsException(MessageConstants.MESSAGE_MISSING_ARGS_EDIT);
        }
        String[] argumentsArray = parseEditArguments(arguments);
        String expenseId = argumentsArray[0];
        String description = argumentsArray[1];
        String category = argumentsArray[2];
        String price = argumentsArray[3];
        logger.info("User input expense ID: " + expenseId);
        logger.info("User input description: " + description);
        logger.info("User input category: " + category);
        logger.info("User input price: " + price);

        try {
            Integer.parseInt(argumentsArray[0]);
        } catch (NumberFormatException e) {
            logger.warning("Expense ID is not an integer: " + MessageConstants.MESSAGE_INVALID_ID);
            throw new InvalidArgumentsException(MessageConstants.MESSAGE_INVALID_ID);
        }

        if (!price.isEmpty()) {
            try {
                Double.parseDouble(price);
            } catch (NumberFormatException e) {
                logger.warning("Price not in numerical format: " + MessageConstants.MESSAGE_INVALID_PRICE);
                throw new InvalidArgumentsException(MessageConstants.MESSAGE_INVALID_PRICE);
            }
        }

        if (!category.isEmpty()) {
            category = category.toUpperCase();
            try {
                Category.valueOf(category);
            } catch (IllegalArgumentException e) {
                logger.warning("Category does not exist: " + MessageConstants.MESSAGE_INVALID_CATEGORY);
                throw new InvalidArgumentsException(MessageConstants.MESSAGE_INVALID_CATEGORY);
            }
        }
        logger.exiting(Parser.class.getName(), "parseEditCommand()");
        return new EditCommand(expenseId, description, category, price);

    }

    /**
     * @param arguments User arguments entered after the command.
     * @throws InvalidArgumentsException If user specified a non-integer for expense
     *                                   ID.
     */
    private Command parseViewCommand(String arguments) throws InvalidArgumentsException, InvalidCategoryException {
        logger.entering(Parser.class.getName(), "parseViewCommand()");
        logger.info("Parsing view command with arguments: " + arguments);
        Category category = null;
        String categoryStr = "";
        String viewCount;
        int viewCountInt;
        if (arguments.isEmpty()) {
            logger.info("No count specified. Listing all expenses");
            // list all commands;
            return new ViewCommand(Integer.MAX_VALUE);
        }
        String[] argumentsArray = arguments.split(" ", 3);
        assert argumentsArray.length >= 1 : "User input contains at least 1 argument";
        Pattern categoryPattern = Pattern.compile("(-c|-category)\\s+(\\w+(\\s+\\w+)*)");
        Pattern viewCountPattern = Pattern.compile("\\d+");
        Matcher matcher = viewCountPattern.matcher(arguments);
        if (matcher.find()) {
            viewCount = matcher.group(0);
        } else { //count not specified
            viewCount = Integer.toString(Integer.MAX_VALUE);
        }
        matcher = categoryPattern.matcher(arguments);
        if (matcher.find()) {
            categoryStr = matcher.group(2);
            category = CategoryUtil.convertStringToCategory(StringUtil.toTitleCase(categoryStr));
        }
        try {
            viewCountInt = Integer.parseInt(viewCount);
        } catch (NumberFormatException e) {
            logger.warning("Expense ID is not an integer: " + MessageConstants.MESSAGE_INVALID_ID);
            throw new InvalidArgumentsException(MessageConstants.MESSAGE_INVALID_ID);
        }
        if (viewCountInt < 0) {
            throw new InvalidArgumentsException(MessageConstants.MESSAGE_INVALID_ID);
        }
        logger.info("User entered count:" + viewCount);
        logger.info("User entered category:" + categoryStr);
        logger.exiting(Parser.class.getName(), "parseViewCommand()");
        return new ViewCommand(viewCountInt, category);
    }
}
