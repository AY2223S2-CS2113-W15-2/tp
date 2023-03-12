package seedu.duke.commands;

import seedu.duke.entries.Category;
import seedu.duke.entries.Entry;
import seedu.duke.entrylog.EntryLog;
import seedu.duke.ui.UI;

import java.util.List;


public class ViewCommand extends Command {
    UI ui = new UI();
    int numberOfEntriesToView;
    Category categoryToView;
    public ViewCommand(int numberOfEntriesToView, Category categoryToView){
        this.categoryToView = categoryToView;
        this.numberOfEntriesToView = numberOfEntriesToView;
    }

    /**
     * This method is called in execute method to improve code readability.
     *
     * @param originalEntries list containing all entries in the desired category
     * @return trimmed list containing the latest "N" number of entries, where N is specified in the view command
     */
    private List<Entry> getLatestEntries(List<Entry> originalEntries){
        List<Entry> latestEntries = originalEntries.subList(originalEntries.size()-this.numberOfEntriesToView,
                originalEntries.size());
        return latestEntries;
    }
    
    @Override
    public void execute(EntryLog entries) {
        List<Entry> relevantEntries = entries.filterCategory(this.categoryToView);
        if(relevantEntries.size()>this.numberOfEntriesToView){
            relevantEntries = getLatestEntries(relevantEntries);
        }
        ui.printEntriesToBeViewed(relevantEntries, this.categoryToView.toString());
    }
}
