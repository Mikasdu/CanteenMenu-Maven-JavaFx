package lt.mikasdu.ui.sqlConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lt.mikasdu.*;
import lt.mikasdu.ui.alerts.AlertBox;

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

    // Total quantity kazkaip nustatyti reikia
    public static ObservableList<Products> getActiveProducts(SqlStatement sqlStatement, int id) {
        ObservableList<Products> productList = FXCollections.observableArrayList();
        try (Connection conn = getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement.getStatement());
            pstmt.setInt(1, id);
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
            productCategoryName = resultSet.getString("name");
        } catch (SQLException e) {
            AlertBox.exceptionAlert(e);
        }
        return productCategoryName;
    }


    public static ObservableList<ProductCategories> returnActiveProductCategoriesList() {
        ObservableList<ProductCategories> productCategories = FXCollections.observableArrayList();
        String sql = SqlStatement.ACTIVE_CATEGORY.getStatement();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                productCategories.add(new ProductCategories(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
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

    public static ObservableList<Recipes> returnActiveRecipeList() {
        ObservableList<Recipes> recipesList = FXCollections.observableArrayList();
        String sql = SqlStatement.ACTIVE_RECIPES.getStatement();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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


//    public static ObservableList<Products> returnActiveProductsByCategory(int categoryId) {
//        ObservableList<Products> products = FXCollections.observableArrayList();
//        String sql = SqlStatement.ACTIVE_PRODUCT_WHERE_CATEGORY.getStatement();
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, categoryId);
//            ResultSet resultSet = pstmt.executeQuery();
//            while (resultSet.next()) {
//                Products product = new Products();
//                getObjectById(resultSet.getInt("id"), product);
//                products.add(product);
//            }
//        } catch (Exception e) {
//            AlertBox.exceptionAlert(e);
//        }
//        return products;
//    }
//    public static ObservableList getObservableDatabaseList(SqlStatement sqlStatement) {
//
//        ObservableList observableList = FXCollections.observableArrayList();
//        String sql = sqlStatement.getStatement();
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            ResultSet resultSet = pstmt.executeQuery();
//            while (resultSet.next()) {
//                observableList.add(new ProductCategories(resultSet.getInt("id"), resultSet.getString("name")));
//            }
//        } catch (Exception e) {
//            AlertBox.exceptionAlert(e);
//        }
//        return observableList;
//    }
//    public static Products getProductById(int productId) {
//        Products product = new Products(0, "Nenustatyta", "Vienetas", "", 4);
//        String sql = SqlStatement.PRODUCT_BY_ID.getStatement();
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, productId);
//            ResultSet resultSet = pstmt.executeQuery();
//            resultSet.next();
//            product.setId(resultSet.getInt("id"));
//            product.setName(resultSet.getString("name"));
//            product.setMeasure(resultSet.getString("measure"));
//            product.setDescription(resultSet.getString("description"));
//            product.setCategories(resultSet.getInt("category"));
//        } catch (SQLException e) {
//            AlertBox.exceptionAlert(e);
//        }
//        return product;
//    }


//    public static Recipes getRecipeById(int recipeId) {
//        Recipes recipe = new Recipes(0, "", "", new BigDecimal(0), false);
//        String sql = SqlStatement.RECIPE_BY_ID.getStatement();
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, recipeId);
//            ResultSet resultSet = pstmt.executeQuery();
//            resultSet.next();
//            recipe.setId(resultSet.getInt("Id"));
//            recipe.setName(resultSet.getString("Name"));
//            recipe.setDescription(resultSet.getString("Description"));
//            recipe.setPrice(new BigDecimal(resultSet.getString("Price")));
//            recipe.setStatus(resultSet.getBoolean("Status"));
//        } catch (SQLException e) {
//            AlertBox.exceptionAlert(e);
//        }
//        return recipe;
//    }
}