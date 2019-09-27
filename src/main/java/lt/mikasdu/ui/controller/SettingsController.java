package lt.mikasdu.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lt.mikasdu.settings.Settings;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML private TextField defaultFolderTextField;
    @FXML private TextField textFieldAppUserName;
    @FXML private Button cancelButton;
    private Settings settings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.settings = new Settings();
        textFieldAppUserName.setText(settings.getUserName());
        defaultFolderTextField.setText(settings.getFilesPath());


        //directoryChooser.setInitialDirectory(new File("src"));

        //todo http://tutorials.jenkov.com/javafx/directorychooser.html


        System.out.println(settings.getUserName());
        System.out.println(settings.getFilesPath());
        System.out.println(settings.isFullScreen());
        System.out.println(settings.getAppHeight());
        System.out.println(settings.getAppWidth());
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

    }

    public void buttonCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
