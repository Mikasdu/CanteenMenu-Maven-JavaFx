package lt.mikasdu.ui.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lt.mikasdu.Products;
import lt.mikasdu.WeekMenu;
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.pdfCreator.PdfFile;
import lt.mikasdu.ui.sqlConnection.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;


public class PurchaseController implements Initializable {

    @FXML private TableView<Products> tbData;
    @FXML private Button printButton;
    @FXML private TableColumn<Products, Integer> productId;
    @FXML private TableColumn<Products, String> productName;
    @FXML private TableColumn<Products, BigDecimal> productQuantityTable;
    @FXML private TableColumn<Products, String> productMeasure;
    @FXML private TableColumn<Products, String> productDescription;
    @FXML private TableColumn<Products, String> productCategory;
    @FXML private ComboBox<WeekMenu> weekMenuComboBox;
    private Settings settings = new Settings();
    //todo print buton disabled while not selected list

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWeekMenuComboBoxItems();
        printButton.setDisable(true);
    }

    private void setTableData(){
        int id = weekMenuComboBox.getSelectionModel().getSelectedItem().getId();
        ObservableList<Products> productsItems = SqlConnection.getProductsList(SqlStatement.PRODUCT_PURCHASE_LIST, id, true);
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantityTable.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productMeasure.setCellValueFactory(new PropertyValueFactory<>("measure"));
        productCategory.setCellValueFactory(new PropertyValueFactory<>("categories"));
        productDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbData.setItems(productsItems);
    }

    private void setWeekMenuComboBoxItems() {
        ObservableList<WeekMenu> weekMenuList = SqlConnection.returnActiveWeekMenuList();
        weekMenuComboBox.setItems(weekMenuList);
    }
    public void weekMenuComboBoxChange() {
        setTableData();
        if (weekMenuComboBox.getSelectionModel().isEmpty())
            printButton.setDisable(true);
        else printButton.setDisable(false);
    }

    public void printButtonClick() throws IOException {
        ObservableList<Products> items = tbData.getItems();
        PdfFile.createFile(items);
    }

    public void openDocumentsFolder() throws IOException {
        Desktop.getDesktop().open(new File(settings.getFilesPath()));
    }

}
