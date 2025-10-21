//package seedu.address.logic.commands;
//
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//
//public class ArchiveCommand extends Command {
//    public static final String COMMAND_WORD = "archive";
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the details of the recruit identified "
//            + "by the index number used in the displayed recruit list. "
//            + "Existing values will be overwritten by the input values.\n"
//            + "Parameters: INDEX (must be a positive integer) "
//            + "[" + PREFIX_NAME + "NAME] "
//            + "[" + PREFIX_PHONE + "PHONE] "
//            + "[" + PREFIX_EMAIL + "EMAIL] "
//            + "[" + PREFIX_ADDRESS + "ADDRESS] "
//            + "[" + PREFIX_TAG + "TAG]...\n"
//            + "Example: " + COMMAND_WORD + " 1 "
//            + PREFIX_PHONE + "91234567 "
//            + PREFIX_EMAIL + "johndoe@example.com";
//}
