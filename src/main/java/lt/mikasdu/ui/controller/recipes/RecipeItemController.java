package lt.mikasdu.ui.controller.recipes;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lt.mikasdu.Formatter;
import lt.mikasdu.Recipes;
import lt.mikasdu.Validator;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

import java.math.BigDecimal;

public class RecipeItemController {

    @FXML private VBox recipeInputsBox;
    @FXML private Label headerLabelCategory;
    @FXML private TextField recipeNameInput;
    @FXML private TextArea recipeDescriptionInput;
    @FXML private TextField recipePriceInput;
    @FXML private HBox isDeletedBox;
    @FXML private CheckBox checkBoxRecipeStatus;
    @FXML private Button cancelButton;

    private Recipes recipe;
    private boolean isNew;

    public void initData(Recipes recipe) {
        this.recipe = recipe;
        isNew = recipe.getId() == 0;
        recipePriceInput.setTextFormatter(Formatter.formatDecimalPriceNumbers());
        setDefaultSettings();
    }

    private void setDefaultSettings() {
        checkBoxRecipeStatus.setSelected(!recipe.isActive());
        isDeletedBox.setDisable(recipe.isActive());
        recipeInputsBox.setDisable(!recipe.isActive());
        if (isNew) {
            headerLabelCategory.setText("Įveskite naują receptą");
        } else {
            headerLabelCategory.setText("Redaguojamas receptas Id: " + recipe.getId());
            recipeNameInput.setText(recipe.getName());
            recipeDescriptionInput.setText(recipe.getDescription());
            recipePriceInput.setText(recipe.getPrice().toString());
        }
    }

    public void buttonSave() {
        if (Validator.stringValid(recipeNameInput.getText(), 5, 50)) {
            recipe.setName(recipeNameInput.getText());
            if (!recipePriceInput.getText().isEmpty()) {
                recipe.setPrice(new BigDecimal(recipePriceInput.getText()));
                recipe.setDescription(recipeDescriptionInput.getText());
                if (isNew) {
                    recipe.saveToDatabase();
                } else {
                    recipe.updateDatabase();
                }
                closeCurrentWindow();
            } else AlertBox.alertSimple(AlertMessage.ERROR_PRICE);
        } else AlertBox.alertSimple(AlertMessage.ERROR_NAME);
    }

    public void removeDeletedTag() {
        recipeInputsBox.setDisable(checkBoxRecipeStatus.isSelected());
        recipe.setActive(!checkBoxRecipeStatus.isSelected());
        recipeNameInput.setText(recipe.getName());
    }

    public void buttonCancel() {
        closeCurrentWindow();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
