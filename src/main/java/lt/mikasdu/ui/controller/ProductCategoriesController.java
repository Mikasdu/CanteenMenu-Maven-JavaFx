package lt.mikasdu.ui.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.internal.runtime.ListAdapter;
import lt.mikasdu.AppNavigator;
import lt.mikasdu.ProductCategories;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductCategoriesController implements Initializable {

    @FXML private Button testButtonThis;
    @FXML private CheckBox showDeleted;
    @FXML private Button editCategoryButton;
    @FXML private TableView<ProductCategories> tbData;
    @FXML private TableColumn<ProductCategories, Integer> categoryId;
    @FXML private TableColumn<ProductCategories, String> categoryName;
    @FXML private Button removeCategoryButton;

    private ObservableList<ProductCategories> productCategoriesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableData(true);
        defaultSettings();
        showDeleted.setOnAction(event ->
                setTableData(!showDeleted.isSelected())
        );
        tbData.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            removeCategoryButton.setDisable(true);
            if (newSelection != null) {
                if (tbData.getSelectionModel().getSelectedItem().getActive()) {
                    removeCategoryButton.setDisable(false);
                }
            }
        });
    }

    private void setTableData(boolean status) {
        productCategoriesList.clear();
        tbData.getItems().clear();
        productCategoriesList = SqlConnection.returnProductCategoriesList(status);
        categoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbData.setItems(productCategoriesList);
    }

    private void defaultSettings() {
        editCategoryButton.setDisable(true);
        removeCategoryButton.setDisable(true);
        showDeleted.setSelected(false);
    }

    public void deleteButtonClicked() {
        if (!tbData.getSelectionModel().isEmpty()) {
            ProductCategories productCategories = tbData.getSelectionModel().getSelectedItem();
            if (!SqlConnection.productsWithCategoryId(productCategories)) {
                boolean confirmedDelete = AlertBox.alertWithConformation(AlertMessage.CONFIRM_DELETE,
                        "Kurio Id: " + productCategories.getId()
                                + " Pavadinimas: " + productCategories.getName());
                if (confirmedDelete) {
                    tbData.getItems().remove(productCategories);
                    productCategories.removeFromDatabase();
                }
            } else AlertBox.alertSimple(AlertMessage.ERROR_CANTDELETE);
        } else AlertBox.alertSimple(AlertMessage.ERROR_PLEASECHOOSE);
    }

    public void editButtonClicked() {
        if (!tbData.getSelectionModel().isEmpty()) {
            ProductCategoryWindow(tbData.getSelectionModel().getSelectedItem());
        } else AlertBox.alertSimple(AlertMessage.ERROR_PLEASECHOOSE);
    }

    public void buttonAddProductCategory() {
        ProductCategoryWindow(new ProductCategories());
    }

    private void ProductCategoryWindow(ProductCategories productCategory) {
        AppNavigator.addProductCategory(productCategory);
        setTableData(true);
        defaultSettings();
    }
}