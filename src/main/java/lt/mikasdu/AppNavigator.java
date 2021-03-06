package lt.mikasdu;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.controller.MainController;
import lt.mikasdu.ui.controller.categories.ProductCategoriesItemController;
import lt.mikasdu.ui.controller.menu.MenuAddItemController;
import lt.mikasdu.ui.controller.menu.MenuAddRecipeController;
import lt.mikasdu.ui.controller.menu.MenuGenerateFileController;
import lt.mikasdu.ui.controller.products.ProductItemController;
import lt.mikasdu.ui.controller.recipes.RecipeItemController;

import java.io.IOException;

public class AppNavigator {
    static final String MAIN = "main.fxml";
    public static final String PRODUCTS = "products.fxml";
    public static final String MENU = "menu.fxml";
    public static final String PURCHASE = "purchase.fxml";
    public static final String RECIPES = "recipes.fxml";
    public static final String PRODUCT_CATEGORIES = "productcategories.fxml";
    static final String APP_SETTINGS ="appSettings.fxml";
    public static final String ADD_MENU_ITEM = "sub/menuAddItem.fxml";
    public static final String ADD_MENU_RECIPE = "sub/menuAddRecipe.fxml";
    static final String GENERATE_MENU = "sub/menuGenerateFile.fxml";
    private static final String ADD_PRODUCT_CATEGORY = "sub/productCategoriesItem.fxml";
    private static final String ADD_PRODUCT = "sub/productItem.fxml";
    private static final String ADD_RECIPE = "sub/recipeItem.fxml";

    private static MainController mainController;

    static void setMainController(MainController mainController) {
        AppNavigator.mainController = mainController;
    }

    public static void loadApp(String fxml) {
        try {
            mainController.setApp(
                    FXMLLoader.load(AppNavigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addProductCategory(ProductCategories productCategory) {
        Stage window = getStage("Pridėti produktų kategoriją");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = getScene(AppNavigator.ADD_PRODUCT_CATEGORY, new StackPane(), loader);
        ProductCategoriesItemController productCategoriesItemController = loader.getController();
        productCategoriesItemController.initData(productCategory);
        showWindow(window, scene);
    }

    public static void addProduct(Products product) {
        Stage window = getStage("Pridėti produktą");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = getScene(AppNavigator.ADD_PRODUCT, new StackPane(), loader);
        ProductItemController productItemController = loader.getController();
        productItemController.initData(product);
        showWindow(window, scene);
    }

    private static Stage getStage(String windowName) {
        Stage window = new Stage();
        window.setTitle(windowName);
        window.getIcons().add(new Image(AppNavigator.class.getResourceAsStream("image/CanteenMenu.png")));
        return window;
    }

    public static void addRecipe(Recipes recipe) {
        Stage window = getStage("Pridėti receptą");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = getScene(AppNavigator.ADD_RECIPE, new StackPane(), loader);
        RecipeItemController recipeItemController = loader.getController();
        recipeItemController.initData(recipe);
        showWindow(window, scene);
    }


    public static void openAppSettingsWindow() {
        Stage window = getStage("Programos nustatymai");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = getScene(APP_SETTINGS, new StackPane(), loader);
        showWindow(window, scene);
    }

    public static void generateMenuWindow(ObservableList<WeekMenuRecipes> weekMenuRecipes) {
        Stage window = getStage("Generuoti savaitės meniu PDF");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = getScene(GENERATE_MENU, new StackPane(), loader);
        MenuGenerateFileController menuGenerateFileController = loader.getController();
        menuGenerateFileController.initData(weekMenuRecipes);
        showWindow(window, scene);
    }

    public static void menuRecipeItem(String fxml, WeekMenuRecipes weekMenuRecipes) {
        Stage window = getStage("Recepto įrašas");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = getScene(fxml, new StackPane(), loader);
        MenuAddRecipeController menuAddRecipeController = loader.getController();
        menuAddRecipeController.initData(weekMenuRecipes);
        showWindow(window, scene);
    }


    public static void loadNewWindow(String fxml, WeekMenu weekMenu) {
        Stage window = getStage("Meniu įrašas");
        FXMLLoader loader = new FXMLLoader();
        Scene scene = getScene(fxml, new StackPane(), loader);
        MenuAddItemController menuAddItemController = loader.getController();
        menuAddItemController.initData(weekMenu);
        showWindow(window, scene);
    }

    private static Scene getScene(String fxml, StackPane layout, FXMLLoader loader) {
        loader.setLocation(AppNavigator.class.getResource(fxml));
        try {
            layout = loader.load();
        } catch (IOException e) {
            AlertBox.exceptionAlert(e);
        }
        return new Scene(layout);
    }

    private static void showWindow(Stage window, Scene scene) {
        scene.getStylesheets().setAll(AppNavigator.class.getResource("main.css").toExternalForm());
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setScene(scene);
        window.showAndWait();
    }
}
