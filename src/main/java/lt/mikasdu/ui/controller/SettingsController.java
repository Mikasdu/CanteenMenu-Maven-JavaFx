package lt.mikasdu.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lt.mikasdu.Formatter;
import lt.mikasdu.Validator;
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML private CheckBox checkBoxFullScreen;
    @FXML private TextField textFieldAppHeight;
    @FXML private TextField textFieldAppWidth;
    @FXML private TextField defaultFolderTextField;
    @FXML private TextField textFieldAppUserName;
    @FXML private Button closeButton;
    private Settings settings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.settings = new Settings();
        textFieldAppUserName.setText(settings.getUserName());
        defaultFolderTextField.setText(settings.getFilesPath());
        textFieldAppHeight.setText(settings.getAppHeight());
        textFieldAppHeight.setTextFormatter(Formatter.formatIntegerNumbers());
        textFieldAppWidth.setText(settings.getAppWidth());
        textFieldAppWidth.setTextFormatter(Formatter.formatIntegerNumbers());
        checkBoxFullScreen.setSelected(settings.isFullScreen());
        checkBoxFullScreenAction();
    }

    public void chooseDefaultFileFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(settings.getFilesPath()));
        File selectedDirectory = directoryChooser.showDialog(defaultFolderTextField.getScene().getWindow());
        if (selectedDirectory != null) {
            defaultFolderTextField.setText(selectedDirectory.getAbsolutePath());
            System.out.println("New Path: " + selectedDirectory.getAbsolutePath());
        }
    }

    public void buttonSave() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) screenSize.getWidth();
        int maxHeight = (int) screenSize.getHeight();
        int newWidth = Integer.parseInt(textFieldAppWidth.getText());
        int newHeight = Integer.parseInt(textFieldAppHeight.getText());
        String newUserName = textFieldAppUserName.getText();
        String newFilesPath = defaultFolderTextField.getText();

        if (Validator.stringValid(newUserName, 3, 50)) {
            if (Validator.directoryExists(newFilesPath)) {
                if (newHeight < maxHeight && newWidth < maxWidth) {
                    settings.setUserName(newUserName);
                    settings.setFilesPath(newFilesPath);
                    settings.setAppWidth(String.valueOf(newWidth));
                    settings.setAppHeight(String.valueOf(newHeight));
                    settings.setFullScreen(checkBoxFullScreen.isSelected());
                    settings.saveConfigFile();
                    AlertBox.alertSimple(AlertMessage.INFO, "Pakeitimai bus matomi kai perkrausite programą");
                    closeWindow();
                } else {
                    String err = "Bandode nustatyti per didelį langą, maximalus leistinas: " + maxWidth + "x" + maxHeight;
                    AlertBox.alertSimple(AlertMessage.ERROR_CUSTOM, err);
                }
            } else
                AlertBox.alertSimple(AlertMessage.ERROR_CUSTOM, "Katalogas neegzistuoja pasirinkite kitą");
        } else
            AlertBox.alertSimple(AlertMessage.ERROR_NAME);
    }

    private void disableWidthHeightInputs(boolean val) {
        textFieldAppWidth.setDisable(val);
        textFieldAppHeight.setDisable(val);
    }

    public void checkBoxFullScreenAction() {
        disableWidthHeightInputs(checkBoxFullScreen.isSelected());
    }

    public void buttonCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
//todo prideti vartotojo ivairia informacija kontaktai
}
