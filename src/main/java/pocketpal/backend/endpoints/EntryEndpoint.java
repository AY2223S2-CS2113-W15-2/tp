package pocketpal.backend.endpoints;

import pocketpal.communication.Request;
import pocketpal.communication.RequestParams;
import pocketpal.communication.Response;
import pocketpal.communication.ResponseStatus;
import pocketpal.data.entry.Entry;
import pocketpal.data.entrylog.EntryLog;
import pocketpal.data.parsing.EntryParser;
import pocketpal.frontend.exceptions.InvalidCategoryException;
import pocketpal.frontend.util.CategoryUtil;

import java.util.logging.Logger;

public class EntryEndpoint extends Endpoint {
    private static final Logger logger = Logger.getLogger(EntryEndpoint.class.getName());
    private final EntryLog entries;

    public EntryEndpoint(EntryLog entries) {
        this.entries = entries;
    }

    /**
     * Delete an entry from the list
     *
     * @param request The request should have the following data
     *                - data: 1-based index of entry
     * @return Response containing deleted entry if ID is valid, otherwise not found
     */
    @Override
    public Response handleDelete(Request request) {
        logger.info("/entry [DELETE]: request received");
        int targetId = Integer.parseInt(request.getBody());
        try {
            Entry deletedEntry = entries.deleteEntry(targetId - 1);
            logger.info("/entry [DELETE]: OK");
            return new Response(ResponseStatus.OK, deletedEntry.serialise());
        } catch (IndexOutOfBoundsException e) {
            logger.warning("/entry [DELETE]: received invalid entry ID");
            return new Response(ResponseStatus.NOT_FOUND, "");
        }
    }

    @Override
    public Response handleGet(Request request) {
        logger.info("/entry [GET]: request received - " + request.getBody());
        Entry entry = entries.getEntry(Integer.parseInt(request.getBody()));
        if (entry == null) {
            logger.warning("/entry [GET]: received invalid entry ID " + request.getBody());
            return new Response(ResponseStatus.NOT_FOUND, "");
        }
        logger.info("/entry [GET]: OK");
        return new Response(ResponseStatus.OK, entry.serialise());
    }

    /**
     * Edit the fields in an entry
     *
     * @param request The request should have the following data
     *                - data: 1-based index of the entry to be patched
     *                - param?: EDIT_AMOUNT
     *                - param?: EDIT_CATEGORY
     *                - param?: EDIT_DESCRIPTION
     * @return Response with the updated entry
     */
    @Override
    public Response handlePatch(Request request) {
        logger.info("/entry [PATCH]: request received");
        Entry editEntry = entries.getEntry(Integer.parseInt(request.getBody()));
        if (editEntry == null) {
            logger.warning("/entry [PATCH]: received invalid entry ID " + request.getBody());
            return new Response(ResponseStatus.NOT_FOUND, "");
        }

        if (request.hasParam(RequestParams.EDIT_CATEGORY)) {
            try {
                String category = request.getParam(RequestParams.EDIT_CATEGORY);
                editEntry.setCategory(CategoryUtil.convertStringToCategory(category));
                logger.info("/entry [PATCH]: update category" + request.getBody());
            } catch (InvalidCategoryException e) {
                logger.warning("/entry [PATCH]: received invalid category" + request.getBody());
                return new Response(ResponseStatus.UNPROCESSABLE_CONTENT, "");
            }
        }
        if (request.hasParam(RequestParams.EDIT_AMOUNT)) {
            double amount = Double.parseDouble(request.getParam(RequestParams.EDIT_AMOUNT));
            editEntry.setAmount(amount);
            logger.info("/entry [PATCH]: update amount" + request.getBody());
        }
        if (request.hasParam(RequestParams.EDIT_DESCRIPTION)) {
            String description = request.getParam(RequestParams.EDIT_DESCRIPTION);
            editEntry.setDescription(description);
            logger.info("/entry [PATCH]: update description" + request.getBody());
        }

        logger.info("/entry [PATCH]: OK");
        return new Response(ResponseStatus.OK, editEntry.serialise());
    }

    /**
     * Add a new entry to the list.
     *
     * @param request The request should have the following data
     *                - data: Serialised Entry to be added
     * @return Created response
     */
    @Override
    public Response handlePost(Request request) {
        logger.info("/entry [POST]: request received");
        String json = request.getBody();
        Entry entry = EntryParser.deserialise(json);
        entries.addEntry(entry);
        logger.info("/entry [POST]: CREATED");
        // TODO: validate request parameters
        return new Response(ResponseStatus.CREATED, "");
    }
}
