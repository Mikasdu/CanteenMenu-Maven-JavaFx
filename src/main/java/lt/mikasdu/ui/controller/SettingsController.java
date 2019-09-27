package lt.mikasdu.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lt.mikasdu.Validator;
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML private CheckBox checkBoxFullScreen;
    @FXML private TextField textFieldAppHeight;
    @FXML private TextField textFieldAppWidth;
    @FXML private TextField defaultFolderTextField;
    @FXML private TextField textFieldAppUserName;
    @FXML private Button cancelButton;
    private Settings settings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.settings = new Settings();
        textFieldAppUserName.setText(settings.getUserName());
        defaultFolderTextField.setText(settings.getFilesPath());
        textFieldAppHeight.setText(settings.getAppHeight());
        textFieldAppWidth.setText(settings.getAppWidth());
        checkBoxFullScreen.setSelected(settings.isFullScreen());
    }

    public void chooseDefaultFileFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(settings.getFilesPath()));
        File selectedDirectory = directoryChooser.showDialog(defaultFolderTextField.getScene().getWindow());
        if(selectedDirectory != null){
            defaultFolderTextField.setText(selectedDirectory.getAbsolutePath());
            System.out.println("New Path: " + selectedDirectory.getAbsolutePath());
        }
    }


    public void buttonSave() {
        if (!Validator.directoryExists(defaultFolderTextField.getText())) {
            AlertBox.alertSimple(AlertMessage.ERROR_CUSTOM, "Katalogas neegzistuoja pasirinkite kitą");
        }
    }

    public void buttonCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
