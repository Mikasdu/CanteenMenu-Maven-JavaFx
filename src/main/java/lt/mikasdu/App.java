package lt.mikasdu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lt.mikasdu.settings.Settings;
import lt.mikasdu.ui.controller.MainController;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = new Settings();
        primaryStage.setTitle(settings.configProps.getProperty("appName"));
        primaryStage.setScene(createScene(loadMainPane()));
        primaryStage.setMaximized(Boolean.parseBoolean(settings.configProps.getProperty("fullScreen")));
        primaryStage.setWidth(Double.parseDouble(settings.configProps.getProperty("appWidth")));
        primaryStage.setHeight(Double.parseDouble(settings.configProps.getProperty("appHeight")));
        primaryStage.show();
    }

    private Scene createScene(Pane mainPane) {
        scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource("main.css").toExternalForm());
        return scene;
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = loader.load(getClass().getResourceAsStream(AppNavigator.MAIN));
        MainController mainController = loader.getController();
        AppNavigator.setMainController(mainController);
        AppNavigator.loadApp(AppNavigator.PRODUKTAI);
        return mainPane;
    }

}
