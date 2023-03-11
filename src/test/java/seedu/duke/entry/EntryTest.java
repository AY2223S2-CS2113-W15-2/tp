package seedu.duke.entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seedu.duke.constants.EntryConstants;
import seedu.duke.entries.Category;
import seedu.duke.entries.Entry;

@DisplayName("Test Entry class")
public class EntryTest {
    private static final double AMOUNT = 2.3;
    private static final String DESCRIPTION = "Mango pudding";
    private static final Category CATEGORY = Category.FOOD;
    private Entry entry;

    @BeforeEach
    void init() {
        entry = new Entry(DESCRIPTION, AMOUNT, CATEGORY);
    }

    @Test
    void testGetters() {
        assertEquals(entry.getAmount(), AMOUNT);
        assertEquals(entry.getCategory(), CATEGORY);
        assertEquals(entry.getDescription(), DESCRIPTION);
    }

    @Test
    void testGetCategoryString() {
        entry.setCategory(Category.CLOTHING);
        assertEquals(entry.getCategoryString(), EntryConstants.CLOTHING);
        entry.setCategory(Category.ENTERTAINMENT);
        assertEquals(entry.getCategoryString(), EntryConstants.ENTERTAINMENT);
        entry.setCategory(Category.FOOD);
        assertEquals(entry.getCategoryString(), EntryConstants.FOOD);
        entry.setCategory(Category.MEDICAL);
        assertEquals(entry.getCategoryString(), EntryConstants.MEDICAL);
        entry.setCategory(Category.OTHERS);
        assertEquals(entry.getCategoryString(), EntryConstants.OTHERS);
        entry.setCategory(Category.PERSONAL);
        assertEquals(entry.getCategoryString(), EntryConstants.PERSONAL);
        entry.setCategory(Category.TRANSPORTATION);
        assertEquals(entry.getCategoryString(), EntryConstants.TRANSPORTATION);
        entry.setCategory(Category.UTILITIES);
        assertEquals(entry.getCategoryString(), EntryConstants.UTILITIES);
    }

    @Test
    void testSetAmount() {
        final double newAmount = 43.12;
        entry.setAmount(newAmount);
        assertEquals(entry.getAmount(), newAmount);
    }

    @Test
    void testSetCategory() {
        final Category newCategory = Category.ENTERTAINMENT;
        entry.setCategory(newCategory);
        assertEquals(entry.getCategory(), newCategory);
    }

    @Test
    void testSetDescription() {
        final String newDescription = "Dried mango";
        entry.setDescription(newDescription);
        assertEquals(entry.getDescription(), newDescription);
    }
}
