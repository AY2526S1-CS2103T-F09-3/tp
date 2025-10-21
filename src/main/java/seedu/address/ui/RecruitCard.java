package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.recruit.Recruit;

/**
 * An UI component that displays information of a {@code Person}.
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
        id.setText(displayedIndex + ". ");
        name.setText(recruit.getName().fullName);
        phone.setText(recruit.getPhone().value);
        address.setText(recruit.getAddress().value);
        email.setText(recruit.getEmail().value);
        recruit.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (recruit.getArchiveStatus()) {
            archivedLabel.setVisible(true);
            archivedLabel.setManaged(true);
        } else {
            archivedLabel.setVisible(false);
            archivedLabel.setManaged(false);
        }
    }
}
