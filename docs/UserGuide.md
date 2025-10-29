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
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.
   <figure>
      <img src="images/Ui.png" alt="Student 1 Before" width="600"/>
      <figcaption><em>Figure 1: TutorTrack GUI</em></figcaption>
    </figure>

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all students.

   * `add n/John Tan c/3B s/Math s/Science ec/91234567 ps/Paid as/Completed` : Adds a student named `John Tan` to the Tutor Track.

   * `delete 3` : Deletes the 3rd student shown in the current list.

   * `clearcurrent` : Deletes all students in the current list.

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
  e.g `n/NAME [ps/PAYMENT_STATUS]` can be used as `n/John Doe ps/Paid` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times.<br>
  e.g. `[s/SUBJECTS]…​` can be used as `s/Math`, `s/Math s/Science` etc.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message with a link to access the full help page.
  <figure>
    <img src="images/helpcommand.png" alt="Student 1 Before" width="600"/>
    <figcaption><em>Figure 2: Help Window</em></figcaption>
  </figure>

**Format:** `help`

**What happens:** A help window opens with a link to this user guide.

---

### Adding a student: `add`

Adds a new student to the Tutor Track with their academic and contact information.

**Format:** `add n/NAME c/CLASS s/SUBJECT [s/SUBJECT]... ec/EMERGENCY_CONTACT [ps/PAYMENT_STATUS] [as/ASSIGNMENT_STATUS]`

**Parameters:**
* `n/NAME` - Student's full name (**required**)
* `c/CLASS` - Student's class (e.g., 3B) (**required**)
* `s/SUBJECT` - Subject(s) the student is taking (**at least one required**, use multiple `s/` prefixes to add more)
* `ec/EMERGENCY_CONTACT` - 8-digit Emergency contact phone number (**required**)
* `ps/PAYMENT_STATUS` - Payment status: Paid or Unpaid (_optional, default to Unpaid if omitted_)
* `asg/ASSIGNMENT_STATUS` - Assignment completion status: Completed or Incomplete (_optional, defaults to Incomplete if omitted_)

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
* You can add multiple subjects by using multiple `s/` prefixes
* Optional fields (PAYMENT_STATUS and ASSIGNMENT_STATUS) will use the default values mentioned if not specified
* Names are case-sensitive
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
You cannot add a student with the same name and class as an existing student.
</div>

**Examples:**
* `add n/John Tan c/3B s/Math s/Science ec/91234567` 
  * Accounts for compulsory fields and adds John Tan from class 3B (uses default values for optional fields)

  <figure>
    <img src="images/addcommand_compulsory.png" alt="Add Compulsory" width="600"/>
    <figcaption><em>Figure 3a: Compulsory Fields for Adding students</em></figcaption>
  </figure>

* `add n/Sarah Lim c/2A s/English ec/98765432 ps/Paid as/Completed`
  * Fills out optional fields along with compulsory ones while adding Sarah Lim from class 2A.

  <figure>
    <img src="images/addcommand_optional.png" alt="Add Optional" width="600"/>
    <figcaption><em>Figure 3b: Optional Fields for Adding students</em></figcaption>
  </figure> 

---

### Listing all students : `list`

Shows a list of all active (non-archived) students in the Tutor Track.

<figure>
    <img src="images/listcommand.png" alt="List" width="600"/>
    <figcaption><em>Figure 4: Sample student list shown after the list Command is called</em></figcaption>
  </figure>

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
Edits an existing student's information in Tutor Track 
**Format:** `edit [INDEX] [n/NAME] [c/CLASS] [s/SUBJECT...] [ec/EMERGENCY_CONTACT] [ps/PAYMENT_STATUS] [as/ASSIGNMENT_STATUS]`

**Parameters:**
*	`c/` — class (e.g., `3A`)
*	`s/` — subject(s). Supply multiple by repeating `s/` or using a comma-separated list (e.g., `s/Math s/Physics` or `s/Math, Physics`)
*	`ec/` — emergency contact (numbers only)
*	`ps/` — payment status (e.g., Paid, Pending, Overdue)
*	`as/` — assignment status (e.g., Submitted, Incomplete, Not Submitted)

**Description**:
*	Edits the student at the given `INDEX` (as shown in the current student list). `INDEX` is a positive integer.
*	Existing values are replaced by the new ones you supply.
* If the person at `INDEX`is not yet a Student, supplying any student-specific fields will convert them into a Student.
* Subjects are matched case-insensitively.

<div markdown="span" class="alert alert-warning">:exclamation:**Caution:**
*	Provide at least one field to change, otherwise, the command is rejected.
*	Ensure the target `INDEX` is visible after any filters (e.g., after find); otherwise you may edit the wrong entry.
*	Use consistent subject names to avoid near-duplicate entries (e.g., prefer Math over Mathematics if your list already uses Math).
*	If you intend to clear a field, use the app’s documented “clear” variant (if supported) rather than leaving the prefix empty.
</div>

⸻

**Examples:**
1. Changing a student's class and subjects
* Command: `edit 1 c/CS2103 s/CS`
  <table>
  <tr>
    <td><img src="images/editcommand1_before.png" alt="Student 1 Before" width="540" height="200"></td>
    <td><img src="images/editcommand1_after.png" alt="Student 1 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 5a: Before editing fields</em></td>
    <td align="center"><em>Figure 5b: After editing class & subjects</em></td>
  </tr>
</table>

2.	Changing a student's payment status
* Command: `edit 2 ps/Pending`
  <table>
  <tr>
    <td><img src="images/editcommand2_before.png" alt="Student 2 Before" width="540" height="200"></td>
    <td><img src="images/editcommand2_after.png" alt="Student 2 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 6a: Before editing fields</em></td>
    <td align="center"><em>Figure 6b: After setting Payment Status to “Pending”</em></td>
  </tr>
</table>

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* students matching all keywords will be returned (i.e. `AND` search).
  e.g. `find john tan` will return `john tan` and not `john wee`.

Examples:
* `find John` returns `John Teo` and `John Tan`

  <figure>
    <img src="images/findcommand_multiple.png" alt="Find Multiple" width="600"/>
    <figcaption><em>Figure 7: find john shows multiple students from the student list named John</em></figcaption>
  </figure>

* `find InvalidStudentName` returns an empty list of students since no student is named `InvalidStudentName`<br>
  <figure>
    <img src="images/findcommand_invalid.png" alt="Find Invalid" width="600"/>
    <figcaption><em>Figure 8: Finding an invalid student generates the following output</em></figcaption>
  </figure>

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
<table>
  <tr>
    <td><img src="images/deletecommand_list_before.png" alt="Student 2 Before" width="540" height="200"></td>
    <td><img src="images/deletecommand_list_after.png" alt="Student 2 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 9a: Student List before deleting John Lee</em></td>
    <td align="center"><em>Figure 9b: Student List after the command delete 3 is run</em></td>
  </tr>
</table>

* `find John` followed by `delete 2` deletes the 2nd student in the results of the `find` command.
<table>
  <tr>
    <td><img src="images/deletecommand_find_before.png" alt="Student 2 Before" width="540" height="200"></td>
    <td><img src="images/deletecommand_find_after.png" alt="Student 2 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 10a: Filtered Student List before deleting John Lee</em></td>
    <td align="center"><em>Figure 10b: Filtered Student List after the command delete 2 is run</em></td>
  </tr>
</table>

* `delete 5` where there are only 2 or fewer students results in the following error message:
  **"The person index provided is invalid"**
  <figure>
    <img src="images/deletecommand_error.png" alt="Delete Invalid" width="600"/>
    <figcaption><em>Figure 11: An example of an invalid delete command</em></figcaption>
  </figure>

### Archiving a student : `archive`

Archives the specified student from the Tutor Track.

Format: `archive INDEX`

Moves a student into the **archive list**.
Archived students are **not deleted** and can be viewed anytime using `listarchive`.

**Format:** `archive INDEX`

**Details:**
* Archives the student at the specified `INDEX` (from the currently displayed list).
* The index must be a **positive integer**.
* Archived students are hidden from the main student list but **preserved** in the data file.
* You can use `listarchive` to view all archived students or restore them in future versions.

**Examples:**
* `archive 3` archives the 3rd student in the list.
<table>
  <tr>
    <td><img src="images/archiveStudent1_command_before.png" alt="Student 2 Before" width="540" height="200"></td>
    <td><img src="images/archiveStudent1_command_after.png" alt="Student 2 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 12a: Student List before archiving Sarah Lim</em></td>
    <td align="center"><em>Figure 12b: Student List once Sarah is in the archive student list</em></td>
  </tr>
</table>

* `find Gary` followed by `archive 1` archives the 1st student in the filtered results.
<figure>
  <img src="images/findStudent2_command_view.png" alt="Find Student 2" width="600"/>
    <figcaption><em>Figure 13a: Find the desired student</em></figcaption>
  </figure>
  <table>
  <tr>
    <td><img src="images/archiveStudent2_command_view.png" alt="Student 2 Before" width="540" height="200"></td>
    <td><img src="images/archiveStudent2_command_after.png" alt="Student 2 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 13b: Index is specified based on the filtered student list (1 in this case)</em></td>
    <td align="center"><em>Figure 13c: John gets successfully archived</em></td>
  </tr>
</table>

### Viewing archived students : `listarchive`

Displays **all archived students** in the system.
Use this to view or verify students who have been archived previously.

**Format:** `listarchive`

**Details:**
* Displays a list of all archived students.
* This command has no parameters.
* Archived students retain all their details (class, subjects, attendance, payment, etc.) for recordkeeping.

**Example:**
* `listarchive`
  Shows all archived students with their full details.
  <figure>
  <img src="images/listarchive.png" alt="Archive List" width="600"/>
    <figcaption><em>Figure 14: Archive student list with all archived students</em></figcaption>
  </figure>

### Unarchiving students : `unarchive`

Unarchives students. Use this to unarchive students that have been archived to bring them back to the main list.

**Format** `unarchive INDEX`

**Details:**
* Unarchives at the student at the specified `INDEX` (from the archive list).
* The index must be a **positive integer**.
* If the provided index is invalid (e.g out of range), an error message will be shown.

**Example:**
* `unarchive 1` unarchives index 1 `John Tan`.
<figure>
    <img src="images/unarchive.png" alt="Unarchive" width="600"/>
    <figcaption><em>Figure 15a: Unarchiving a student from the archive student list</em>
    </figcaption>
</figure>

* unarchiving an invalid index displays an error message.

<figure>
    <img src="images/unarchive_error.png" alt="Student 2 Archive Command" width="600"/>
    <figcaption><em>Figure 15b: Error message when unarchiving invalid index</em>
    </figcaption>
</figure>

### Clearing all current student entries : `clearcurrent`

Deletes **all current students** from the student list. This action is **irreversible**

**Format:** `clearcurrent`

**Details:**
* Permanently deletes all student records from the **current list**.
* Does **not** affect archived students.
* The data file is automatically updated after the operation.

**Example:**
* `clearcurrent`
  Removes every student from the current list.
  <table>
  <tr>
    <td><img src="images/currentclear_command_view.png" alt="Student 2 Before" width="540" height="200"></td>
    <td><img src="images/currentclear_command_after.png" alt="Student 2 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 16a: Student List before it is cleared</em></td>
    <td align="center"><em>Figure 16b: Student List once all students have been cleared out</em></td>
  </tr>
</table>


<div markdown="span" class="alert alert-warning">:exclamation:**Caution:**
This command cannot be undone. All active records will be permanently deleted.
Archived records are not affected by this command.</div>

### Clearing all archived student entries : `cleararchive`

Deletes **all archived students** from the student list. This action is **irreversible**

**Format:** `cleararchive`

**Details:**
* Permanently deletes all student records from the **archived list**.
* Does **not** affect current students.
* The data file is automatically updated after the operation.

**Example:**
* `cleararchive`
  Removes every student from the archived list.
  <table>
  <tr>
    <td><img src="images/archiveclear_command_view.png" alt="Student 2 Before" width="540" height="200"></td>
    <td><img src="images/archiveclear_command_after.png" alt="Student 2 After" width="540" height="200"></td>
  </tr>
  <tr>
    <td align="center"><em>Figure 17a: Archive Student List before it is cleared</em></td>
    <td align="center"><em>Figure 17b: Archive Student List once all students have been cleared out</em></td>
  </tr>
</table>


<div markdown="span" class="alert alert-warning">:exclamation:**Caution:**
This command cannot be undone. All active records will be permanently deleted.
Current records are not affected by this command.</div>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

## Command Summary

| Action | Format, Examples (if necessary) |
|--------|------------------|
| **Help** | `help` |
| **Add** | `add n/NAME c/CLASS s/SUBJECT [s/SUBJECT]... ec/EMERGENCY_CONTACT [ps/PAYMENT_STATUS] [as/ASSIGNMENT_STATUS]`<br> e.g., `add n/John Tan c/3B s/Math s/Science ec/91234567 ps/Paid as/Completed` |
| **List** | `list` |
| **Edit** | `edit INDEX [n/NAME] [c/CLASS] [s/SUBJECT]... [ec/EMERGENCY_CONTACT] [ps/PAYMENT_STATUS] [as/ASSIGNMENT_STATUS]`<br> e.g., `edit 2 n/Betsy Crower c/4A s/Math s/Science ps/Pending` |
| **Find** | `find KEYWORD [MORE_KEYWORDS]` |
| **Delete** | `delete INDEX` <br> e.g., `delete 2` |
| **Archive** | `archive INDEX`<br> e.g., `archive 3` |
| **List Archived** | `listarchive` |
| **Unarchive** | `unarchive INDEX` <br> e.g., `unarchive 1` |
| **Clear** | `clear` |
| **Exit** | `exit` |

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

------------------------------------------------------------------------------------------------------------
