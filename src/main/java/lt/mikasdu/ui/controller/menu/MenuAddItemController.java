package lt.mikasdu.ui.controller.menu;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lt.mikasdu.Validator;
import lt.mikasdu.WeekMenu;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;


public class MenuAddItemController {

    @FXML private TextField menuName;
    @FXML private Label editLabel;
    @FXML private Button cancelButton;
    private boolean isNew = true;
    private WeekMenu weekMenu;


    public void initData(WeekMenu weekMenu) {
        this.weekMenu = weekMenu;
        if (weekMenu.getId() == 0) {
            editLabel.setText("Naujas meniu įrašas");
        } else {
            isNew = false;
            editLabel.setText("Redaguojamas įrašas: " + weekMenu.getId());
            menuName.setText(weekMenu.getName());
        }
    }

    public void buttonSave() {
        if (Validator.stringValid(menuName.getText(), 5, 50)) {
            weekMenu.setName(menuName.getText());
            weekMenu.setStatus(true);
            if (isNew) weekMenu.saveToDatabase();
            else weekMenu.updateDatabase();
            closeCurentWindow();
        } else AlertBox.alertSimple(AlertMessage.ERROR_NAME);
    }

    public void buttonCancel() {
        closeCurentWindow();
    }

    private void closeCurentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
