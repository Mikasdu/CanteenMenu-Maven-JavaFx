package lt.mikasdu.ui.alerts;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import lt.mikasdu.Formatter;
import lt.mikasdu.Validator;

import java.util.Optional;


public class AlertBox {

    public static String alertWithInput(String input) {
        Dialog dialog = new Dialog();
        dialog.setTitle("Pakeiskite kiekį");
        dialog.setHeaderText(null);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 5, 10, 10));
        TextField quantity = new TextField();
        quantity.setText(input);
        grid.add(new Label("Kiekis:"), 0, 0);
        grid.add(quantity, 1, 0);
        dialog.getDialogPane().setContent(grid);
        quantity.setTextFormatter(Formatter.formatDecimalQuantityNumbers());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional result = dialog.showAndWait();
        boolean nameValid = Validator.stringValid(quantity.getText(), 1, 10);
        if (result.isPresent() && result.get() == ButtonType.OK && nameValid) {
            input = quantity.getText();
        } else if (!nameValid) AlertBox.alertSimple(AlertMessage.ERROR_QUANTITY);
        return input;
    }

    public static boolean alertWithConformation(AlertMessage alertMessage, String customMessage) {
        alertMessage.setCustomMessage(customMessage);
        return alertWithConformation(alertMessage);
    }

    public static boolean alertWithConformation(AlertMessage alertMessage) {
        Alert alert = alert(alertMessage);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent() && result.get() == ButtonType.OK);
    }

    public static void alertSimple(AlertMessage alertMessage) {
        Alert alert = alert(alertMessage);
        alert.showAndWait();
    }

    public static void alertSimple(AlertMessage alertMessage, String customMessage) {
        alertMessage.setCustomMessage(customMessage);
        Alert alert = alert(alertMessage);
        alert.showAndWait();
    }

    private static Alert alert(AlertMessage alertMessage) {
        Alert alert = new Alert(alertMessage.getAlertType());
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.setTitle(alertMessage.getTitle());
        alert.setContentText(alertMessage.getMessage());
        return alert;
    }

    public static void exceptionAlert(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Nenumatyta klaida");
        alert.setContentText(exception.getMessage());

        exception.printStackTrace();
        String exceptionText = exception.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

}