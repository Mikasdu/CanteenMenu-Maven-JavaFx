package lt.mikasdu.ui.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lt.mikasdu.ProductCategories;
import lt.mikasdu.Validator;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;
import lt.mikasdu.ui.sqlConnection.SqlStatement;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductCategoriesController implements Initializable {

    @FXML private Button editCategoryButton;
    @FXML private TextField productCategoryName;
    @FXML private TableView<ProductCategories> tbData;
    @FXML private TableColumn<ProductCategories, Integer> categoryId;
    @FXML private TableColumn<ProductCategories, String> categoryName;
    @FXML private Label editLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button removeCategoryButton;

    private ObservableList<ProductCategories> productCategoriesList = FXCollections.observableArrayList();
    private boolean isNew = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableData();
        defaultSettings();
    }

    private void setTableData() {
        productCategoriesList.clear();
        tbData.getItems().clear();
        //TODO sarasa iskarto gauti
        try {
            Connection con = SqlConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(SqlStatement.ACTIVE_CATEGORY.getStatement());
            while (rs.next()) {
                productCategoriesList.add(new ProductCategories(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
            categoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
            categoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbData.setItems(productCategoriesList);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void defaultSettings() {
        saveButton.disableProperty().bind(Bindings.isEmpty(productCategoryName.textProperty()));
        editCategoryButton.disableProperty().bind(Bindings.isEmpty(tbData.getSelectionModel().getSelectedItems()));
        removeCategoryButton.disableProperty().bind(Bindings.isEmpty(tbData.getSelectionModel().getSelectedItems()));
        productCategoryName.clear();
        cancelButton.setVisible(false);
        isNew = true;
        saveButton.setText("Išsaugoti įrašą");
        editLabel.setText("Įveskite naują produktų kategoriją");
    }

    public void saveButtonClicked() {
        String productCategoryName = this.productCategoryName.getText();
        if (!Validator.stringValid(productCategoryName, 5, 50)) {
            AlertBox.alertSimple(AlertMessage.ERROR_NAME);
        } else {
            if (isNew)
                new ProductCategories(productCategoryName).saveToDatabase();
            else {
                ProductCategories productCategories = tbData.getSelectionModel().getSelectedItem();
                productCategories.setName(productCategoryName);
                productCategories.updateDatabase();
            }
            setTableData();
            defaultSettings();
        }
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
            ProductCategories productCategoriesSelected = tbData.getSelectionModel().getSelectedItem();
            editLabel.setText("Redaguojate įrašą kurio Id: " + productCategoriesSelected.getId() +
                    " Pavadinimas: " + productCategoriesSelected.getName());
            saveButton.setText("Redaguoti įrašą");
            cancelButton.setVisible(true);
            isNew = false;
            productCategoryName.setText(productCategoriesSelected.getName());
        } else AlertBox.alertSimple(AlertMessage.ERROR_PLEASECHOOSE);
    }

    public void cancelButtonClicked() {
        defaultSettings();
    }
}