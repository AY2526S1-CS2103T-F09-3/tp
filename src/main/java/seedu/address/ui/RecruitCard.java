package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.recruit.Recruit;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class RecruitCard extends UiPart<Region> {

    private static final String FXML = "RecruitListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Recruit recruit;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label index;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label archivedLabel;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public RecruitCard(Recruit recruit, int displayedIndex) {
        super(FXML);
        this.recruit = recruit;
        index.setText(displayedIndex + ". ");
        id.setText(recruit.getID().toString());
        name.setText(recruit.getName().value);
        recruit.getPhone().ifPresent(phone -> this.phone.setText(phone.value));
        recruit.getAddress().ifPresent(address -> this.address.setText(address.value));
        recruit.getEmail().ifPresent(email -> this.email.setText(email.value));
        recruit.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        archivedLabel.setVisible(recruit.isArchived());
        archivedLabel.setManaged(recruit.isArchived());
    }

    /**
     * Handles the action of copying the recruit's ID to the system clipboard.
     * <p>
     * This method is triggered when the "Copy" button associated with the recruit's ID is clicked.
     * It retrieves the text from the {@link #id} label and places it into the system clipboard.
     *
     * @param event the {@link javafx.event.ActionEvent} representing the button click event
     */
    @FXML
    public void handleCopyId(javafx.event.ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(id.getText());
        clipboard.setContent(content);
    }
}
