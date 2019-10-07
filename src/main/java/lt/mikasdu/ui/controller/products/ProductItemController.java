package lt.mikasdu.ui.controller.products;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lt.mikasdu.*;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;


public class ProductItemController {

    @FXML private VBox productInputsBox;
    @FXML private CheckBox checkBoxProductStatus;
    @FXML private HBox isDeletedBox;
    @FXML private TextField productNameInput;
    @FXML private ComboBox<MeasureUnit> productMeasureChoice;
    @FXML private ComboBox<ProductCategories> productCategoryBox;
    @FXML private TextArea productDescriptionInput;
    @FXML private Button cancelButton;
    @FXML private Label headerLabelCategory;

    private Products product;
    private boolean isNew;

    public void buttonSave() {
        String productName = productNameInput.getText();
        boolean nameValid = Validator.stringValid(productName, 5, 50);
        if (nameValid) {
            this.product.setName(productName);
            if (!productMeasureChoice.getSelectionModel().isEmpty()) {
                this.product.setMeasure(productMeasureChoice.getSelectionModel().getSelectedItem().getId());
                if (!productCategoryBox.getSelectionModel().isEmpty()) {
                    this.product.setCategories(productCategoryBox.getSelectionModel().getSelectedItem().getId());
                    this.product.setDescription(productDescriptionInput.getText());
                    if (isNew) {
                        product.saveToDatabase();
                    } else {
                        product.updateDatabase();
                    }
                    closeCurrentWindow();
                } else AlertBox.alertSimple(AlertMessage.ERROR_CATEGORY);
            } else AlertBox.alertSimple(AlertMessage.ERROR_MEASUREUNTI);
        } else AlertBox.alertSimple(AlertMessage.ERROR_NAME);
    }

    public void buttonCancel() {
        closeCurrentWindow();
    }

    public void initData(Products product) {
        this.product = product;
        isNew = this.product.getId() == 0;
        setProductCategories();
        setProductMeasureUnits();
        setDefaultSettings();
    }

    private void setProductMeasureUnits() {
        productMeasureChoice.getItems().setAll(MeasureUnit.measureUnitsList());
    }

    private void setProductCategories() {
        ObservableList<ProductCategories> categoriesList = SqlConnection.getProductCategoriesList(true);
        productCategoryBox.setItems(categoriesList);
    }

    private void setDefaultSettings() {
        checkBoxProductStatus.setSelected(!product.isActive());
        isDeletedBox.setDisable(product.isActive());
        productInputsBox.setDisable(!product.isActive());
        if (isNew) {
            headerLabelCategory.setText("Įveskite naują produktą");
        } else {
            headerLabelCategory.setText("Redaguojamas produktas Id: " + this.product.getId());
            productNameInput.setText(this.product.getName());
            productMeasureChoice.getSelectionModel().select(this.product.getMeasureObj());
            productCategoryBox.getItems().forEach(productCategory -> {
                if (productCategory.getId() == this.product.getCategoriesId())
                    productCategoryBox.getSelectionModel().select(productCategory);
            });
            productDescriptionInput.setText(this.product.getDescription());
        }
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void removeDeletedTag() {
        productInputsBox.setDisable(checkBoxProductStatus.isSelected());
        product.setActive(!checkBoxProductStatus.isSelected());
        productNameInput.setText(product.getName());
    }
}
