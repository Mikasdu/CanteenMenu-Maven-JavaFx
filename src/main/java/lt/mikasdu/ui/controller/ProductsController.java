package lt.mikasdu.ui.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lt.mikasdu.*;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;
import lt.mikasdu.ui.sqlConnection.SqlStatement;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {
    @FXML private Button editProductButton;
    @FXML private Button deleteProductButton;
    @FXML private TableView<Products> tbData;
    @FXML private TableColumn<Products, Integer> productId;
    @FXML private TableColumn<Products, String> productName;
    @FXML private TableColumn<Products, String> productMeasure;
    @FXML private TableColumn<Products, String> productDescription;
    @FXML private TableColumn<Products, Integer> productCategory;
    @FXML private CheckBox showDeleted;

    private ObservableList<Products> productsObsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultSettings();
        showDeleted.setOnAction(event ->
                setTableData(!showDeleted.isSelected())
        );
        tbData.getSelectionModel().selectedItemProperty().addListener(e -> {
            deleteProductButton.setDisable(true);
            if (!tbData.getSelectionModel().isEmpty()) {
                if (tbData.getSelectionModel().getSelectedItem().isActive())
                    deleteProductButton.setDisable(false);
            }
        });
    }

    private void defaultSettings() {
        setTableData(true);
        editProductButton.disableProperty().bind(Bindings.isEmpty(tbData.getSelectionModel().getSelectedItems()));
        deleteProductButton.setDisable(true);
        showDeleted.setSelected(false);
    }


    private void setTableData(boolean isActive) {
        productsObsList.clear();
        tbData.getItems().clear();
        productsObsList = SqlConnection.getProductsList(SqlStatement.PRODUCTS, 1, isActive);
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productMeasure.setCellValueFactory(new PropertyValueFactory<>("measure"));
        productCategory.setCellValueFactory(new PropertyValueFactory<>("categories"));
        productDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbData.setItems(productsObsList);
    }

    public void deleteButtonClicked() {
        if (!tbData.getSelectionModel().isEmpty()) {
            Products selectedProduct = tbData.getSelectionModel().getSelectedItem();
            boolean confirmedDelete = AlertBox.alertWithConformation(AlertMessage.CONFIRM_DELETE,
                    "Įrašo Id: " + selectedProduct.getId() +
                            " Įrašo pavadinimas: " + selectedProduct.getName()
            );
            if (confirmedDelete) {
                tbData.getItems().remove(selectedProduct);
                selectedProduct.removeFromDatabase();
            }
        }
    }

    public void addNewProductButton() {
        productWindow(new Products());
    }

    public void editSelectedProductButton() {
        if (!tbData.getSelectionModel().isEmpty()) {
            productWindow(tbData.getSelectionModel().getSelectedItem());
        }
    }

    private void productWindow(Products product) {
        AppNavigator.addProduct(product);
        defaultSettings();
    }
}