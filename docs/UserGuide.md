<!-- omit in toc -->

# User Guide

<!-- omit in toc -->

## Table of Contents

- [Getting Started](#getting-started)
- [Features](#features)
    - [Adding an expense: `/add`](#adding-an-expense-add)
    - [Deleting an expense: `/delete`](#deleting-an-expense-delete)
    - [Edit an expense: `/edit`](#edit-an-expense-edit)
    - [View an expense: `/view`](#view-an-expense-view)
    - [Show help menu: `/help`](#show-help-menu-help)
    - [Exit Program: `/bye`](#exit-program-bye)
    - [Supported Categories](#supported-categories)
- [Command Summary](#command-summary)
- [Frequently Asked Questions](#frequently-asked-questions)

## Getting Started

1. Ensure that you have Java `11` and above
   installed [(Installation Guide)](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A)
2. Download our latest release of `PocketPal.jar` [here](https://github.com/AY2223S2-CS2113-W15-2/tp/releases)
3. Run the application
   with `java -jar PocketPal.jar` [(Running JAR Guide)](https://se-education.org/guides/tutorials/jar.html#running-jar-files)
4. You should see the following welcome screen
   ```
   Welcome to
   _____           _        _   _____      _
   |  __ \         | |      | | |  __ \    | |
   | |__) |__   ___| | _____| |_| |__) |_ _| |
   |  ___/ _ \ / __| |/ / _ \ __|  ___/ _` | |
   | |  | (_) | (__|   <  __/ |_| |  | (_| | |
   |_|   \___/ \___|_|\_\___|\__|_|   \__,_|_|

   How may I help you?
   ________________________________________________
   Enter a command or /help to see the list of commands available.
   > 
   ```
5. To enter an entry, you may use [`/add`](#adding-an-expense-add-add),
   or enter [`/help`](#show-help-menu-help-help) to view the help menu.

<!-- @@author adenteo -->
> The table below provides a summary of all the currently supported features in PocketPal.
> More detailed explanations on the usage of the commands are provided as well.

## Features

This user guide adopts the following conventions for the command-line syntax:

+ Angle brackets (`<>`) indicate that the enclosed arguments are mandatory.
+ Square brackets (`[]`) indicate that the enclosed arguments are optional.
+ Ellipsis (`...`) indicate that the preceding argument can be repeated several times in one command.
+ Pipe or vertical line (`|`) indicates a choice within an argument. You can select either one of them, but cannot
  select more than one.

All arguments starting with a single dash (`-`) will be treated as options.

If you face any problems, do visit the [FAQ](#frequently-asked-questions) segment!

| Command                                |                      Function                       |
|----------------------------------------|:---------------------------------------------------:|
| [/add](#adding-an-expense-add)         |                   Adds an expense                   |
| [/delete](#deleting-an-expense-delete) |                 Deletes an expense                  |
| [/edit](#edit-an-expense-edit)         |                  Edits an expense                   |
| [/view](#view-an-expense-view)         | Displays details of an expense e.g. Price, Category |
| [/help](#show-help-menu-help)          |               Displays the help menu                |
| [/bye](#exit-program-bye)              |               Terminates the program                |

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

### Adding an expense: `/add`

Adds an expense to your current expenditure.

Format: `/add -d <description> -c <category> -p <price>`

Options:

- `-d | -description`: Description of the expense.
    - All characters except comma (,) are valid.
    - Multiple words separated by spaces are allowed. However, a word should not start with dash (`-`) or it will be
      treated as an option.
- `-c | -category`: Category of the expense.
    - Must be a **one-word** category currently [supported](#supported-categories) in PocketPal.
    - Non case-sensitive.
- `-p | -price`: Price of the expense.
    - Must be a non-negative numeric or decimal value.
- The order of the options are interchangeable, but they are all **required**.

Example of usage:

`/add -d Lunch at McDonalds -category Food -price 19.9`

`/add -d Apple Macbook Air -p 1300 -category Personal`

`/add -p 1300 -c Personal -d Apple Macbook Air`

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

### Deleting an expense: `/delete`

Deletes specified expense(s) from your current expenditure.

The expense IDs can be obtained from the [`/view`](#view-an-expense-view) command.

Format: `/delete <index> [additional_index...]`

- `index`, `additional_index`: Index of the expense to be deleted.
    - Index must be a positive integer. The maximum index allowed is the total number of existing expenses.
    - Additional indexes must be separated by spaces.

Example of usage:

`/delete 5`

`/delete 4 6 10`

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

### Edit an expense: `/edit`

Edits a specified expense in your current expenditure with the given flag(s).

Format: `/edit <index> [options]`

- `index`: Index of the expense to be deleted.
    - Must be a positive integer. The maximum index allowed is the number of existing expenses.

Options:

- `-d | -description` `<description>`: New description of the expense.
    - All characters except comma (,) are valid.
    - Multiple words are allowed.
- `-c | -category` `<category>`: New category of the expense.
    - Must be a **one-word** category currently [supported](#supported-categories) in PocketPal.
    - Non case-sensitive.
- `-p | -price` `<price>`: New price of the expense.
    - Must be a non-negative numeric or decimal value.

The order of the options are interchangeable.

If none of the options are specified,or if they are provided with empty values, the corresponding expense fields will
remain unchanged.

Example of usage:

`/edit 5 -p 10.50`

`/edit 5 -description Grab to school -c Transportation`

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

### View an expense: `/view`

Displays a list of your current expenditure.

Format: `/view [count] [filter_options]`

- `count`: Number of expenses to be listed.
    - Must be a positive integer.
    - If not specified, or if count is greater than number of existing expenses, all expenses will be listed.

### Filter options

**Filter by category**

- `-c | -category`  `<category>`: Category of expenses to be listed.
    - Must be a **one-word** category currently [supported](#supported-categories) in PocketPal.
    - Non case-sensitive.
    - If not specified, expenses of all categories will be listed.

**Filter by price**

- `-p | -price` `<min_price>`: Minimum price of expenses to be listed.
- `-p | -price` `<max_price>` Maximum price of expenses to be listed.

Note:

- If `max_price` and `min_price` are both specified, all expenses between `min_price` and `max_price` **inclusive** will
  be listed.
- If only `min_price` is specified, all expenses greater than or equal to `min_price` will be listed.
- `min_price` must be smaller than `max_price`. i.e. **`min_price` should be entered before `max_price`.**

**Filter by date range**

- `-sd, -startdate` `<start_date>`: Starting date of expenses to be listed.
- `-ed, -enddate` `<end_date>`: Ending date of expenses to be listed.

Note:

- `start_date`, `end_date` must be in `dd/MM/yy` format.
- Both flags are **required** if user wishes to use this
  option.

Order of options are interchangeable.

Example of usage:

`/view 10`

`/view -c food`

`/view -sd 12/03/23 -ed 11/04/23 -c food`

`/view -c food -p 2 -sd 12/03/23 -ed 11/04/23`

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

### Show help menu: `/help`

Displays the help menu.

Format: `/help`

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

### Exit Program: `/bye`

Terminates PocketPal.

Format: `/bye`

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

### Supported Categories

These are the categories currently supported by PocketPal:

`Clothing, Entertainment, Food, Medical, Personal, Transportation, Utilities, Income, Others`

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

## Command Summary

| Command | Format                                                                                                   |
|--------:|----------------------------------------------------------------------------------------------------------|
|    /add | /add -d &lt;description&gt; -c &lt;category&gt; -p &lt;price&gt;                                         |
|   /view | /view [count] [-c &lt;category&gt;] [-p &lt;price&gt;]<br/>[-sd &lt;start_date&gt; -ed &lt;end_date&gt;] |
|   /edit | /edit &lt;index&gt; [-c &lt;category&gt;] [-p &lt;price&gt;]<br/>[-d &lt;description&gt;]                |
| /delete | /delete &lt;index&gt; [additional_index...]                                                              |
|   /help | /help                                                                                                    |
|    /bye | /bye                                                                                                     |

<!-- @@author -->

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

## Frequently Asked Questions

> __Q:__ I am facing trouble starting the application. Do you know what might be the issue?
>
> __A:__ Please ensure that you have Java `11` and above installed on your machine.
> You may find more instructions at the [Getting Started](#getting-started) section

> __Q:__ How do I know whether the data entered is saved?
>
> __A:__ Your data is saved automatically when you interact with the application.
> There is no need to manually perform the save operation.

> __Q:__ How do I transfer my application data to another computer?
>
> __A:__ Your application data stored in `data/storage.txt`. To use PocketPal on another device,
> simply copy the `data` folder to the same directory as `PocketPal.jar` and start the
> application as per normal. Your stored entries will be automatically loaded.

> __Q:__ My application crashed. How do I report the problem to the developers?
>
> __A:__ We are sorry for the unpleasant experience with PocketPal, and we would be more than happy
> to solve the issue. You may file an issue on our GitHub stating how you arrived at the
> problem, so that our developers can assist you with the issue. Please also attach the application
> logs, which can be found at `logs/pocketpal.txt`

> __Q:__ I am developer. How can I find the source code and contribute to PocketPal?
>
> __A:__ PocketPal is an open-source application, and we welcome developers to share their ideas.
> You may find the source code on [GitHub](https://github.com/AY2223S2-CS2113-W15-2/tp/).

<div style="text-align: right;">
   <a href="#table-of-contents"> Back to Table of Contents </a>
</div>

