package seedu.duke.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seedu.duke.entries.Category;
import seedu.duke.entries.Entry;
import seedu.duke.entrylog.EntryLog;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Test delete command")
public class DeleteCommandTest {
    private final Entry testEntry = new Entry("Rice", 8.50, Category.FOOD);
    private final List<Entry> testEntriesList = new ArrayList<>() {{
            add(testEntry);
        }};
    private final EntryLog testEntries = new EntryLog(testEntriesList);
    private final UI ui = new UI();

    @Test
    @DisplayName("Test constructor for DeleteCommand")
    void testDeleteCommand(){
        try {
            DeleteCommand deleteCommand = new DeleteCommand(1);
            assertEquals(0, deleteCommand.getEntryId());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    @Test
    @DisplayName("Test execute method in DeleteCommand")
    void testExecute(){
        try {
            DeleteCommand testCommand = assertDoesNotThrow(() -> new DeleteCommand(1));
            testCommand.executor(testEntries, ui);
            assertFalse(testEntries.getEntriesList().contains(testEntry));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }
}
