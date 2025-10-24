---
layout: default.md
title: "User Guide"
pageNav: 3
---

<style>
/* Professional Documentation Styling */
:root {
    --primary: #0066CC;
    --primary-dark: #004C99;
    --primary-light: #E6F2FF;
    --success: #00A86B;
    --warning: #FF8C00;
    --danger: #D9534F;
    --text-primary: #2C3E50;
    --text-secondary: #5A6C7D;
    --text-muted: #8492A6;
    --bg-primary: #FFFFFF;
    --bg-secondary: #F8FAFB;
    --bg-tertiary: #EFF3F6;
    --border-color: #D8E1E8;
    --code-bg: #F6F8FA;
    --shadow-sm: 0 1px 3px rgba(0, 0, 0, 0.06);
    --shadow-md: 0 4px 12px rgba(0, 0, 0, 0.08);
    --shadow-lg: 0 10px 30px rgba(0, 0, 0, 0.12);
}

body {
    font-family: -apple-system, BlinkMacSystemFont, 'Inter', 'Segoe UI', 'Helvetica Neue', Arial, sans-serif;
    line-height: 1.65;
    color: var(--text-primary);
    font-size: 16px;
    font-weight: 400;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

/* Typography Hierarchy */
h1 {
    font-size: 42px;
    font-weight: 700;
    letter-spacing: -0.025em;
    line-height: 1.2;
    margin-bottom: 16px;
    color: var(--text-primary);
    border-bottom: 3px solid var(--primary);
    padding-bottom: 16px;
}

h2 {
    font-size: 32px;
    font-weight: 700;
    letter-spacing: -0.02em;
    line-height: 1.3;
    margin-top: 56px;
    margin-bottom: 24px;
    color: var(--text-primary);
    position: relative;
    padding-left: 16px;
}

h2::before {
    content: '';
    position: absolute;
    left: 0;
    top: 8px;
    bottom: 8px;
    width: 4px;
    background: var(--primary);
    border-radius: 2px;
}

h3 {
    font-size: 24px;
    font-weight: 600;
    letter-spacing: -0.015em;
    line-height: 1.4;
    margin-top: 40px;
    margin-bottom: 16px;
    color: var(--text-primary);
}

h4 {
    font-size: 18px;
    font-weight: 600;
    margin-top: 24px;
    margin-bottom: 12px;
    color: var(--text-primary);
}

p {
    margin-bottom: 16px;
    color: var(--text-secondary);
}

strong {
    font-weight: 600;
    color: var(--text-primary);
}

a {
    color: var(--primary);
    text-decoration: none;
    border-bottom: 1px solid transparent;
    transition: all 0.2s ease;
}

a:hover {
    color: var(--primary-dark);
    border-bottom-color: var(--primary);
}

/* Code Styling */
code {
    background: var(--code-bg);
    padding: 2px 6px;
    border-radius: 4px;
    font-family: 'JetBrains Mono', 'SF Mono', Monaco, 'Cascadia Code', 'Courier New', monospace;
    font-size: 14px;
    color: var(--primary-dark);
    font-weight: 500;
    border: 1px solid var(--border-color);
}

pre {
    background: var(--text-primary);
    color: #E6EDF3;
    padding: 20px;
    border-radius: 8px;
    overflow-x: auto;
    margin: 24px 0;
    font-family: 'JetBrains Mono', 'SF Mono', Monaco, 'Cascadia Code', monospace;
    font-size: 14px;
    line-height: 1.6;
    box-shadow: var(--shadow-sm);
    border: 1px solid rgba(255, 255, 255, 0.1);
}

pre code {
    background: none;
    color: #E6EDF3;
    padding: 0;
    border: none;
    font-weight: 400;
}

/* Professional Callout Boxes */
blockquote {
    background: var(--primary-light);
    border-left: 4px solid var(--primary);
    padding: 16px 20px;
    border-radius: 6px;
    margin: 24px 0;
    box-shadow: var(--shadow-sm);
}

blockquote p {
    margin: 0;
    color: var(--text-primary);
    font-size: 15px;
}

blockquote strong {
    color: var(--primary);
    font-size: 14px;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    display: block;
    margin-bottom: 6px;
}

/* Hero Section */
.hero-intro {
    background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
    padding: 48px;
    border-radius: 12px;
    margin: 32px 0;
    box-shadow: var(--shadow-lg);
    position: relative;
    overflow: hidden;
}

.hero-intro::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url('data:image/svg+xml,<svg width="60" height="60" xmlns="http://www.w3.org/2000/svg"><circle cx="30" cy="30" r="1.5" fill="white" opacity="0.1"/></svg>');
    opacity: 0.4;
}

.hero-intro strong {
    color: #FFFFFF;
    font-size: 18px;
    position: relative;
}

.hero-intro p {
    color: rgba(255, 255, 255, 0.95);
    font-size: 16px;
    margin-bottom: 0;
    position: relative;
}

/* Professional Tables */
table {
    width: 100%;
    border-collapse: separate;
    border-spacing: 0;
    margin: 32px 0;
    background: var(--bg-primary);
    border-radius: 8px;
    overflow: hidden;
    box-shadow: var(--shadow-md);
    border: 1px solid var(--border-color);
}

th {
    background: var(--bg-tertiary);
    padding: 14px 16px;
    text-align: left;
    font-weight: 600;
    font-size: 14px;
    color: var(--text-primary);
    text-transform: uppercase;
    letter-spacing: 0.5px;
    border-bottom: 2px solid var(--border-color);
}

td {
    padding: 14px 16px;
    border-bottom: 1px solid var(--border-color);
    font-size: 15px;
    color: var(--text-secondary);
    vertical-align: top;
}

tr:last-child td {
    border-bottom: none;
}

tr:hover {
    background: var(--bg-secondary);
}

/* Lists */
ul, ol {
    margin: 16px 0;
    padding-left: 28px;
}

li {
    margin: 10px 0;
    color: var(--text-secondary);
    line-height: 1.6;
}

li::marker {
    color: var(--primary);
    font-weight: 600;
}

/* Horizontal Rules */
hr {
    border: none;
    height: 2px;
    background: linear-gradient(90deg, var(--border-color) 0%, transparent 100%);
    margin: 48px 0;
}

/* Warning Box */
.warning {
    background: #FFF4E6;
    border-left: 4px solid var(--warning);
    padding: 16px 20px;
    border-radius: 6px;
    margin: 24px 0;
    box-shadow: var(--shadow-sm);
}

.warning strong {
    color: var(--warning);
    font-size: 14px;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    display: block;
    margin-bottom: 6px;
}

.warning p {
    margin: 0;
    color: #8B5A00;
}

/* Info Badge */
.badge {
    display: inline-block;
    padding: 4px 10px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    margin-left: 8px;
}

.badge-new {
    background: var(--success);
    color: white;
}

.badge-required {
    background: var(--danger);
    color: white;
}

.badge-optional {
    background: var(--text-muted);
    color: white;
}

/* Section Numbering */
.section-number {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 32px;
    height: 32px;
    background: var(--primary);
    color: white;
    border-radius: 6px;
    font-weight: 700;
    font-size: 16px;
    margin-right: 12px;
}

/* Command Format Box */
.command-format {
    background: var(--bg-secondary);
    border: 2px solid var(--border-color);
    border-radius: 8px;
    padding: 16px 20px;
    margin: 16px 0;
    font-family: 'JetBrains Mono', monospace;
    font-size: 15px;
    color: var(--text-primary);
    font-weight: 500;
}

/* Parameter Legend */
.param-legend {
    background: var(--bg-tertiary);
    border-radius: 6px;
    padding: 16px;
    margin: 24px 0;
    font-size: 14px;
}

.param-legend ul {
    margin: 8px 0;
    padding-left: 20px;
}

.param-legend li {
    font-size: 14px;
    margin: 6px 0;
}

/* Quick Reference Cards */
.quick-ref-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: 16px;
    margin: 24px 0;
}

.quick-ref-card {
    background: var(--bg-primary);
    border: 1px solid var(--border-color);
    border-radius: 8px;
    padding: 20px;
    transition: all 0.2s ease;
}

.quick-ref-card:hover {
    box-shadow: var(--shadow-md);
    border-color: var(--primary);
    transform: translateY(-2px);
}

/* Responsive Design */
@media (max-width: 768px) {
    h1 { font-size: 32px; }
    h2 { font-size: 24px; }
    h3 { font-size: 20px; }
    
    .hero-intro {
        padding: 32px 24px;
    }
    
    table {
        font-size: 14px;
    }
    
    th, td {
        padding: 10px 12px;
    }
}

/* Print Styles */
@media print {
    .hero-intro {
        background: white !important;
        border: 2px solid var(--primary);
        color: var(--text-primary) !important;
    }
    
    .hero-intro strong,
    .hero-intro p {
        color: var(--text-primary) !important;
    }
}
</style>

# TalentNexus User Guide

<div class="hero-intro">

**TalentNexus** is a professional desktop application for managing recruits, optimized for use via a **Command Line Interface** (CLI) while maintaining the benefits of a Graphical User Interface (GUI).

Designed for recruitment professionals who value efficiency, TalentNexus enables faster contact management than traditional GUI applications through keyboard-driven workflows.

</div>

----

## Quick Start

<div class="section-number">1</div>

**Install Java Runtime**  
Ensure Java `17` or above is installed on your system.  
**Mac users:** Follow the [official JDK installation guide](https://se-education.org/guides/tutorials/javaInstallationMac.html) for macOS-specific instructions.

<div class="section-number">2</div>

**Download Application**  
Download the latest `.jar` file from the [official releases page](https://github.com/se-edu/addressbook-level3/releases).

<div class="section-number">3</div>

**Configure Home Directory**  
Copy the JAR file to your preferred application directory. This location will serve as the home folder for TalentNexus.

<div class="section-number">4</div>

**Launch Application**  
Open a terminal, navigate to the application directory, and execute:

```bash
java -jar talentnexus.jar
```

The graphical interface will launch within seconds, pre-populated with sample data for exploration.

<div class="section-number">5</div>

**Execute Commands**  
Type commands in the command box and press **Enter** to execute. Begin with `help` to access comprehensive documentation.

> **💡 PRO TIP**  
> TalentNexus is optimized for keyboard-driven workflows. Users proficient in typing will experience significant productivity gains compared to traditional mouse-based interfaces.

### Essential Commands Reference

* `help` — Display help documentation
* `list` — Display all active recruits
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` — Add new recruit
* `view 2` — Display detailed information for recruit #2
* `edit 1 p/91234567` — Modify phone number for recruit #1
* `find John` — Search recruits by name
* `sort` — Sort recruits alphabetically
* `archive 2` — Archive recruit #2
* `unarchive 1` — Restore recruit #1 from archive
* `delete 3` — Permanently remove recruit #3
* `export` — Export data to CSV format
* `clear` — Remove all recruits from database
* `exit` — Close application

----

## Command Format Conventions

<div class="param-legend">

**Parameter Notation:**

* `UPPER_CASE` — User-supplied parameters (e.g., `NAME`, `INDEX`)
* `[parameter]` — Optional parameters
* `parameter...` — Parameters that can be specified multiple times (including zero)
* `|` — Mutually exclusive options

**Command Flexibility:**

* Parameters can be specified in any order
* Commands that don't accept parameters will ignore extraneous input
* When copying multi-line commands from PDF, verify that line breaks don't introduce unexpected spaces

</div>

----

## Core Functionality

### Viewing Help : `help`

Displays instructions for accessing the comprehensive help documentation.

**Format:** `help`

**Behavior:** Opens help window with full command reference and usage examples.

----

### Adding a Recruit: `add`

Registers a new recruit in the system with complete contact and descriptive information.

**Format:** `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DESCRIPTION [t/TAG]…`

**Parameters:**
* `n/NAME` — Full name of recruit <span class="badge badge-required">Required</span>
* `p/PHONE_NUMBER` — Contact phone number <span class="badge badge-required">Required</span>
* `e/EMAIL` — Email address <span class="badge badge-required">Required</span>
* `a/ADDRESS` — Physical address <span class="badge badge-required">Required</span>
* `d/DESCRIPTION` — Additional notes or description <span class="badge badge-required">Required</span>
* `t/TAG` — Organizational tags <span class="badge badge-optional">Optional</span>

> **💡 PRO TIP**  
> Tags provide flexible categorization. Recruits can have multiple tags or none at all. Use tags for status tracking, skill categorization, or custom organizational schemes.

**Examples:**

```
add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01
```

```
add n/Jenny Mantle p/66612873 e/jenneram@example.com a/Shadow maze, block 3, #01-01 d/Senior developer, 5 years experience t/tech t/senior
```

```
add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/referral
```

----

### Listing All Recruits : `list`

Displays recruits based on their archive status.

**Format:** `list [FLAG]`

**Available Flags:**
* `list` — Display active (unarchived) recruits only (default)
* `list -archive` — Display archived recruits only
* `list -all` — Display all recruits regardless of status

**Use Cases:**
* Regular workflow: Use default `list` for active recruit management
* Periodic review: Use `list -archive` to audit archived records
* Comprehensive overview: Use `list -all` for system-wide visibility

**Examples:**

```
list                    # Active recruits only
list -archive          # Archived recruits only
list -all              # Complete database
```

----

### Viewing a Recruit : `view`

Displays comprehensive details for a specific recruit.

**Format:** `view INDEX` or `view UUID`

**Parameters:**
* `INDEX` — Position in current list (must be positive integer: 1, 2, 3, ...)
* `UUID` — Unique identifier for the recruit

**Behavior:**
* Displays full recruit profile including all fields and tags
* Index references the currently displayed list (filtered by search/archive status)

**Examples:**

```
list
view 2                 # View second recruit in active list
```

```
find Betsy
view 1                 # View first result from search
```

----

### Editing a Recruit : `edit`

Modifies existing recruit information.

**Format:** `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DESCRIPTION] [t/TAG]…`

**Parameters:**
* `INDEX` or `UUID` — Target recruit identifier <span class="badge badge-required">Required</span>
* All other parameters <span class="badge badge-optional">Optional</span> (minimum one required)

**Important Behaviors:**
* Existing values are replaced with new input
* Tag editing is **non-cumulative** — all existing tags are replaced
* Use `t/` without arguments to remove all tags
* Only specified fields are modified; others remain unchanged

**Examples:**

```
edit 1 p/91234567 e/johndoe@example.com
```
*Updates phone and email for recruit #1*

```
edit 2 n/Betsy Crower t/
```
*Renames recruit #2 and removes all tags*

```
edit 3 t/senior t/tech t/active
```
*Replaces all existing tags with three new tags*

----

### Locating Recruits by Name: `find`

Searches recruit database using keyword matching on names.

**Format:** `find KEYWORD [MORE_KEYWORDS]`

**Search Characteristics:**
* **Case-insensitive:** `hans` matches `Hans`, `HANS`, `HaNs`
* **Order-independent:** `Hans Bo` matches `Bo Hans`
* **Full-word matching:** `Han` will not match `Hans`
* **OR logic:** Returns recruits matching any keyword
* **Name-only:** Searches only the name field

**Examples:**

```
find John              # Returns: "john", "John Doe", "Johnny"
find alex david        # Returns: "Alex Yeoh", "David Li", "Alexandra"
```

----

### Sorting Recruits : `sort`

Organizes recruit list by specified fields in ascending or descending order.

**Format:** `sort [n/ ORDER] [p/ ORDER] [e/ ORDER] [a/ ORDER]`

**Parameters:**
* `ORDER` — Either `asc` (ascending) or `desc` (descending)
* Multiple fields can be specified (applied left-to-right by priority)
* Text fields use case-insensitive sorting

**Shorthand Formats:**

| Command | Behavior |
|---------|----------|
| `sort` | Sort by name, ascending (A→Z) |
| `sort asc` | Sort by name, ascending (A→Z) |
| `sort desc` | Sort by name, descending (Z→A) |

**Multi-Field Sorting:**

```
sort n/ asc                    # Name ascending
sort p/ desc                   # Phone descending
sort n/ asc p/ desc            # Name ascending, then phone descending
sort e/ asc a/ desc            # Email ascending, then address descending
```

> **💡 PRO TIP**  
> Multi-field sorting enables sophisticated organizational schemes. The leftmost field has highest priority, with subsequent fields acting as tie-breakers.

----

### Archiving a Recruit : `archive`

Removes recruits from active view while preserving all data for future reference.

**Format:** `archive INDEX`

**Parameters:**
* `INDEX` — Position in current list <span class="badge badge-required">Required</span>

**Archive Functionality:**
* Archived recruits are hidden from default `list` view
* All data remains intact and accessible
* Access via `list -archive` or `list -all`
* Ideal for inactive candidates or completed recruitment processes

**Use Cases:**
* Completed hires
* Inactive candidates
* Seasonal or temporary separations
* Historical record keeping

**Examples:**

```
list
archive 2              # Archive second recruit in list
```

```
find John
archive 1              # Archive first search result
```

> **💡 PRO TIP**  
> Archiving maintains a clean active workspace while preserving complete historical records. Use archiving instead of deletion for data retention and compliance.

----

### Unarchiving a Recruit : `unarchive`

Restores archived recruits to active status.

**Format:** `unarchive INDEX`

**Parameters:**
* `INDEX` — Position in archived list <span class="badge badge-required">Required</span>

**Prerequisites:**
* Must first view archived recruits using `list -archive` or `list -all`
* Index references currently displayed list

**Examples:**

```
list -archive
unarchive 1            # Restore first archived recruit
```

```
list -all
unarchive 3            # Restore recruit #3 if archived
```

> **💡 PRO TIP**  
> To unarchive a specific recruit, first use `list -archive` to locate them, then execute `unarchive INDEX`.

----

### Deleting a Recruit : `delete`

Permanently removes a recruit from the database.

**Format:** `delete INDEX` or `delete UUID`

**Parameters:**
* `INDEX` — Position in current list <span class="badge badge-required">Required</span>
* `UUID` — Unique identifier <span class="badge badge-optional">Alternative</span>

**Examples:**

```
list
delete 2               # Delete second recruit
```

```
find Betsy
delete 1               # Delete first search result
```

<div class="warning">

**⚠️ WARNING**  
Deletion is permanent and irreversible. Consider using `archive` for temporary removal while maintaining data integrity.

</div>

----

### Clearing All Entries : `clear`

Removes all recruits from the database.

**Format:** `clear`

<div class="warning">

**⚠️ CRITICAL WARNING**  
This operation is irreversible and will permanently delete all recruit data. Ensure you have created backups via `export` before executing this command.

</div>

----

### Exporting Data : `export`

Generates CSV export of all recruits for backup, sharing, or external analysis.

**Format:** `export [FILEPATH]`

**Parameters:**
* `FILEPATH` <span class="badge badge-optional">Optional</span> — Custom export path (uses default from `preferences.json` if omitted)

**CSV Format:**
* Standard comma-separated values
* Compatible with Excel, Google Sheets, and database imports
* Includes all recruit fields and tags

**Examples:**

```
export                          # Use default path
export ./data/recruits.csv      # Custom relative path
export /Users/admin/backup.csv  # Custom absolute path
```

> **💡 PRO TIP**  
> Regular exports provide data redundancy and facilitate collaboration. CSV format ensures universal compatibility across platforms and tools.

----

### Exiting the Program : `exit`

Terminates the application gracefully.

**Format:** `exit`

**Behavior:** Ensures all pending data operations are completed before closing.

----

## Data Management

### Automatic Data Persistence

TalentNexus automatically saves all data modifications to disk immediately after execution. Manual saving is not required.

**Storage Location:** `[JAR file location]/data/addressbook.json`

**Characteristics:**
* JSON format for human readability
* Automatic backup on each modification
* Transactional integrity ensures data consistency

----

### Manual Data File Editing

Advanced users can directly modify the JSON data file.

**File Location:** `[JAR file location]/data/addressbook.json`

<div class="warning">

**⚠️ CAUTION: ADVANCED USERS ONLY**

* **Invalid JSON formatting will result in data loss** — TalentNexus will discard corrupted data and initialize with an empty database
* **Always create backups** before manual editing
* **Validate JSON syntax** using online validators before saving
* **Verify data ranges** — out-of-range values may cause unexpected behavior
* **Test modifications** on a copy of the data file first

**Recommended Workflow:**
1. Export current data using `export` command
2. Create backup copy of `addressbook.json`
3. Make modifications
4. Validate JSON syntax
5. Test with copy of application
6. Deploy to production if successful

</div>

----

## Frequently Asked Questions

**Q: How do I transfer data to another computer?**  
**A:** Install TalentNexus on the target system and replace the auto-generated `addressbook.json` with your existing data file. Alternatively, use the `export` command to create a CSV, then import via the new installation.

**Q: Where is application data stored?**  
**A:** Primary data: `[JAR file location]/data/addressbook.json`. User preferences: `[JAR file location]/preferences.json`. Both files are created automatically on first launch.

**Q: Can I edit the data file directly?**  
**A:** Yes, but only recommended for advanced users. Always create backups first. Invalid JSON formatting will result in data loss.

**Q: What's the difference between archiving and deleting?**  
**A:** Archiving hides recruits from active view while preserving all data. Deletion permanently removes recruits and cannot be undone. Use archiving for data retention.

**Q: How do I backup my data?**  
**A:** Use the `export` command to create CSV backups, or manually copy the `addressbook.json` file to a secure location.

**Q: Can I use TalentNexus across multiple computers?**  
**A:** Yes. Use `export` to create CSV backups and transfer via cloud storage, then import on other systems. For synchronized multi-user access, additional configuration is required.

----

## Known Issues

**1. Multi-Monitor Configuration**

**Issue:** When moving TalentNexus to a secondary monitor and subsequently disconnecting that monitor, the application may launch off-screen on next startup.

**Resolution:** Delete `preferences.json` from the application directory before relaunching. This resets window position to default.

**2. Help Window Minimization**

**Issue:** Minimizing the Help Window and invoking help again (via `help` command, menu, or F1 shortcut) does not restore or create a new window.

**Resolution:** Manually restore the minimized Help Window from your operating system's taskbar or window manager.

----

## Command Reference

| Command | Format | Example |
|---------|--------|---------|
| **Add** | `add n/NAME p/PHONE e/EMAIL a/ADDRESS d/DESCRIPTION [t/TAG]…` | `add n/James Ho p/22224444 e/jamesho@example.com a/123 Clementi Rd d/Senior engineer t/tech` |
| **Archive** | `archive INDEX` | `archive 2` |
| **Clear** | `clear` | `clear` |
| **Delete** | `delete INDEX\|UUID` | `delete 3` |
| **Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DESCRIPTION] [t/TAG]…` | `edit 2 n/James Lee e/jameslee@example.com` |
| **Exit** | `exit` | `exit` |
| **Export** | `export [FILEPATH]` | `export ./data/recruits.csv` |
| **Find** | `find KEYWORD [MORE_KEYWORDS]` | `find James Jake` |
| **Help** | `help` | `help` |
| **List** | `list [-archive\|-all]` | `list -archive` |
| **Sort** | `sort [n/\|p/\|e/\|a/ ORDER]…` | `sort n/ asc p/ desc` |
| **Unarchive** | `unarchive INDEX` | `unarchive 1` |
| **View** | `view INDEX\|UUID` | `view 2` |

----

## Appendix: Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `F1` | Open help window |
| `Ctrl + Enter` | Execute command |
| `Ctrl + L` | Focus command input |
| `↑` / `↓` | Navigate command history |
| `Esc` | Clear command input |

---

**Version:** 1.4  
**Last Updated:** 2024  
**Documentation:** [https://talentnexus.docs](https://github.com/se-edu/addressbook-level3)  
**Support:** [support@talentnexus.com](mailto:support@talentnexus.com)