package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

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
        // Recalculate Text Area
        double windowHeight = resultDisplay.getScene().getWindow().getHeight();
        int totalLines = 0;
        double wrappingWidth = resultDisplay.getWidth() - 20;
        String[] lines = text.split("\n");

        for (String line : lines) {
            double avgCharWidth = resultDisplay.getFont().getSize() * 0.45;
            double lineWidth = line.length() * avgCharWidth;

            int wraps = (int) Math.ceil(lineWidth / wrappingWidth);
            totalLines += Math.max(1, wraps);
        }

        double estimatedHeight = totalLines * 25 + 20;
        double newHeight = Math.max(estimatedHeight, 20); // extra padding
        resultDisplay.setPrefHeight(Math.min(newHeight, 0.5 * windowHeight));
    }

}
