package seedu.pocketpal.frontend.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.pocketpal.frontend.constants.MessageConstants;
import seedu.pocketpal.frontend.exceptions.InvalidArgumentsException;
import seedu.pocketpal.frontend.exceptions.InvalidCommandException;
import seedu.pocketpal.frontend.exceptions.MissingArgumentsException;


public class ParserTest {
    @Test
    public void parseUserInput_emptyDescription_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            parser.parseUserInput("/add -p 100 -c food");
        });

        String expectedMessage = MessageConstants.MESSAGE_MISSING_ARGS_ADD;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseUserInput_emptyPrice_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            parser.parseUserInput("/add expense1 -c food");
        });

        String expectedMessage = MessageConstants.MESSAGE_MISSING_ARGS_ADD;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseUserInput_emptyCategory_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            parser.parseUserInput("/add expense1 -p 100");
        });

        String expectedMessage = MessageConstants.MESSAGE_MISSING_ARGS_ADD;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseUserInput_nonNumericalPrice_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
            parser.parseUserInput("/add expense1 -p 10f0 -c food");
        });

        String expectedMessage = MessageConstants.MESSAGE_INVALID_PRICE;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseUserInput_emptyInput_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            parser.parseUserInput("");
        });

        String expectedMessage = MessageConstants.MESSAGE_EMPTY_INPUT;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseUserInput_invalidCommand_exceptionThrown() {
        assertThrows(InvalidCommandException.class,
                () -> {
                    Parser parser = new Parser();
                    parser.parseUserInput("/invalid");
                });
    }

    @Test
    public void parseUserInput_missingArguments_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            parser.parseUserInput("/add");
        });
        String expectedMessage = MessageConstants.MESSAGE_MISSING_ARGS_ADD;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseEditCommand_missingArguments_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            parser.parseUserInput("/edit");
        });
        String expectedMessage = MessageConstants.MESSAGE_MISSING_ARGS_EDIT;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseEditCommand_invalidExpenseId_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
            parser.parseUserInput("/edit f");
        });
        String expectedMessage = MessageConstants.MESSAGE_INVALID_ID;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseEditCommand_wrongPriceFormat_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
            parser.parseUserInput("/edit 10 -p 10f");
        });
        String expectedMessage = MessageConstants.MESSAGE_INVALID_PRICE;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseEditCommand_invalidCategory_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
            parser.parseUserInput("/edit 10 -c wrongCategory");
        });
        String expectedMessage = MessageConstants.MESSAGE_INVALID_CATEGORY;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseViewCommand_invalidExpenseId_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
            parser.parseUserInput("/view 10f");
        });
        String expectedMessage = MessageConstants.MESSAGE_INVALID_ID;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseViewCommand_negativeExpenseId_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
            parser.parseUserInput("/view -5");
        });
        String expectedMessage = MessageConstants.MESSAGE_INVALID_ID;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    public void parseUserInput_validAddCommand_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> {
            parser.parseUserInput("/add coffee -c food -p 3.50");
        });
    }

    @Test
    public void parseUserInput_validViewCommand_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/view"));
    }

    @Test
    public void parseViewCommand_validViewCategory_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/view -c food"));
    }

    @Test
    public void parseEditCommand_validEditCommand_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/edit 1 -d new description"));
    }

    @Test
    public void parseEditCommand_validEditCategory_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/edit 10 -c food"));
    }

    @Test
    public void parseEditCommand_validEditPrice_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/edit 10 -p 10"));
    }

    @Test
    public void parseUserInput_validDeleteCommand_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/delete 1"));
    }

    @Test
    public void parseUserInput_validHelpCommand_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/help"));
    }

    @Test
    public void parseUserInput_validByeCommand_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/bye"));
    }

    @Test
    public void parseUserInput_whitespaceTrimmed() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("   /view   "));
    }

    @Test
    public void parseDeleteCommand_missingArguments_exceptionThrown() {
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            Parser parser = new Parser();
            parser.parseUserInput("/delete");
        });
        String expectedMessage = MessageConstants.MESSAGE_INVALID_ID;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseDeleteCommand_invalidArguments_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
            parser.parseUserInput("/delete abc");
        });
        String expectedMessage = MessageConstants.MESSAGE_INVALID_ID;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseDeleteCommand_validArguments_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/delete 1"));
    }

    @Test
    public void parseAddCommand_missingArguments_exceptionThrown() {
        Parser parser = new Parser();
        Exception exception = assertThrows(MissingArgumentsException.class, () -> {
            parser.parseUserInput("/add");
        });
        String expectedMessage = MessageConstants.MESSAGE_MISSING_ARGS_ADD;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void parseViewCommand_validArguments_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/view 10"));
    }

    @Test
    public void parseViewCommand_validPriceStartEnd_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/view -p 300 -p 500"));
    }

    @Test
    public void parseViewCommand_validPriceStart_parsedSuccessfully() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.parseUserInput("/view -p 300"));
    }
}
