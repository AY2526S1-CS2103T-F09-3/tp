package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        updateTextAreaHeight(feedbackToUser);
    }

    private void updateTextAreaHeight(String text) {
        if (text == null || text.isEmpty()) {
            resultDisplay.setPrefHeight(30);
            return;
        }

        Text temp = new Text(text);
        temp.setFont(resultDisplay.getFont());

        double wrappingWidth = resultDisplay.getWidth() - 20;
        temp.setWrappingWidth(wrappingWidth > 0 ? wrappingWidth : 0);

        double textHeight = temp.getLayoutBounds().getHeight();

        double newHeight = Math.max(textHeight + 30, 30);

        resultDisplay.setPrefHeight(newHeight);
    }

}
