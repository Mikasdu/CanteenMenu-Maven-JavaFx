package lt.mikasdu.ui.controller.categories;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lt.mikasdu.ProductCategories;
import lt.mikasdu.Validator;
import lt.mikasdu.WeekMenuRecipes;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

public class ProductCategoriesItemController {
    @FXML private CheckBox checkBoxProductCategoryStatus;
    @FXML private Label headerLabelCategory;
    @FXML private TextField productCategoryName;
    @FXML private Button cancelButton;
    private boolean isNew = true;

    public void buttonSave() {
        String productCategoryName = this.productCategoryName.getText();
        if (Validator.stringValid(productCategoryName, 5, 50)) {
            if (isNew) {
                System.out.println("saving new");
            } else {
                System.out.println("Updating");
            }
        } else {
            AlertBox.alertSimple(AlertMessage.ERROR_NAME);
        }
    }
    public void initData(ProductCategories productCategory) {
        isNew = productCategory.getId() == 0;
        if (isNew) {
            headerLabelCategory.setText("Įveskite naujos kategorijos pavadinimą");
        } else {
            headerLabelCategory.setText("Redaguojama kategorija Id: " + productCategory.getId());
            productCategoryName.setText(productCategory.getName());
            checkBoxProductCategoryStatus.setSelected(!productCategory.getStatus());
        }
    }
    public void buttonCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
