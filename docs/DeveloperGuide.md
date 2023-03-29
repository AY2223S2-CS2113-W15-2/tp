<!-- omit in toc -->
# Developer Guide

<!-- omit in toc -->
## Table of Contents
- [Design](#design)
  - [Architecture](#architecture)
  - [Frontend](#frontend)
    - [Parser](#parser)
    - [Commands](#commands)
      - [Add Command](#add-command)
      - [Delete Command](#delete-command)
      - [Edit Command](#edit-command)
        - [Implementation](#implementation)
        - [Overall class diagram for editing an Entry](#overall-class-diagram-for-editing-an-entry)
        - [Overall sequence diagram for editing an Entry](#overall-sequence-diagram-for-editing-an-entry)
  - [Backend](#backend)
    - [Storage](#storage)
    - [API](#api)
      - [Endpoints](#endpoints)
        - [Creating a request](#creating-a-request)
        - [Making a request](#making-a-request)
      - [Access all entries available](#access-all-entries-available)
        - [Get recent or all entries](#get-recent-or-all-entries)
      - [Add, modify, view or delete an entry](#add-modify-view-or-delete-an-entry)
        - [Add an entry](#add-an-entry)
        - [View a specific entry](#view-a-specific-entry)
        - [Delete an entry](#delete-an-entry)
        - [Modify an entry](#modify-an-entry)
  - [Data Structure](#data-structure)
  - [Communication](#communication)
- [Testing](#testing)
  - [Unit Tests](#unit-tests)
  - [Instructions for manual testing](#instructions-for-manual-testing)
    - [Feature Testing](#feature-testing)
    - [Add expense: /add](#add-expense-add)
    - [View expense: /view](#view-expense-view)
    - [Delete expense: /delete](#delete-expense-delete)
    - [Edit expense: /edit](#edit-expense-edit)
    - [Show help menu: /help](#show-help-menu-help)
    - [Terminate program: /bye](#terminate-program-bye)
  - [Testing with sample data (from file)](#testing-with-sample-data-from-file)
    - [Exceptions](#exceptions)
- [Product scope](#product-scope)
  - [Target user profile](#target-user-profile)
  - [Value proposition](#value-proposition)
- [User Stories](#user-stories)
- [Non-Functional Requirements](#non-functional-requirements)
- [Acknowledgements](#acknowledgements)
  - [Documentation](#documentation)
  - [Storage](#storage-1)
  - [Unit Tests](#unit-tests-1)

# Design

## Architecture
![PocketPal Architecture](./static/PocketPalArchitecture.png)
![Backend Overview](./static/backend/BackendOverviewClassDiagram.png)

## Frontend

<!-- @@author adenteo -->
### Parser

The `Parser` class is a fundamental component instantiated as soon as PocketPal is initialised. Its __purpose__ is to
convert the user's input into structured data which then produces clear instructions for the rest of the program.

Some of its core features include:

- Breaking down user input and extracting the relevant data for further processing.
- Performing input validation and error handling to ensure that input data is in the correct format and ready to be
  processed.
- Converting the input data into the correct format and returning it as a `Command` class to be further processed by the
  application.

Here's a class diagram that shows the core structure of the `Parser` class.

![ParserClassDiagram](static/ParserClassDiagram.png)

How `Parser` works:

1. When a user enters a command, the `Frontend` uses `Parser` to resolve the user input via `parseUserInput()`.
2. Within `parseUserInput()`, the corresponding `parseXCommand()` (`X` is a placeholder for the various command
   names[^1] e.g. `parseAddCommand()`, `parseDeleteCommand()`.)  is invoked to validate that the user input is in the
   correct format. Any exceptions will be thrown and their corresponding error messages will be shown to the user via
   the `ui` class.
2. If the user input is valid, an `XCommand` object containing the relevant data is created and returned.
   E.g. `parseAddCommand()` would create a `AddCommand` object containing the description, price and category.
3. From there, the `XCommand` is ready to be executed by the program. (All `XCommand` classes inherit from `Command` and
   have corresponding `execute()` that carry out their specific instructions.)

[^1]: A list of currently supported commands in PocketPal can be found [here](../../UserGuide.html/features/)

The Sequence Diagram below illustrates the interactions within the `Parser` component when a user inputs the following
command: `/add McDonalds -c Food -p 10.50`

![ParserSequenceDiagram](static/ParserSequenceDiagram.png)

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>
<!-- @@author -->
<!-- @@author kaceycsn -->

### Commands

#### Add Command
The add entry mechanism is facilitated by `EntryLog`. Every instance of `AddCommand` is created with an `Entry` instance.

The following sequence diagram shows how the add command work:

![AddCommandSequenceDiagram](./static/AddCommandSequenceDiagram.png)
![AddCommandSequenceDiagramReference](./static/AddCommandSequenceDiagramReference.png)

Given below is an example usage scenario and how the add mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `EntryLog` will be initialized and contains no entry.

Step 2. The user executes `/add Lunch at McDonalds -category Food -price 19.9` command to add an `Entry` to the `EntryLog`.

_***Note.*** The command will fail its execution if its format is incorrect, and no `Entry` will be added to the `Entrylog`. An error message will be displayed informing the user._

Step 3. The command will be resolved by `Parser`, which would create an `AddCommmand` object.

Step 4. The `AddCommand` constructor creates and returns an `Entry` object containing the description, price and category to be added.

Step 5. When `execute()` method is called, a `Request` object is created.

Step 6. From there, the `Request` is ready to be handled. `addEntry()` method is called and the new `Entry` is added to the `EntryLog`.

Step 7. A success message is after the new `Entry` is added to the `EntryLog`.

The following activity diagram summarizes what happens when a user executes an add command:

![AddCommandActivityDiagram](./static/AddCommandActivityDiagram.png)

#### Delete Command
The 'delete' entry mechanism is facilitated by `EntryLog`.

Every instance of `DeleteCommand` is created with an Integer, which is the ID of the `Entry` to be deleted.

The following sequence diagram shows how the delete command work:

![DeleteCommandSequenceDiagram](./static/DeleteCommandSequenceDiagram.png)
![DeleteCommandSequenceDiagramGetResponse](./static/DeleteCommandSequenceDiagramDeleteResponse.png)
![DeleteCommandSequenceDiagramDeleteResponse](./static/DeleteCommandSequenceDiagramDeleteResponse.png)

Given below is an example usage scenario and how the delete mechanism behaves at each step.

Step 1. The user decides to remove an `Entry` from the `EntryLog` and executes `/delete 1` command.

_**Note:** The command will fail its execution if the index provided is invalid, and no `Entry` will be removed from the `EntryLog`. An error message will be displayed informing the user._

Step 2. The command will be resolved by `Parser`, which would create an `DeleteCommmand` object containing the index of the `Entry` to be deleted.

Step 3. When `execute()` method is called, a `Request` object is created.

Step 4. From there, the `Request` is ready to be handled. `deleteEntry()` method is called and the `Entry` is removed from `EntryLog`.

Step 5. A success message is after the `Entry` is removed from `EntryLog`.

The following activity diagram summarizes what happens when a user executes a delete   command:

![DeleteCommandActivityDiagram](./static/DeleteCommandActivityDiagram.png)

#### Edit Command

##### Implementation

**Step 1.** User runs Edit command, specifying the ID of the entry to edit, as well as the new attributes of the respective
fields.

**Step 2.** Parser extracts the relevant argument and returns a EditCommand object.

**Step 3.** The execute method of the EditCommand is called and a Request object is created. This request object specifies
the modifications of the various fields.

**Step 4.** The Request object is then parsed as an argument to the Backend Class, which parses this request as an argument
to a method in the EntryEndpoint class.

**Step 5.** The EntryEndpoint class then finds and modifies the entry as specified by the user.

**Step 6.** Upon successful completion of the modification, the EntryEndpoint class returns a Response object to the Backend
class. The Response object contains the updated fields of the entry.

**Step 7.** The Backend class calls the save() method to update the Storage class with the edited entry.It then returns
the Response object to the execute function in the EditCommand object.

**Step 8.** The printExpenditureEdited method under the UI class is then called in the execute function and an
acknowledgement message is printed to the user.

##### Overall class diagram for editing an Entry

![img.png](EditCommandClassDiagram.png)

##### Overall sequence diagram for editing an Entry

![img_1.png](EditCommandSequenceDiagram.png)

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>
<!-- @@author -->

<!-- ### UI

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div> -->

<!-- @@author jinxuan-owyong -->
## Backend

The backend uses a simplified RESTful API approach. This allows us to decouple code using the proven industry practices.

![Backend](./static/backend/BackendClassDiagram.png)

To find out more, visit the following sections:
- [Storage](#storage)
- [API](#api)
  - [Add, modify, view or delete an entry - `GET`](#add-modify-view-or-delete-an-entry)
  - [Access all entries available - `DELETE`, `GET`, `PATCH`, `POST`](#access-all-entries-available)

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

<!-- @@author nghochi123 -->
### Storage

The `Storage` class is responsible for the serialization of `Entry` data into a csv-like syntax, as well as the deserialization of that data back into `Entry` objects.

The main callable functions to be used are:

- `readFromDatabase()` - Deserializes data stored in text form back into `Entry` objects. Executed when PocketPal is instantiated
- `writeToDatabase()` - Serializes `Entry` objects in `EntryLog` into text form.
- `reset()` - Clears whatever is in the stored text file, without affecting what is in the current `EntryLog`.

The structure of the Storage class is as follows:

![StorageClassDiagram](./static/StorageClassDiagram.png)

The Sequence Diagram below illustrates the interactions within the `Parser` component upon initialization of PocketPal, as well as whenever data is being saved.

![StorageSequenceDiagram](./static/StorageSequenceDiagram.png) 

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>
<!-- @@author -->

<!-- @@author jinxuan-owyong -->
### API
#### Endpoints

![Endpoints](./static/backend/EndpointClassDiagram.png)

The sequence diagram for specific request handling at each endpoint can be viewed at their respective sections.

Each endpoint is a child class `Endpoint`. Currently, there are 2 endpoints available:

| Endpoint   | Method to call          |
| ---------- | ----------------------- |
| `/entry`   | `callEntryEndpoint()`   |
| `/entries` | `callEntriesEndpoint()` |

##### Creating a request

- To create a request, simply instantiate `pocketpal.communication.pocketpal.Request`
  with the desired request method.
- If there are any parameters associated with the request, you may add them using `addParam()`

```java
Request req = new Request(RequestMethod.PATCH);
req.addParam(RequestParams.EDIT_DESCRIPTION, "mango juice");
```

##### Making a request

- To call each endpoint, pass the `Request` into its corresponding method in the frontend.
- A `Response` will be returned to the frontend.

> All request body and parameter data should be serialised with `String.valueOf()` if not specified.

```java
Backend backend = new Backend();
Response res = backend.callEntryEndpoint(req);

if (res.getResponseStatus() != ResponseStatus.OK) {
   // handle status        
}

Entry entry = EntryParser.deserialise(res.getData());
// process entry
```

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

#### Access all entries available
##### Get recent or all entries

<details>
   <summary>Sequence diagram</summary>

   <img alt="Entries Endpoint [GET] Sequence Diagram" src="docs/../static/backend/EntriesEndpointGetSequence.png" />
</details>

`GET /entries`

__Body__

Number of recent entries to view if present, otherwise all entries will be returned.

__Parameters__

__`GET_SIZE`__ boolean

- If present, response will contain the number of entries.
- Otherwise, the following filters will be applied to the entries:

__`FILTER_BY_AMOUNT_START`__ double

- If this is the only parameter, it will be used as a minimum amount filter.
- If both filter amount parameters are present, it will be used as a amount range filter.

__`FILTER_BY_AMOUNT_END`__ double

- If this is the only parameter, it will be used as a maximum amount filter.
- If both filter amount parameters are present, it will be used as a amount range filter.

__`FILTER_BY_CATEGORY`__ Category

- Filter entries by category

__`FILTER_BY_QUERY`__ String

- Filter entries by user query

__Responses__

| Status Code | Description           | Remarks                                                                       |
| ----------- | --------------------- | ----------------------------------------------------------------------------- |
| `200`       | OK                    | Gson-serialised `List<Entry>`, deserialise with `EntryLogParser::deserialise` |
| `422`       | Unprocessable Content | -                                                                             |

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

#### Add, modify, view or delete an entry
##### Add an entry

`POST /entry`

<details>
   <summary>Sequence diagram</summary>

   <img alt="Entry Endpoint [POST] Sequence Diagram" src="docs/../static/backend/EntryEndpointGetSequence.png" />
</details>

__Body__

- Gson-serialised `Entry`, obtained using `Entry::serialise`

__Parameters__

N/A

__Responses__

| Status Code | Description | Remarks |
| ----------- | ----------- | ------- |
| `201`       | Created     | -       |


##### View a specific entry

`GET /entry`

__Body__

- 1-based index of entry to view _Required_

__Parameters__

N/A

__Responses__

| Status Code | Description | Remarks                                                              |
| ----------- | ----------- | -------------------------------------------------------------------- |
| `200`       | OK          | Gson-serialised `Entry`, deserialise with `EntryParser::deserialise` |
| `404`       | Not Found   | -                                                                    |


##### Delete an entry

`DELETE /entry`

<details>
   <summary>Sequence diagram</summary>

   <img alt="Entry Endpoint [DELETE] Sequence Diagram" src="docs/../static/backend/EntryEndpointDeleteSequence.png" />
</details>

__Body__

- 1-based index of entry to be deleted _Required_

__Parameters__

N/A

__Responses__

| Status Code | Description | Remarks                                                              |
| ----------- | ----------- | -------------------------------------------------------------------- |
| `200`       | OK          | Gson-serialised `Entry`, deserialise with `EntryParser::deserialise` |
| `404`       | Not Found   | -                                                                    |


##### Modify an entry

`PATCH /entry`

<details>
   <summary>Sequence diagram</summary>

   <img alt="Entry Endpoint [PATCH] Sequence Diagram" src="docs/../static/backend/EntryEndpointPatchSequence.png" />
</details>

__Body__

- 1-based index of entry to be deleted _Required_

__Parameters__

__`EDIT_AMOUNT`__ int

- The updated entry amount

__`EDIT_CATEGORY`__ Category

- The updated entry category

__`EDIT_DESCRIPTION`__ string

- The updated entry description

__Responses__

| Status Code | Description           | Remarks                                                              |
| ----------- | --------------------- | -------------------------------------------------------------------- |
| `200`       | OK                    | Gson-serialised `Entry`, deserialise with `EntryParser::deserialise` |
| `404`       | Not Found             | -                                                                    |
| `422`       | Unprocessable Content | -                                                                    |  |

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

## Data Structure

We use the `EntryLog` data structure to keep track of the entries entered by the user.

![Data Structure Class Diagram](./static/data/DataStructureClassDiagram.png)

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

## Communication

This project uses a simplified HTTP model, where the frontend sends a `Request` to the backend to perform data-related operations. The backend returns a `Response`, which is then processed by the frontend

![Simplified HTTP Model](static/communication/SimplifiedHTTPClassDiagram.png)

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>
<!-- @@author -->

<!-- # Implementation -->

<!-- ## [Proposed] Undo/Redo feature -->

# Testing

## Unit Tests
We adopt the Arrange, Act, Assert pattern for unit tests in this project.
This allows us to achieve a structured unit tests while balancing code readability and maintainability, and allowing a clear separation of the setup, operations and results.
For backend testing, we use utility classes such as `EntryTestUtil` and `BackendTestUtil` to reduce code repetition and to simplify the testing process.

<details>
<summary>Example</summary>

  ```java
  @DisplayName("Test /entries [GET]")
class TestEntriesGet extends EntryTestUtil {
    private static final EntryLog expectedEntryLog = new EntryLog();

    @BeforeEach
    void init() {
        TEST_BACKEND.clearData();
        expectedEntryLog.clearAllEntries();
    }

    @Test
    void entriesEndpointGET_recentEntries_correctEntries() {
        // Arrange
        addEntry(ENTRY_1);
        addEntry(ENTRY_2);
        addEntry(ENTRY_3);
        addEntry(ENTRY_4);
        expectedEntryLog.addEntry(ENTRY_3);
        expectedEntryLog.addEntry(ENTRY_4);

        // Act
        Request request = new Request(RequestMethod.GET); // recent 2 entries
        request.addParam(RequestParams.NUM_ENTRIES, "2");
        Response response = TEST_BACKEND.requestEndpointEntries(request);
        EntryLog returnedEntryLog = EntryLogParser.deserialise(response.getData());

        // Assert
        assertEquals(response.getResponseStatus(), ResponseStatus.OK);
        assertTrue(isSameEntryLog(expectedEntryLog, returnedEntryLog));
    }
}
  ```

  </details>

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

<!-- ## Integration Testing

Integration testing in our application is performed by testing `Frontend` and `Backend`, where we ensure that the relevant features 
for each major component is working, before they are combined and tested through system testing

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div> -->

<!-- ## System Testing

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div> -->

<!-- @@author adenteo -->
## Instructions for manual testing

Refer to the [user guide](/docs/UserGuide.md#getting-started) on launching PocketPal.

### Feature Testing

The following section provides instructions and code snippets for the manual testing of all currently supported features
in PocketPal.

---

**Do note that the test cases provided are not exhaustive and may not cover all possible outcomes.**

---

### Add expense: /add

**Usage:** `/add <DESCRIPTION> <-c | -category CATEGORY> <-p | -price PRICE>`

__Test Case 1 (All required flags are provided):__

- **Prerequisites:** None
- __Input:__ `/add McDonalds -c Food -p 10.50`

<details>
<summary>Expected output:</summary>

```
________________________________________________
The following expenditure has been added:
Description: McDonalds
Price: $10.50
Category: Food
28 Mar 2023, 01:04:42
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>


__Test Case 2 (Missing price flag):__

- **Prerequisites:** None
- __Input:__ `/add McDonalds -c Food`

<details>
<summary>Expected output:</summary>

```
________________________________________________
Please specify the description, category and price!
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>


### View expense: /view

**Usage:** `/view [COUNT] [-c | -category CATEGORY]`

__Test case 1 (No expenses exist):__

- **Prerequisites:** None.
- __Input:__ `/view`

<details>
<summary>Expected output:</summary>

```
________________________________________________
There are no entries available.
________________________________________________
Enter a command or /help to see the list of commands available.
```


__Test case 2 (Multiple expenses exist):__

- **Prerequisites:** At least **3** existing expenses.
- __Input:__ ```/view 3```

<details>
<summary>Expected output:</summary>

```
________________________________________________
These are the latest 3 entries.
<1>: McDonalds (Food) - $10.50 <<28 Mar 2023, 01:03:39>>
<2>: Air Jordan 1 (Clothing) - $200.00 <<28 Mar 2023, 01:04:30>>
<3>: Birthday Dinner (Food) - $150.00 <<28 Mar 2023, 01:04:42>>
________________________________________________

Enter a command or /help to see the list of commands available.
```
</details>

__Test case 3 (View entries in price range)__

- **Prerequisites:** At least **1** existing expense.
- __Input:__ ```/view -p 120.50 -p 210.00```

<details>
<summary>Expected output:</summary>

```
________________________________________________
These are the latest 2 entries.
<1>: Air Jordan 1 (Clothing) - $200.00 <<28 Mar 2023, 01:04:30>>
<2>: Birthday Dinner (Food) - $150.00 <<28 Mar 2023, 01:04:42>>
________________________________________________

Enter a command or /help to see the list of commands available.
```
</details>

### Delete expense: /delete

**Usage:** `/delete <EXPENSE_ID>`

You may view the list of existing expenses along with their corresponding indexes with `/view`.

__Test case 1:__

- **Prerequisites:** At least **3** expenses pre-added into the program, with the 3rd expense matching the one shown
in the example above.
- __Input:__ `/delete 3`

<details>
<summary>Expected output:</summary>

```
________________________________________________
The following expenditure has been deleted:
Description: Birthday Dinner
Price: $150.00
Category: Food
28 Mar 2023, 01:03:39
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>

__Test case 2__

- **Prerequisites:** Fewer than **5** expenses pre-added into the program
- __Input:__ `/delete 20`

<details>
<summary>Expected output:</summary>

```
________________________________________________
Please enter a valid numerical index!
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>


__Test case 3__

- **Prerequisites:** At least **2** expenses pre-added into the program
- __Input:__ `/delete 1 2`
 
<details>
<summary>Expected output:</summary>

```
________________________________________________
The following expenditure has been deleted:
Description: Light bulb
Price: $10.20
Category: Utilities
28 Mar 2023, 01:04:42
________________________________________________
The following expenditure has been deleted:
Description: Pizza
Price: $8.30
Category: Food
28 Mar 2023, 01:04:30
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>

### Edit expense: /edit


**Usage:** `/edit <EXPENSE_ID> [FLAG...]`

__Test case 1 (Editing all flags)__

- **Prerequisites:** At least **2** expenses pre-added into the program.
- __Input:__ `/edit 2 -p 300.50 -c others -d MacBook Air`

<details>
<summary>Expected output:</summary>

```
________________________________________________
The following expenditure has been updated:
Description: MacBook Air
Price: $300.50
Category: Others
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>

__Test case 2 (Editing price only)__

- **Prerequisites:** At least **2** expenses pre-added into the program, with the 2nd expense matching the one shown in the example above.
- __Input:__ `/edit 2 -p 300.50`

<details>
<summary>Expected output:</summary>

```
________________________________________________
The following expenditure has been updated:
Description: MacBook Air
Price: $300.50
Category: Others
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>

### Show help menu: /help

**Usage:** `/help`

__Test case__

- **Prerequisites:** None.
- __Input:__ `/help`

<details>
<summary>Expected output:</summary>

```
________________________________________________
PocketPal is a expense tracking app, optimised for use via a Command Line Interface.
Users can take advantage of the input flags for entering entries quickly.
Listed below are the various commands that are currently supported.

Add - Adds an expense to your current expenditure.
Usage: /add <DESCRIPTION> <-c CATEGORY> <-p PRICE>

Delete - Deletes a specified expense from your expenditure.
Usage: /delete <EXPENSE_ID>

Edit - Edits a specified expense in your current expenditure.
Usage: /edit <EXPENSE_ID> [FLAG...]

View - Displays a list of your current expenditure.
Usage: /view [COUNT]

Help - Displays the help menu.
Usage: /help

Exit - Terminates PocketPal.
Usage: /bye
________________________________________________
Enter a command or /help to see the list of commands available.
```
</details>

### Terminate program: /bye

**Usage:** `/bye`

__Test case__

- **Prerequisites:** None.
- __Input:__ `/delete 3`

<details>
<summary>Expected output:</summary>

```
________________________________________________
Bye. See you again :)
________________________________________________
```
</details>

---
More test cases will be added as more features are introduced.

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

## Testing with sample data (from file)

PocketPal stores data in a *storage.txt* file under the "*data/*" directory. Each row in the "*storage.txt*" file
represents a single expense Entry. Each column in each row should have 3 columns, representing the *description* of the
Entry, *amount* associated with the Entry and *category* of the Entry in that order, and are separated with the ","
delimiter. All of them are in the String format.

An example *storage.txt* file that will be readable by PocketPal is as such:

'''
Apple Juice,5.50,Food
Bus Card,50,Transportation
Paracetamol,10.39,Medical
'''

which will give us 3 Entries.

As of the time of writing, the available categories are:

- Clothing
- Entertainment
- Food
- Medical
- Others
- Personal
- Transportation
- Utilities
- Income

An empty input for category is not allowed. If necessary, use the "Others" category.

### Exceptions

Exceptions are thrown for a couple of cases where files are being read. If you wish to test the exceptions, they can be
replicated as follows:

1. Delimiter is invalid: If the delimiter is not the comma (","), it is not recognised as a delimiter and will not be
   processed correctly.
   Example row: Apple Juice|5.50|Food - In this case, the pipe ("|") is used as a delimiter, which is not allowed.
2. Amount is invalid: If the amount is not a numeric, it is not recognised as a valid amount and will not be processed
   correctly.
   Example row: Apple Juice,5A6B,Food - In this case, the amount is "5A6B", which is not a numeric and therefore not
   allowed.
3. Category is invalid: If the category is not a string from the list of allowed categories (see above), it is not
   recognised as a valid category and will not be processed correctly.
   Example row: Apple Juice,5.50,Drink - In this case, the category is "Drink", which is not a valid category and
   therefore not allowed.
4. Not enough columns: If a row has insufficient columns compared to what is needed, the Entry cannot be created.
   Example row: Apple Juice,5.50 - In this case, there are only two categories which is not allowed.

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

# Product scope

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

## Target user profile

{Describe the target user profile}

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

## Value proposition

{Describe the value proposition: what problem does it solve?}

# User Stories

| Version | As a ... | I want to ... | So that I can ... |
| ------- | -------- | ------------- | ----------------- |


<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

# Non-Functional Requirements

{Give non-functional requirements}

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

<!-- # Glossary

* *glossary item* - Definition

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div> -->

# Acknowledgements

## Documentation

- [Github REST API documentation](https://docs.github.com/en/rest/quickstart?apiVersion=2022-11-28)
- [Diagrams.net](https://app.diagrams.net/)
- PlantUML

## Storage

- [Function `makeFileIfNotExists` - StackOverflow](https://stackoverflow.com/questions/9620683/java-fileoutputstream-create-file-if-not-exists)
- [Deleting files - w3Schools](https://www.w3schools.com/java/java_files_delete.asp)
- [BufferedReader - Baeldung](https://www.baeldung.com/java-buffered-reader)

## Unit Tests

- [Assert Exceptions Thrown - Baeldung](https://www.baeldung.com/junit-assert-exception)
- [Arrange, Act, Assert](https://java-design-patterns.com/patterns/arrange-act-assert)

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>