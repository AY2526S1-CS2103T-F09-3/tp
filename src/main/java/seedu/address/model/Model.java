package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.recruit.Recruit;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Recruit> PREDICATE_SHOW_ALL_RECRUITS = unused -> true;
    Predicate<Recruit> PREDICATE_SHOW_UNARCHVIED_RECRUITS = recruit -> !recruit.getArchiveStatus();
    Predicate<Recruit> PREDICATE_SHOW_ARCHIVED_RECRUITS = Recruit::getArchiveStatus;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasRecruit(Recruit recruit);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteRecruit(Recruit target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addRecruit(Recruit recruit);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setRecruit(Recruit target, Recruit editedRecruit);

    void sortRecruits();

    /** Returns an unmodifiable view of the filtered recruit list */
    ObservableList<Recruit> getFilteredRecruitList();

    /**
     * Returns the recruit with the given ID.
     * @throws IllegalArgumentException if there is no recruit with the given ID.
     */
    Optional<Recruit> getFilteredRecruitByID(UUID id);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecruitList(Predicate<Recruit> predicate);

    /**
     * Refreshes the Recruit List
     */
    void refreshFilteredRecruitList();

    /**
     * Saves the current address book state in history.
     */
    void commitAddressBook(String command);

    /**
     * Restores the previous address book state from its history.
     */
    String undoAddressBook();

    /**
     * Checks if undo operation is possible.
     */
    boolean canUndoAddressBook();
}
