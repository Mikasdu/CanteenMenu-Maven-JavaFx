package lt.mikasdu.ui.controller.categories;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lt.mikasdu.ProductCategories;
import lt.mikasdu.Validator;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

public class ProductCategoriesItemController {

    @FXML private HBox statusFieldsBox;
    @FXML private CheckBox checkBoxProductCategoryStatus;
    @FXML private Label headerLabelCategory;
    @FXML private TextField productCategoryName;
    @FXML private Button cancelButton;

    private boolean isNew = true;
    private ProductCategories productCategory;

    public void buttonSave() {
        productCategory.setName(this.productCategoryName.getText());
        if (Validator.stringValid(productCategory.getName(), 5, 50)) {
            if (isNew) {
                productCategory.saveToDatabase();
            } else {
                productCategory.updateDatabase();
            }
        } else {
            AlertBox.alertSimple(AlertMessage.ERROR_NAME);
        }
        closeCurrentWindow();
    }

    public void initData(ProductCategories productCategory) {
        this.productCategory = productCategory;
        setDefaultSettings();
    }

    private void setDefaultSettings() {
        isNew = productCategory.getId() == 0;
        if (isNew) {
            headerLabelCategory.setText("Įveskite naujos kategorijos pavadinimą");
        } else {
            headerLabelCategory.setText("Redaguojama kategorija Id: " + productCategory.getId());
            productCategoryName.setText(productCategory.getName());
            checkBoxProductCategoryStatus.setSelected(!productCategory.getStatus());
        }
        if (!productCategory.getStatus()) {
            productCategoryName.setDisable(true);
        }
        statusFieldsBox.setDisable(productCategory.getStatus());
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void buttonCancel() {
        closeCurrentWindow();
    }

    public void removeDeletedTag() {
        productCategory.setStatus(!checkBoxProductCategoryStatus.isSelected());
        productCategoryName.setText(productCategory.getName());
        productCategoryName.setDisable(checkBoxProductCategoryStatus.isSelected());
    }
}
