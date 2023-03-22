package seedu.pocketpal.frontend.commands;

import seedu.pocketpal.backend.Backend;
import seedu.pocketpal.communication.Request;
import seedu.pocketpal.communication.RequestMethod;
import seedu.pocketpal.communication.Response;
import seedu.pocketpal.communication.ResponseStatus;
import seedu.pocketpal.data.entry.Entry;
import seedu.pocketpal.data.parsing.EntryParser;
import seedu.pocketpal.frontend.constants.MessageConstants;
import seedu.pocketpal.frontend.exceptions.InvalidEntryIdException;
import seedu.pocketpal.frontend.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.Collections;

/**
 * Represents the delete feature in PocketPal. Users may specify
 * the index of the entry to be deleted
 * e.g., <code>/delete 10</code>
 */
public class DeleteCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());

    private final Integer[] entryIds;

    public DeleteCommand(Integer[] inputIds) {
        this.entryIds = inputIds;
    }

    /**
     * Deletes Entry object from entry log
     *
     * @param ui UI to output action result
     * @param backend  Backend to process requests
     */
    @Override
    public void execute(UI ui, Backend backend) throws InvalidEntryIdException {
        // delete request
        Arrays.sort(entryIds, Collections.reverseOrder());
        Integer[] uniqueEntryIds = Arrays.stream(entryIds).distinct().toArray(Integer[]::new);
        for(int entryId: uniqueEntryIds){
            System.out.println(entryId);
            Request requestGet = new Request(RequestMethod.GET, String.valueOf(entryId));
            Response responseGet = backend.requestEndpointEntry(requestGet);
            if (responseGet.getResponseStatus() == ResponseStatus.NOT_FOUND) {
                logger.log(Level.WARNING, "Input entry ID is invalid");
                throw new InvalidEntryIdException(MessageConstants.MESSAGE_INVALID_ID);
            }
        }
        for(int entryId: uniqueEntryIds){
            System.out.println(entryId);
            Request requestDelete = new Request(RequestMethod.DELETE, String.valueOf(entryId));
            Response responseDelete = backend.requestEndpointEntry(requestDelete);
            if (responseDelete.getResponseStatus() == ResponseStatus.NOT_FOUND) {
                logger.log(Level.WARNING, "Input entry ID is invalid");
                throw new InvalidEntryIdException(MessageConstants.MESSAGE_INVALID_ID);
            }
            Entry deletedEntry = EntryParser.deserialise(responseDelete.getData());
            ui.printExpenditureDeleted(deletedEntry);
        }
    }

    public Integer getEntryId(Integer entryId) {
        return this.entryIds[entryId];
    }
}
