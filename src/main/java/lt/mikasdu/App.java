package lt.mikasdu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.controller.MainController;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = new Settings();
        primaryStage.setTitle(settings.getAppName());
        primaryStage.setScene(createScene(loadMainPane()));
        primaryStage.setMaximized(settings.isFullScreen());
        primaryStage.setWidth(Double.parseDouble(settings.getAppWidth())); // TODO
        primaryStage.setHeight(settings.getAppHeight());
        primaryStage.show();
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource("main.css").toExternalForm());
        return scene;
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = loader.load(getClass().getResourceAsStream(AppNavigator.MAIN));
        MainController mainController = loader.getController();
        AppNavigator.setMainController(mainController);
        AppNavigator.loadApp(AppNavigator.PRODUCTS);
        return mainPane;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
