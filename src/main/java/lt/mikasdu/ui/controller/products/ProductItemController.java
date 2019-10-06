package lt.mikasdu.ui.controller.products;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lt.mikasdu.ProductCategories;
import lt.mikasdu.Products;
import lt.mikasdu.Validator;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;

public class ProductItemController {
    @FXML private TextField productNameInput;
    @FXML private ChoiceBox<String> productMeasureChoice;
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
                this.product.setMeasure(productMeasureChoice.getValue());
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
        setDefaultSettings();
    }

    private void setProductCategories() {
        ObservableList<ProductCategories> categoriesList = SqlConnection.getProductCategoriesList(true);
        productCategoryBox.setItems(categoriesList);
    }

    private void setDefaultSettings() {
        if (isNew) {
            headerLabelCategory.setText("Įveskite naują produktą");
        } else {
            headerLabelCategory.setText("Redaguojamas produktas Id: " + this.product.getId());
            productNameInput.setText(this.product.getName());
            productMeasureChoice.getSelectionModel().select(this.product.getMeasure());
            productCategoryBox.getItems().forEach(productCategory -> {
                if (productCategory.getId() == this.product.getCategoriesId())
                    productCategoryBox.getSelectionModel().select(productCategory);
            });
            productDescriptionInput.setText(this.product.getDescription());

            //checkBoxProductCategoryStatus.setSelected(!productCategory.getActive());
        }
//        if (!productCategory.getActive()) {
//            productCategoryName.setDisable(true);
//        }
//        statusFieldsBox.setDisable(productCategory.getActive());
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
