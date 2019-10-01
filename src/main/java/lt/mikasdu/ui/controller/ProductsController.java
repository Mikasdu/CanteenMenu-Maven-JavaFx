package lt.mikasdu.ui.controller;

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
    @FXML private TextField productNameInput;
    @FXML private ChoiceBox<String> productMesureChoice;
    @FXML private ComboBox<ProductCategories> productCategoryBox;
    @FXML private TextArea productDescriptionInput;
    @FXML private Label editLabel;
    @FXML private TableView<Products> tbData;
    @FXML private TableColumn<Products, Integer> productId;
    @FXML private TableColumn<Products, String> productName;
    @FXML private TableColumn<Products, String> productMesure;
    @FXML private TableColumn<Products, String> productDescription;
    @FXML private TableColumn<Products, Integer> productCategory;

    //TODO kai trini objecta nerodo kuris trinamas

    private boolean isNew = true;
    private ObservableList<Products> produktuDuomenys = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableData();
        setProductCategories();
    }

    private void setProductCategories() {
        ObservableList<ProductCategories> categoriesList = SqlConnection.returnProductCategoriesList(true);
        productCategoryBox.setItems(categoriesList);
    }


    public void buttonCancelProduct() {
        defaultSettings();
    }

    private void defaultSettings() {
        productNameInput.clear();
        productMesureChoice.getSelectionModel().clearSelection();
        productCategoryBox.getSelectionModel().clearSelection();
        productDescriptionInput.clear();
        isNew = true;
        editLabel.setText("Įveskite naują produktą");
    }

    public void buttonSaveProduct() {
        String productName = productNameInput.getText();
        String productMeasure = productMesureChoice.getValue();
        String description = productDescriptionInput.getText();
        boolean nameValid = Validator.stringValid(productName, 5, 50);
        if (!nameValid)
            AlertBox.alertSimple(AlertMessage.ERROR_NAME);
        else if (productMesureChoice.getSelectionModel().isEmpty())
            AlertBox.alertSimple(AlertMessage.ERROR_MEASUREUNTI);
        else if (productCategoryBox.getSelectionModel().isEmpty())
            AlertBox.alertSimple(AlertMessage.ERROR_CATEGORY);
        else {
            int productCategory = productCategoryBox.getSelectionModel().getSelectedItem().getId();
            if (isNew) {
                Products newProduct = new Products(0, productName, productMeasure, description, productCategory);
                newProduct.saveToDatabase();
            } else {
                Products editProduct = tbData.getSelectionModel().getSelectedItem();
                editProduct.setName(productName);
                editProduct.setMeasure(productMeasure);
                editProduct.setDescription(description);
                editProduct.setCategories(productCategory);
                editProduct.updateDatabase();
            }
            defaultSettings();
            setTableData();
        }
    }

    public void deleteButtonClicked() {
        if (!tbData.getSelectionModel().isEmpty()) {
            boolean confirmedDelete = AlertBox.alertWithConformation(AlertMessage.CONFIRM_DELETE);

            System.out.println("Paspaustas mygtukas: " + confirmedDelete); //todo uzkomentuota kazkodel
//            if (confirmedDelete) {
//                Products productSelected = tbData.getSelectionModel().getSelectedItem();
//                tbData.getItems().remove(productSelected);
//                productSelected.removeFromDatabase();
//            }
        } else
            AlertBox.alertSimple(AlertMessage.ERROR_PLEASECHOOSE);
    }

    public void editButtonClicked() {
        if (!tbData.getSelectionModel().isEmpty()) {
            Products productSelected = tbData.getSelectionModel().getSelectedItem();
            productNameInput.setText(productSelected.getName());
            productDescriptionInput.setText(productSelected.getDescription());
            productMesureChoice.getSelectionModel().select(productSelected.getMeasure());
            productCategoryBox.getItems().forEach(productCategory -> {
                if (productCategory.getId() == productSelected.getCategoriesId())
                    productCategoryBox.getSelectionModel().select(productCategory);
            });
            isNew = false;
        } else AlertBox.alertSimple(AlertMessage.ERROR_PLEASECHOOSE);
    }

    private void setTableData() {
        produktuDuomenys.clear();
        tbData.getItems().clear();
        produktuDuomenys = SqlConnection.getProductsList(SqlStatement.ACTIVE_PRODUCT, 1);
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productMesure.setCellValueFactory(new PropertyValueFactory<>("measure"));
        productCategory.setCellValueFactory(new PropertyValueFactory<>("categories"));
        productDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbData.setItems(produktuDuomenys);
    }
}