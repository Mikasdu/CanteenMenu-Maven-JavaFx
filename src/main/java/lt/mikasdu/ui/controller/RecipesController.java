package lt.mikasdu.ui.controller;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lt.mikasdu.*;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;
import lt.mikasdu.ui.sqlConnection.SqlConnection;
import lt.mikasdu.ui.sqlConnection.SqlStatement;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

//todo pakeitaliojus istrintus kazkaip edit butonas buna active

public class RecipesController implements Initializable {
    @FXML private CheckBox showDeletedRecipes;
    @FXML private Button editRecipeProductButton;
    @FXML private Button deleteRecipeProductButton;
    @FXML private VBox recipeProductBox;
    @FXML private ListView<Recipes> recipesListView;
    @FXML private Label quantityLabel;
    @FXML private TableColumn<RecipeProduct, String> productDescription;
    @FXML private TableColumn<RecipeProduct, String> productCategory;
    @FXML private TableColumn<RecipeProduct, Integer> productQuantityTable;
    @FXML private TableColumn<RecipeProduct, String> productMeasure;
    @FXML private TableColumn<RecipeProduct, Integer> productId;
    @FXML private TableColumn<RecipeProduct, String> productName;
    @FXML private Label recipeDescriptionLabel;
    @FXML private TableView<RecipeProduct> tbData;
    @FXML private TextField productQuantityInput;
    @FXML private Button editRecipeButton;
    @FXML private Button deleteRecipeButton;
    @FXML private ComboBox<ProductCategories> productCategoryBox;
    @FXML private ComboBox<Products> productBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultSettings();
        productQuantityInput.setTextFormatter(Formatter.formatDecimalQuantityNumbers());
        showDeletedRecipes.setOnAction(event ->
                setRecipes(!showDeletedRecipes.isSelected())
        );
        recipesListView.getSelectionModel().selectedItemProperty().addListener((ob) -> {
            recipeListViewChange();
            deleteRecipeButton.setDisable(true);
            if (!recipesListView.getSelectionModel().isEmpty()) {
                editRecipeButton.setDisable(false);
                if (recipesListView.getSelectionModel().getSelectedItem().isActive())
                    deleteRecipeButton.setDisable(false);
            } else {
                editRecipeButton.setDisable(true);
            }
        });

        tbData.getSelectionModel().selectedItemProperty().addListener(e -> {
            editRecipeProductButton.setDisable(true);
            deleteRecipeProductButton.setDisable(true);
            if (!tbData.getSelectionModel().isEmpty()) {
                editRecipeProductButton.setDisable(false);
                deleteRecipeProductButton.setDisable(false);
            }
        });
    }

    private void recipeProductBoxVisibility(boolean isVisisble) {
        recipeProductBox.setDisable(!isVisisble);
    }

    private void setDefaultSettings() {
        setRecipes(true);
        recipeProductBoxVisibility(false);
        recipesListView.getSelectionModel().clearSelection();
        recipesListView.setDisable(false);
        deleteRecipeButton.setDisable(true);
        editRecipeButton.setDisable(true);
        productQuantityInput.clear();
        productBox.getSelectionModel().clearSelection();
    }

    private void showRecipeTable() {
        editRecipeProductButton.setDisable(true);
        deleteRecipeProductButton.setDisable(true);
        int recipeId = recipesListView.getSelectionModel().getSelectedItem().getId();
        ObservableList<RecipeProduct> recipeProducts = SqlConnection.returnActiveRecipeProduct(recipeId);
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantityTable.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productMeasure.setCellValueFactory(new PropertyValueFactory<>("productMeasure"));
        productDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        productCategory.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        tbData.setItems(recipeProducts);
    }

    private void setRecipes(boolean isActive) {
        ObservableList<Recipes> recipesList = SqlConnection.returnActiveRecipeList(isActive);
        recipesListView.setItems(recipesList);
    }

    public void productBoxOnChange() {
        if (!productBox.getSelectionModel().isEmpty())
            quantityLabel.setText("Kiekis: " + productBox.getSelectionModel().getSelectedItem().getMeasure());
        else quantityLabel.setText("Kiekis");
    }

    private void setProductCategories() {
        ObservableList<ProductCategories> categoriesList = SqlConnection.getProductCategoriesList(true);
        productCategoryBox.setItems(categoriesList);
        productBox.setDisable(true);
    }

    public void productCategoryBoxOnchange() {
        if (!productCategoryBox.getSelectionModel().isEmpty()) {
            productBox.setDisable(false);
            int catId = productCategoryBox.getSelectionModel().getSelectedItem().getId();
            ObservableList<Products> productsList = SqlConnection.getProductsList(SqlStatement.PRODUCT_WHERE_CATEGORY, catId, true);
            productBox.setItems(productsList);
        } else
            productBox.setDisable(true);
    }

    private void recipeListViewChange() {
        if (!recipesListView.getSelectionModel().isEmpty()) {
            setProductCategories();
            recipeProductBoxVisibility(true);
            showRecipeTable();
//            deleteRecipeButton.setDisable(false);
//            editRecipeButton.setDisable(false);
            recipeDescriptionLabel.setText(recipesListView.getSelectionModel().getSelectedItem().getDescription());
        }
    }

    public void deleteRecipeButton() {
        boolean answer = AlertBox.alertWithConformation(AlertMessage.CONFIRM_DELETE,
                recipesListView.getSelectionModel().getSelectedItem().getName());
        if (answer) {
            Recipes recipe = recipesListView.getSelectionModel().getSelectedItem();
            recipe.removeFromDatabase();
            setDefaultSettings();
        }
    }


    public void addProductButton() {
        String quantity = this.productQuantityInput.getText();
        boolean nameValid = Validator.stringValid(quantity, 1, 10);
        if (productBox.getSelectionModel().isEmpty()) {
            AlertBox.alertSimple(AlertMessage.ERROR_PRODUCT);
        } else if (!nameValid) {
            AlertBox.alertSimple(AlertMessage.ERROR_QUANTITY);
        } else {
            int recipeId = recipesListView.getSelectionModel().getSelectedItem().getId();
            int productId = productBox.getSelectionModel().getSelectedItem().getId();
            BigDecimal productQuantity = new BigDecimal(quantity);
            RecipeProduct recipeProduct = new RecipeProduct(1, recipeId, productId, productQuantity);
            recipeProduct.saveToDatabase();
            resetProductInputs();
            showRecipeTable();
        }
    }

    private void resetProductInputs() {
        productCategoryBox.getSelectionModel().clearSelection();
        productBox.getSelectionModel().clearSelection();
        productQuantityInput.clear();
    }

    public void changeProductQuantityButton() {
        RecipeProduct product = tbData.getSelectionModel().getSelectedItem();
        String quantity = product.getQuantity().toString();
        product.setQuantity(new BigDecimal(AlertBox.alertWithInput(quantity)));
        product.updateDatabase();
        showRecipeTable();
    }

    public void removeProductFromRecipeButton() {
        boolean answer = AlertBox.alertWithConformation(AlertMessage.CONFIRM_DELETE); //todo add what deleting ?
        if (answer) {
            tbData.getSelectionModel().getSelectedItem().removeFromDatabase();
            showRecipeTable();
        }
    }

    public void addNewRecipe() {
        recipeWindow(new Recipes());
    }

    private void recipeWindow(Recipes recipe) {
        AppNavigator.addRecipe(recipe);
        setDefaultSettings();
    }

    public void editSelectedRecipe() {
        recipeWindow(recipesListView.getSelectionModel().getSelectedItem());
    }

}
