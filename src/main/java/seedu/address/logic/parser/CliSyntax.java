package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix SEARCH_PREFIX_ID = new Prefix("id/");
    public static final Prefix SEARCH_PREFIX_NAME = new Prefix("n/");
    public static final Prefix SEARCH_PREFIX_PHONE = new Prefix("p/");
    public static final Prefix SEARCH_PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix SEARCH_PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix SEARCH_PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix SEARCH_PREFIX_TAG = new Prefix("t/");

    public static final Prefix SORT_PREFIX_NAME = new Prefix("-n");
    public static final Prefix SORT_PREFIX_PHONE = new Prefix("-p");
    public static final Prefix SORT_PREFIX_EMAIL = new Prefix("-e");
    public static final Prefix SORT_PREFIX_ADDRESS = new Prefix("-a");

    public static final Prefix EDIT_PREFIX_APPEND = new Prefix("-ap");
    public static final Prefix EDIT_PREFIX_OVERWRITE = new Prefix("-o");
    public static final Prefix EDIT_PREFIX_REMOVE = new Prefix("-rm");
    public static final Prefix EDIT_PREFIX_PRIMARY = new Prefix("-primary");

    public static final Prefix PREFIX_LIST_ALL = new Prefix("-all");
    public static final Prefix PREFIX_LIST_ARCHIVE = new Prefix("-archived");

    public static final Prefix PREFIX_CLEAR_CONFIRM = new Prefix("-confirm");
}
