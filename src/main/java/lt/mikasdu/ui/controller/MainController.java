package lt.mikasdu.ui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import lt.mikasdu.AppNavigator;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

public class MainController {

    @FXML
    private StackPane contentHolder;

    public void setApp(Node node) {
        contentHolder.getChildren().setAll(node);
    }

    public void changeContentPurchaseList() { AppNavigator.loadApp(AppNavigator.PURCHASE); }
    public void changeContentMenu() { AppNavigator.loadApp(AppNavigator.MENU); }
    public void changeContentRecipes() {
        AppNavigator.loadApp(AppNavigator.RECEPTAI);
    }
    public void changeContentProducts() {
        AppNavigator.loadApp(AppNavigator.PRODUKTAI);
    }
    public void changeContentProductCategories() { AppNavigator.loadApp(AppNavigator.PRODUCTCATEGORIES); }
    public void showAbout() { AlertBox.alertSimple(AlertMessage.ABOUT); }
    public void closeProgram() { Platform.exit(); }

}
