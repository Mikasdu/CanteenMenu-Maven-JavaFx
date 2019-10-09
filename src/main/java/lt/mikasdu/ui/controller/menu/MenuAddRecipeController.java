package lt.mikasdu.ui.controller.menu;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lt.mikasdu.Formatter;
import lt.mikasdu.Recipes;
import lt.mikasdu.WeekDaysLt;
import lt.mikasdu.WeekMenuRecipes;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuAddRecipeController implements Initializable {
    @FXML private Label editLabel;
    @FXML private ComboBox<Recipes> recipesListComboBox;
    @FXML private TextField quantityInput;
    @FXML private ComboBox<WeekDaysLt> weekDays;
    @FXML private Button cancelButton;

    private WeekMenuRecipes weekMenuRecipes;
    private boolean isNew;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Recipes> recipesList = SqlConnection.returnActiveRecipeList(true);
        recipesListComboBox.setItems(recipesList);
        quantityInput.setTextFormatter(Formatter.formatIntegerNumbers());
        weekDays.getItems().setAll(WeekDaysLt.values());

    }

    public void buttonSave() {
        if (!weekDays.getSelectionModel().isEmpty()) {
            if (!recipesListComboBox.getSelectionModel().isEmpty()) {
                if (!quantityInput.getText().isEmpty()) {
                    int newRecipeId = recipesListComboBox.getSelectionModel().getSelectedItem().getId();
                    int newWeekDay = weekDays.getSelectionModel().getSelectedItem().getDayNumber();
                    BigInteger newQuantity = new BigInteger(quantityInput.getText());
                    weekMenuRecipes.getRecipe().setId(newRecipeId);
                    weekMenuRecipes.setWeekDay(newWeekDay);
                    weekMenuRecipes.setQuantity(newQuantity);
                    weekMenuRecipes.setStatus(true);
                    if (isNew) weekMenuRecipes.saveToDatabase();
                    else weekMenuRecipes.updateDatabase();

                    closeCurrentWindow();
                } else AlertBox.alertSimple(AlertMessage.ERROR_QUANTITY);
            } else AlertBox.alertSimple(AlertMessage.ERROR_RECIPE);
        } else AlertBox.alertSimple(AlertMessage.ERROR_DAY);
    }

    public void buttonCancel() {
        closeCurrentWindow();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void initData(WeekMenuRecipes weekMenuRecipes) {
        this.weekMenuRecipes = weekMenuRecipes;
        if (weekMenuRecipes.getId() == 0) {
            isNew = true;
            editLabel.setText("Naujas meniu įrašas");
        } else {
            isNew = false;
            editLabel.setText("Redaguojamas įrašas Id: " + weekMenuRecipes.getId());
            recipesListComboBox.getItems().forEach(recipe -> {
                if (recipe.getId() == weekMenuRecipes.getRecipe().getId())
                    recipesListComboBox.getSelectionModel().select(recipe);
            });
            weekDays.getItems().forEach(day -> {
                if (day.getDayNumber() == weekMenuRecipes.getWeekDayNumber())
                    weekDays.getSelectionModel().select(day);
            });
            quantityInput.setText(weekMenuRecipes.getQuantity().toString());
        }
    }
}
