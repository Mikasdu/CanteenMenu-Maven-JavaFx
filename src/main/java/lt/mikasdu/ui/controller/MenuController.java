package lt.mikasdu.ui.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lt.mikasdu.*;
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    @FXML public TableView<WeekMenuRecipes> tbData;
    @FXML public TableColumn<WeekMenuRecipes, Integer> menuId;
    @FXML public TableColumn<WeekMenuRecipes, String> recipeName;
    @FXML public TableColumn<WeekDaysLt, String> weekDay;
    @FXML public TableColumn<WeekMenuRecipes, BigDecimal> recipePrice;
    @FXML public TableColumn<WeekMenuRecipes, Integer> quantity;
    @FXML private ComboBox<WeekMenu> menuItems;
    @FXML private Button buttonRemoveRecipe;
    @FXML private Button buttonEditRecipe;
    @FXML private Button buttonPrintMenu;
    @FXML private Button buttonAddRecipe;
    @FXML private Button buttonEditMenu;
    @FXML private Button buttonRemoveMenu;

    private Settings settings = new Settings();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultSettings();
        tbData.getSelectionModel().selectedIndexProperty().addListener( observable -> {
            if (tbData.getSelectionModel().getSelectedItems().isEmpty()) {
                buttonRemoveRecipe.setDisable(true);
                buttonEditRecipe.setDisable(true);
            } else {
                buttonRemoveRecipe.setDisable(false);
                buttonEditRecipe.setDisable(false);
            }
        });
    }

    private void defaultSettings() {
        buttonEditMenu.setDisable(true);
        buttonRemoveMenu.setDisable(true);
        buttonAddRecipe.setDisable(true);
        buttonRemoveRecipe.setDisable(true);
        buttonPrintMenu.setDisable(true);
        buttonEditRecipe.setDisable(true);

        setWeekMenuItems();
    }

    private void setTableData() {
        int menuItem = menuItems.getSelectionModel().getSelectedItem().getId();
        ObservableList<WeekMenuRecipes> weekMenuRecipes = SqlConnection.returnActiveMenuItems(menuItem);
        menuId.setCellValueFactory(new PropertyValueFactory<>("id"));
        recipeName.setCellValueFactory(new PropertyValueFactory<>("recipeName"));
        recipePrice.setCellValueFactory(new PropertyValueFactory<>("recipePrice"));
        weekDay.setCellValueFactory(new PropertyValueFactory<>("weekDay"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tbData.setItems(weekMenuRecipes);
    }

    private void setWeekMenuItems() {
        ObservableList<WeekMenu> weekMenuList = SqlConnection.returnActiveWeekMenuList();
        menuItems.setItems(weekMenuList);
    }

    public void newMenuButtonClicked() {
        AppNavigator.loadNewWindow(AppNavigator.ADD_MENU_ITEM, new WeekMenu());
        setWeekMenuItems();
    }

    public void editMenuButtonClicked() {
        AppNavigator.loadNewWindow(AppNavigator.ADD_MENU_ITEM, menuItems.getSelectionModel().getSelectedItem());
        setWeekMenuItems();
    }

    public void removeMenuButtonClicked() {
        boolean answer = AlertBox.alertWithConformation(AlertMessage.CONFIRM_DELETE,
                "Įrašo pavadinimas: " + menuItems.getSelectionModel().getSelectedItem().getName()
        );
        if (answer) {
            WeekMenu selectedMenu = menuItems.getSelectionModel().getSelectedItem();
            selectedMenu.setStatus(false);
            selectedMenu.removeFromDatabase();
            setWeekMenuItems();
        }
    }

    public void menuItemsOnChange() {
        if (menuItems.getSelectionModel().isEmpty())
            defaultSettings();
        else {
            buttonPrintMenu.setDisable(false);
            buttonEditMenu.setDisable(false);
            buttonRemoveMenu.setDisable(false);
            buttonAddRecipe.setDisable(false);
            setTableData();
        }
    }

    public void addRecipeButton() {
        int weekMenuId = menuItems.getSelectionModel().getSelectedItem().getId();
        WeekMenuRecipes weekMenuRecipes = new WeekMenuRecipes(weekMenuId);
        AppNavigator.menuRecipeItem(AppNavigator.ADD_MENU_RECIPE, weekMenuRecipes);
        setTableData();
    }

    public void editRecipeButton() {
        WeekMenuRecipes weekMenuRecipes = tbData.getSelectionModel().getSelectedItem();
        AppNavigator.menuRecipeItem(AppNavigator.ADD_MENU_RECIPE, weekMenuRecipes);
        setTableData();
    }
    public void choseDate() {
        ObservableList<WeekMenuRecipes> weekMenuRecipes = tbData.getItems();
        AppNavigator.generateMenuWindow(weekMenuRecipes);
    }

    public void removeRecipeButton() {
        WeekMenuRecipes recipe = tbData.getSelectionModel().getSelectedItem();
        boolean answer = AlertBox.alertWithConformation(
                AlertMessage.CONFIRM_DELETE,
               "Trinamas įrašas: " + recipe.getRecipeName()
        );
        if (answer) {
            recipe.removeFromDatabase();
            setTableData();
        }
    }

    public void openDocumentsFolder() throws IOException {
        Desktop.getDesktop().open(new File(settings.getFilesPath()));
    }


}
