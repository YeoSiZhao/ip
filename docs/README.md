# SixSeven

SixSeven is a CLI-based task management chatbot written in Java.  
It allows users to manage tasks efficiently through typed commands.  
Tasks are saved locally and automatically loaded when the application starts.

---

## Features

SixSeven supports:

- Add Todo tasks
- Add Deadline tasks
- Add Event tasks
- List all tasks
- Find tasks by keyword
- Mark and unmark tasks
- Delete tasks
- Persistent local storage

---

## Command Summary

| Command | Format |
|----------|----------|
| Add todo | `todo DESCRIPTION` |
| Add deadline | `deadline DESCRIPTION /by DATE` |
| Add event | `event DESCRIPTION /from START /to END` |
| List tasks | `list` |
| Find tasks | `find KEYWORD` |
| Mark task | `mark TASK_NUMBER` |
| Unmark task | `unmark TASK_NUMBER` |
| Delete task | `delete TASK_NUMBER` |
| Exit | `bye` |

---

## Usage Guide

### Add a Todo

Adds a task without date or time.

**Format**
```
todo DESCRIPTION
```

**Example**
```
todo read book
```

**Output**
```
Got it. I've added this task:
 [T][ ] read book
Now you have 1 task in the list.
```

---

### Add a Deadline

Adds a task with a due date.

**Format**
```
deadline DESCRIPTION /by DATE
```

**Example**
```
deadline submit report /by Friday
```

**Invalid format**
```
Deadline format: [description] /by [date]
```

---

### Add an Event

Adds a task with a start and end time.

**Format**
```
event DESCRIPTION /from START /to END
```

**Example**
```
event project meeting /from 2pm /to 4pm
```

**Invalid format**
```
Event format: description /from START /to END
```

---

### List Tasks

Displays all tasks.

**Format**
```
list
```

If empty:
```
No task available
```

---

### Find Tasks

Searches for tasks containing a keyword.

**Format**
```
find KEYWORD
```

Example:
```
find meeting
```

---

### Mark Task

Marks a task as completed.

**Format**
```
mark TASK_NUMBER
```

Example:
```
mark 2
```

Possible errors:
```
Mark and unmark must be followed by a number.
Task number is out of range.
```

---

### Unmark Task

Marks a task as not completed.

**Format**
```
unmark TASK_NUMBER
```

---

### Delete Task

Removes a task from the list.

**Format**
```
delete TASK_NUMBER
```

Example:
```
delete 1
```

Possible errors:
```
Delete must be followed by a number.
No such item to delete. Check item index again.
```

---

### Exit Program

Closes the application.

**Format**
```
bye
```

Output:
```
Bye. Hope to see you again soon!
```

---

## Data Storage

Tasks are saved in:

```
data/sixseven.txt
```

- Data is loaded at startup
- Changes are saved immediately after modification

---

## Project Structure

```
SixSeven
├── errors
│   ├── ErrorChecker
│   ├── SixSevenException
│   ├── EmptyException
│   ├── InvalidFormatException
│   └── UnknownCommandException
│
├── task
│   ├── Task
│   ├── Todo
│   ├── Deadline
│   ├── Event
│   └── TaskHelpers
│
├── utils
│   ├── CommandHandler
│   └── Helpers
│
├── storage
│   └── Storage
│
└── SixSeven.java
```

---

## Requirements

- Java 17 or later
- Terminal / Command Prompt

---

## Running the Program

Compile:
```
javac *.java
```

Run:
```
java SixSeven
```