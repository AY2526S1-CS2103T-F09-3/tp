package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.recruit.Recruit;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Recruit> filteredRecruits;
    private Predicate<Recruit> currPredicate = PREDICATE_SHOW_UNARCHVIED_RECRUITS;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRecruits = new FilteredList<>(this.addressBook.getRecruitList())
                .filtered(PREDICATE_SHOW_UNARCHVIED_RECRUITS);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasRecruit(Recruit recruit) {
        requireNonNull(recruit);
        return addressBook.hasRecruit(recruit);
    }

    @Override
    public void deleteRecruit(Recruit target) {
        addressBook.removeRecruit(target);
    }

    @Override
    public void addRecruit(Recruit recruit) {
        addressBook.addRecruit(recruit);
        updateFilteredRecruitList(PREDICATE_SHOW_UNARCHVIED_RECRUITS);
    }

    @Override
    public void setRecruit(Recruit target, Recruit editedRecruit) {
        requireAllNonNull(target, editedRecruit);

        addressBook.setRecruit(target, editedRecruit);
    }

    @Override
    public void sortRecruits() {
        addressBook.sortRecruits();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Recruit> getFilteredRecruitList() {
        return filteredRecruits;
    }

    @Override
    public void updateFilteredRecruitList(Predicate<Recruit> predicate) {
        requireNonNull(predicate);
        filteredRecruits.setPredicate(predicate);
        this.currPredicate = predicate;
    }

    @Override
    public void refreshFilteredRecruitList() {
        updateFilteredRecruitList(this.currPredicate);
    }

    public Optional<Recruit> getFilteredRecruitByID(UUID id) {
        return this.filteredRecruits.stream().findFirst().filter(x -> x.getID().equals(id));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredRecruits.equals(otherModelManager.filteredRecruits);
    }

    @Override
    public void commitAddressBook(String descriptor) {
        addressBook.commit(descriptor);
    }

    @Override
    public String undoAddressBook() {
        String undoneCommand = addressBook.undo();
        updateFilteredRecruitList(PREDICATE_SHOW_ALL_RECRUITS);
        return undoneCommand;
    }

    @Override
    public boolean canUndoAddressBook() {
        return addressBook.canUndoAddressBook();
    }
}
