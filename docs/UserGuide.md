---
layout: page
title: User Guide
---

## Welcome to TutorTrack

TutorTrack is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TutorTrack can get your student management tasks done faster than traditional GUI apps.

TutorTrack is designed to help you, as a tutor, efficiently organise and track your students. This includes adding, deleteing and archiving students as well as a students studental information such as name, subjects, payment status, attendance and much more.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Using this guide

* If you are new to TutorTrack, start with the Quick Start section to get set up and running in minutes.
* If you need help with specific commands, refer to the Command Summary for a concise overview.
* If you want a deeper understanding of a particular feature, explore the relevant section under Features.
* If you are a developer seeking technical or implementation details, check out our Developer Guide for architecture and design information.

--------------------------------------------------------------------------------------------------------------------

## Useful Notations and Glossary

While exploring TutorTrack, you will encounter several icons and terms used throughout this guide. The following tables explain their meanings to help you navigate the guide more effectively.

| Term | Meaning |
|:-----|:--------|
| **GUI** | *Graphical User Interface* — The visual interface of TutorTrack that displays students, subjects, and command results. |
| **CLI** | *Command Line Interface* — The text-based interface where users type commands to perform actions in TutorTrack. |
| **Command** | A text instruction entered by the user to perform an operation in TutorTrack (e.g., `add`, `delete`, `archive`). |
| **Parameter** | Information provided to a command to specify details. For example, in `add n/John Tan c/3B`, `n/John Tan` and `c/3B` are parameters. |
| **Case-sensitive** | When the casing of letters matters. For example, `add` is valid but `ADD` is not. |
| **Case-insensitive** | When the casing of letters does not matter. For example, `john` and `John` are treated as the same. |
| **Index** | The position number of a student as shown in the current displayed list (1-indexed). |
| **Mainstream OS** | Commonly used operating systems supported by TutorTrack — Windows, macOS, Linux, and Unix. |
| **Tutor** | An individual providing academic tutoring services. Tutors are the primary users of TutorTrack. |
| **Student** | A learner receiving academic tutoring. Each student has unique information (e.g., name, contact, subjects) and associated records (e.g., attendance, payments, academic performance). |
| **Parent Contact** | The emergency contact of a student’s parent or guardian, typically in the form of a phone number for safety and administrative purposes. |
| **Attendance** | The attendance record associated with a student, tracking presence, lateness, or absence for lessons. |
| **AttendanceStatus** | The recorded attendance status for a student during a lesson — can be **Present**, **Absent**, **Late**, or **Excused**. |
| **Archive** | A status indicating whether a student is **active** (currently enrolled or assigned to a tutor) or **archived** (no longer active but retained for recordkeeping). |
| **Subject Enrollment** | The mapping between students and the subjects they are studying. A student may be enrolled in multiple subjects. |
| **Payment Status** | The record indicating whether a student’s payment for tuition or lessons is **Paid**, **Unpaid**, or **Pending**. |


--------------------------------------------------------------------------------------------------------------------


## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add n/John Tan c/3B s/Math s/Science ec/91234567 att/Present pay/Paid asg/Completed` : Adds a student named `John Tan` to the Address Book.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `clear` : Deletes all students.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Navigating the GUI

(guide on how to use the GUI)


## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message with a link to access the full help page.

![help command](images/helpcommand.png)

**Format:** `help`

**What happens:** A help window opens with a link to this user guide.

---

### Adding a student: `add`

Adds a new student to the address book with their academic and contact information.

![add command](images/addcommand.png)

**Format:** `add n/NAME c/CLASS s/SUBJECT [s/SUBJECT]... ec/EMERGENCY_CONTACT [att/ATTENDANCE] [pay/PAYMENT_STATUS] [asg/ASSIGNMENT_STATUS]`

**Parameters:**
* `n/NAME` - Student's full name (required)
* `c/CLASS` - Student's class (e.g., 3B) (required)
* `s/SUBJECT` - Subject(s) the student is taking (at least one required, can add multiple)
* `ec/EMERGENCY_CONTACT` - Emergency contact phone number (required)
* `att/ATTENDANCE` - Current attendance status: Present, Absent, Late, or Excused (optional)
* `pay/PAYMENT_STATUS` - Payment status: Paid or Unpaid (optional)
* `asg/ASSIGNMENT_STATUS` - Assignment completion status: Completed or Incomplete (optional)

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
* You can add multiple subjects by using multiple `s/` prefixes
* Optional fields will use default values if not specified
* Names are case-sensitive
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
You cannot add a student with the same name and class as an existing student.
</div>

**Examples:**
* `add n/John Tan c/3B s/Math s/Science ec/91234567 att/Present pay/Paid asg/Completed`
    * Adds John Tan from class 3B taking Math and Science
* `add n/Sam Lee c/3A s/Art s/History ec/98765432`
    * Adds Sam Lee from class 3A (uses default values for optional fields)

**Expected output:**
```
New student added: John Tan; Class: 3B; Subjects: [Math, Science]; Emergency Contact: 91234567
```

---

### Listing all students : `list`

Shows a list of all active (non-archived) students in the address book.

![list command](images/listcommand.png)

**Format:** `list`

**What it does:**
* Displays all students who have not been archived
* Returns you to the active student view if you were viewing archived students
* Students are shown with their index numbers, names, classes, and other details

**Expected output:**
```
Listed all students
```

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command to reset any filters and see all active students after using `find` or other filtering commands.
</div>

### Editing a student : `edit`

Edits an existing student in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the student will be removed i.e adding of tags is not cumulative.
* You can remove all the student’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a student : `delete`

Deletes the specified student from the student list.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`. This action is **irreversible**
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​
* The command works on the last shown list of students, which may be filtered (e.g., after using `find`)
* If the provided index is invalid (e.g., out of range), an error message will be shown.

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the student list.
 ![result for 'list' followed by 'delete 2'](images/deletecommand_delete_from_studentlist.png)
* `find John` followed by `delete 2` deletes the 2nd student in the results of the `find` command.
 ![result for 'find John' followed by 'delete 2'](images/deletecommand_delete_after_find.png)
* `delete 3` where there are only 2 or fewer students results in the following error message: 
   **"The person index provided is invalid"**
 ![result for 'delete 3' on a student list of only two people](images/deletecommand_invalid_index.png)
### Archiving a student : `archive`

Archives the specified student from the address book.

Format: `archive INDEX`

* Archives the student at the specified `INDEX`.
* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, …​

### Viewing archived students : `listarchive`

Shows a list of all students in the address book.

Format: `listarchive`

Example:
* `listarchive`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME c/CLASS s/SUBJECT [s/SUBJECT]... ec/EMERGENCY_CONTACT [att/ATTENDANCE] [pay/PAYMENT_STATUS] [asg/ASSIGNMENT_STATUS]​` <br> e.g., `add n/John Tan c/3B s/Math s/Science ec/91234567 att/Present pay/Paid asg/Completed`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
**Archive** | `archive INDEX`<br> e.g., `archive 4`
**ViewArchived** | `listarchive` 
