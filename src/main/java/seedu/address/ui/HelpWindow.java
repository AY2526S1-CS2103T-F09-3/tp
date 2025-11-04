package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s1-cs2103t-f09-3.github.io/tp/UserGuide.html";
    public static final String COMMAND_HELP_MESSAGE = """
            Command Summary:
            \s
            1. Create recruit
            add n/NAME... [p/PHONE_NUMBER]... [e/EMAIL]... [a/ADDRESS]... [t/TAG]...
            \s
            2. View recruit
            view INDEX|UUID
            \s
            3. Edit recruit's details
            edit INDEX|UUID [OPERATION] [n/NAME]... [p/PHONE_NUMBER]... [e/EMAIL]... [a/ADDRESS]... [d/DESCRIPTION]... \
            [t/TAG]...
            \s
            4. Delete recruit
            delete INDEX|UUID
            \s
            5. Search recruits
            find NAME [id/KEYWORDS] [n/KEYWORDS] [p/KEYWORDS] [e/KEYWORDS] [a/KEYWORDS] [t/KEYWORDS]
            \s
            6. List recruits
            list [-archived] [-all]
            \s
            7. Sort recruits
            sort [-n ORDER] [-p ORDER] [-e ORDER] [-a ORDER]
            \s
            8. Archive recruit
            archive INDEX|UUID
            \s
            9. Unarchive recruit
            unarchive INDEX|UUID
            \s
            10. Undo command
            undo
            \s
            11. Redo command
            redo
            \s
            12. Export recruits
            export [FILEPATH]
            \s
            13. Clear all recruits
            clear
            \s
            14. Dismiss displayed results after executing a command
            dismiss
            \s
            15. Exit application
            exit
            \s
            16. Open help menu
            help
           """;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label userGuideLabel;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        // Header of help window
        userGuideLabel.setText("Refer to the user guide: " + USERGUIDE_URL);
        // Body of help window
        helpMessage.setText(COMMAND_HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Opens the user guide in the user's browser.
     */
    @FXML
    private void openUserGuide() {
        new Thread(() -> {
            try {
                Desktop.getDesktop().browse(URI.create(USERGUIDE_URL));
            } catch (IOException e) {
                logger.warning("Failed to open default browser.");
            }
        }).start();
    }
}
