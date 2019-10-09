package lt.mikasdu.ui.controller;


import javafx.beans.binding.Bindings;
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

//todo remove from table category
//todo sort recipes by name


public class RecipesController implements Initializable {
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
        recipesListView.getSelectionModel().selectedItemProperty().addListener((ob) ->
                recipeListViewChange()
        );
//        tbData.getSelectionModel().selectedItemProperty().addListener(e -> {
//            .setDisable(true);
//            System.out.println("sout");
//        });
    }

    private void recipeProductBoxVisibility(boolean isVisisble){
        recipeProductBox.setDisable(!isVisisble);
    }
    private void setDefaultSettings() {
        setRecipes();
        recipeProductBoxVisibility(false);
        recipesListView.getSelectionModel().clearSelection();
        recipesListView.setDisable(false);
        deleteRecipeButton.setDisable(true);
        editRecipeButton.setDisable(true);
        productQuantityInput.clear();
        productBox.getSelectionModel().clearSelection();
    }

    private void showRecipeTable() {
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

    private void setRecipes() {
        ObservableList<Recipes> recipesList = SqlConnection.returnActiveRecipeList();
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

    public void recipeListViewChange() {
        if (!recipesListView.getSelectionModel().isEmpty()) {
            setProductCategories();
            recipeProductBoxVisibility(true);
            showRecipeTable();
            deleteRecipeButton.setDisable(false);
            editRecipeButton.setDisable(false);
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
        if (!tbData.getSelectionModel().isEmpty()) {
            RecipeProduct product = tbData.getSelectionModel().getSelectedItem();
            String quantity = product.getQuantity().toString();
            product.setQuantity(new BigDecimal(AlertBox.alertWithInput(quantity)));
            product.updateDatabase();
            showRecipeTable();
        } else AlertBox.alertSimple(AlertMessage.ERROR_PLEASECHOOSE);
    }

    public void removeProductFromRecipeButton() {
        if (!tbData.getSelectionModel().isEmpty()) {
            boolean answer = AlertBox.alertWithConformation(AlertMessage.CONFIRM_DELETE);
            if (answer) {
                tbData.getSelectionModel().getSelectedItem().removeFromDatabase();
                showRecipeTable();
            }
        } else AlertBox.alertSimple(AlertMessage.ERROR_PLEASECHOOSE);
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
