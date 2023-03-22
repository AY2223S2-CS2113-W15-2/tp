package seedu.pocketpal.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seedu.pocketpal.data.entry.Category;
import seedu.pocketpal.data.entry.Entry;
import seedu.pocketpal.frontend.commands.DeleteCommand;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Test delete command")
public class DeleteCommandTest extends CommandTest {
    private final Entry testEntry1 = new Entry("Chicken Rice", 8.50, Category.FOOD);
    private final Entry testEntry2 = new Entry("Duck Rice", 8.50, Category.FOOD);

    @BeforeEach
    void init() {
        TEST_BACKEND.clearData();
        addEntry(testEntry1);
        addEntry(testEntry2);
    }

    @Test
    @DisplayName("Test constructor for DeleteCommand")
    void testDeleteCommand() {
        try {
            DeleteCommand deleteCommand = new DeleteCommand(new Integer[]{1});
            assertEquals(1, deleteCommand.getEntryId(0));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    @DisplayName("Test execute method in DeleteCommand")
    void testExecute() {
        try {
            DeleteCommand testCommand = assertDoesNotThrow(() -> new DeleteCommand(new Integer[]{2}));
            testCommand.execute(TEST_UI, TEST_BACKEND);
            assertNull(getEntryById(2));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    @DisplayName("Test multiple deletion of ")
    void testDeleteMultiple() {
        try {
            DeleteCommand testCommand = assertDoesNotThrow(() -> new DeleteCommand(new Integer[]{1, 2}));
            testCommand.execute(TEST_UI, TEST_BACKEND);
            assertNull(getEntryById(1));
            assertNull(getEntryById(2));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }
}
