package lt.mikasdu.ui.sqlConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lt.mikasdu.*;
import lt.mikasdu.ui.alerts.AlertBox;
import lt.mikasdu.ui.alerts.AlertMessage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;


public class SqlConnection {

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (Exception e) {
            AlertBox.exceptionAlert(e);
        }
        return null;
    }

    public static void updateDatabase(SqlStatement sqlStatement, Object... arg) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sqlStatement.getStatement());
                for (int i = 0; i < arg.length; i++)
                    pstmt.setObject(i + 1, arg[i]);
                pstmt.executeUpdate();
            } else {
                // TODO
            }

        } catch (SQLException e) {
            AlertBox.exceptionAlert(e);
        }
    }

    public static Database getObjectById(int id, Database dbObj) {
        try (Connection conn = getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(dbObj.getObjectSqlStatement());
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            dbObj.setParam(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            AlertBox.exceptionAlert(e);
        }
        return dbObj;
    }

    public static ObservableList<Products> getProductsList(SqlStatement sqlStatement, int arg, boolean isActive) {
        ObservableList<Products> productList = FXCollections.observableArrayList();
        try (Connection conn = getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement.getStatement());
            pstmt.setInt(1, arg);
            pstmt.setBoolean(2, isActive);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Products product = new Products();
                getObjectById(resultSet.getInt("id"), product);
                try {
                    product.setQuantity(resultSet.getString("TotalQuant"));
                } catch (java.sql.SQLException e) {
                    product.setQuantity("0");
                }
                productList.add(product);
            }
        } catch (Exception e) {
            AlertBox.exceptionAlert(e);
        }
        return productList;
    }


    public static ObservableList<WeekMenuRecipes> returnActiveMenuItems(int menuId) {
        ObservableList<WeekMenuRecipes> weekMenuRecipes = FXCollections.observableArrayList();
        String sql = SqlStatement.ACTIVE_RECIPES_BY_MENU.getStatement();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, menuId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                weekMenuRecipes.add(new WeekMenuRecipes(
                        resultSet.getInt("Id"),
                        resultSet.getInt("WeekMenuId"),
                        resultSet.getInt("RecipeId"),
                        resultSet.getInt("WeekDayEnum"),
                        new BigInteger(String.valueOf(resultSet.getInt("Quantity"))),
                        resultSet.getBoolean("Status")
                ));
            }
        } catch (Exception e) {
            AlertBox.exceptionAlert(e);
        }
        return weekMenuRecipes;
    }

    public static ObservableList<RecipeProduct> returnActiveRecipeProduct(int recipeId) {
        ObservableList<RecipeProduct> products = FXCollections.observableArrayList();
        String sql = SqlStatement.ACTIVE_PRODUCT_IN_RECIPE.getStatement();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recipeId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                products.add(new RecipeProduct(
                        resultSet.getInt("id"),
                        resultSet.getInt("recipeId"),
                        resultSet.getInt("productId"),
                        new BigDecimal(resultSet.getString("quantity"))
                ));
            }
        } catch (Exception e) {
            AlertBox.exceptionAlert(e);
        }
        return products;
    }


    public static String getProductCategory(int id) {
        String sql = SqlStatement.PRODUCT_CATEGORY_BY_ID.getStatement();
        String productCategoryName = "NENUMATYTAS";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            productCategoryName = resultSet.getString("name"); //todo perdaryti i sql pagal id
        } catch (SQLException e) {
            AlertBox.exceptionAlert(e);
        }
        return productCategoryName;
    }

    public static ObservableList<ProductCategories> getProductCategoriesList(boolean status) {
        ObservableList<ProductCategories> productCategories = FXCollections.observableArrayList();
        String sql = SqlStatement.PRODUCT_CATEGORIES.getStatement();
        try (Connection conn = getConnection()) {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setBoolean(1, status);
                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ProductCategories productCategory = new ProductCategories();
                    getObjectById(resultSet.getInt("id"), productCategory);
                    productCategories.add(productCategory);
                }
            } else {
               AlertBox.alertSimple(AlertMessage.ERROR_CONNECTION);
            }
        } catch (Exception e) {
            AlertBox.exceptionAlert(e);
        }
        return productCategories;
    }


    public static ObservableList<WeekMenu> returnActiveWeekMenuList() {
        ObservableList<WeekMenu> weekMenus = FXCollections.observableArrayList();
        String sql = SqlStatement.ACTIVE_WEEK_MENU.getStatement();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                weekMenus.add(new WeekMenu(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("status")
                ));
            }
        } catch (Exception e) {
            AlertBox.exceptionAlert(e);
        }
        return weekMenus;
    }

    public static ObservableList<Recipes> returnActiveRecipeList(boolean isActive) {
        ObservableList<Recipes> recipesList = FXCollections.observableArrayList();
        String sql = SqlStatement.ACTIVE_RECIPES.getStatement();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isActive);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                BigDecimal bigDecimal = resultSet.getBigDecimal("price");
                recipesList.add(new Recipes(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        bigDecimal,
                        resultSet.getBoolean("Status")
                ));
            }
        } catch (Exception e) {
            AlertBox.exceptionAlert(e);
        }
        return recipesList;
    }

    public static boolean productsWithCategoryId(ProductCategories productCategories) {
        String sql = SqlStatement.PRODUCT_ID_WHERE_CATEGORY.getStatement();
        boolean answer = true;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productCategories.getId());
            answer = pstmt.executeQuery().next();
        } catch (SQLException e) {
            AlertBox.exceptionAlert(e);
        }
        return answer;
    }
}