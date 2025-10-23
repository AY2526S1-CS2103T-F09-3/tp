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
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix SEARCH_PREFIX_ID = new Prefix("-id");
    public static final Prefix SEARCH_PREFIX_NAME = new Prefix("-n");
    public static final Prefix SEARCH_PREFIX_PHONE = new Prefix("-p");
    public static final Prefix SEARCH_PREFIX_EMAIL = new Prefix("-e");
    public static final Prefix SEARCH_PREFIX_ADDRESS = new Prefix("-a");
    public static final Prefix SEARCH_PREFIX_TAG = new Prefix("-t");

    public static final Prefix PREFIX_LIST_ALL = new Prefix("-all");
    public static final Prefix PREFIX_LIST_ARCHIVE = new Prefix("-archive");
}
