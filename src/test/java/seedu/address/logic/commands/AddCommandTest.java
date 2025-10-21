package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecruits.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.RecruitBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecruitAdded modelStub = new ModelStubAcceptingRecruitAdded();
        Recruit validRecruit = new RecruitBuilder().build();

        CommandResult commandResult = new AddCommand(validRecruit).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validRecruit)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRecruit), modelStub.recruitsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Recruit validRecruit = new RecruitBuilder().build();
        AddCommand addCommand = new AddCommand(validRecruit);
        ModelStub modelStub = new ModelStubWithRecruit(validRecruit);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_RECRUIT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Recruit alice = new RecruitBuilder().withName("Alice").build();
        Recruit bob = new RecruitBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecruit(Recruit recruit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecruit(Recruit recruit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecruit(Recruit target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecruit(Recruit target, Recruit editedRecruit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortRecruits() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recruit> getFilteredRecruitList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Recruit> getFilteredRecruitByID(UUID id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecruitList(Predicate<Recruit> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void refreshFilteredRecruitList() {
        public void commitAddressBook(String command) {
            // does nothing
        }

        @Override
        public String undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithRecruit extends ModelStub {
        private final Recruit recruit;

        ModelStubWithRecruit(Recruit recruit) {
            requireNonNull(recruit);
            this.recruit = recruit;
        }

        @Override
        public boolean hasRecruit(Recruit recruit) {
            requireNonNull(recruit);
            return this.recruit.isSameRecruit(recruit);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingRecruitAdded extends ModelStub {
        final ArrayList<Recruit> recruitsAdded = new ArrayList<>();

        @Override
        public boolean hasRecruit(Recruit recruit) {
            requireNonNull(recruit);
            return recruitsAdded.stream().anyMatch(recruit::isSameRecruit);
        }

        @Override
        public void addRecruit(Recruit recruit) {
            requireNonNull(recruit);
            recruitsAdded.add(recruit);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
