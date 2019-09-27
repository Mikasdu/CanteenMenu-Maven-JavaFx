package lt.mikasdu;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class AppNavigatorTest {

    private String resourcesDirectory = "src\\main\\resources\\lt\\mikasdu\\";

    @Test
    public void mainFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.MAIN);
        assertTrue(file.exists());
    }

    @Test
    public void productsFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.PRODUCTS);
        assertTrue(file.exists());
    }

    @Test
    public void menuFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.MENU);
        assertTrue(file.exists());
    }

    @Test
    public void purchaseFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.PURCHASE);
        assertTrue(file.exists());
    }

    @Test
    public void recipesFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.RECIPES);
        assertTrue(file.exists());
    }

    @Test
    public void productCategoriesFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.PRODUCT_CATEGORIES);
        assertTrue(file.exists());
    }

    @Test
    public void appSettingsFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.APP_SETTINGS);
        assertTrue(file.exists());
    }

    @Test
    public void addMenuItemFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.ADD_MENU_ITEM);
        assertTrue(file.exists());
    }

    @Test
    public void addMenuRecipeFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.ADD_MENU_RECIPE);
        assertTrue(file.exists());
    }

    @Test
    public void generateMenuFxmlFileExists() {
        File file = new File(resourcesDirectory + AppNavigator.GENERATE_MENU);
        assertTrue(file.exists());
    }

}