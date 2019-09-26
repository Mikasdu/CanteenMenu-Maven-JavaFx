package lt.mikasdu.ui.sqlConnection;

public enum SqlStatement {
    ACTIVE_CATEGORY("SELECT * FROM ProductCategory WHERE status = 1;"),
    ACTIVE_RECIPES_BY_MENU("SELECT * FROM WeekMenuRecipes WHERE WeekMenuId = ? AND Status = 1;"),
    ACTIVE_PRODUCT("SELECT * FROM Product WHERE status = ?;"),
    ACTIVE_RECIPES("SELECT * FROM Recipe WHERE status = 1;"),
    ACTIVE_WEEK_MENU("SELECT * FROM WeekMenu WHERE status = 1;"),
    ACTIVE_PRODUCT_WHERE_CATEGORY("SELECT * FROM Product WHERE status = 1 AND category = ?;"),
    ACTIVE_PRODUCT_IN_RECIPE("SELECT * FROM RecipeProduct WHERE recipeId = ? AND status = 1;"),
    PRODUCT_CATEGORY_BY_ID("SELECT * FROM ProductCategory WHERE id = ?;"),
    PRODUCT_BY_ID("SELECT * FROM Product WHERE Id = ?;"),
    RECIPE_BY_ID("SELECT * FROM Recipe WHERE Id = ?;"),
    INSERT_PRODUCT("INSERT INTO Product (name, measure, description, category) VALUES (?, ?, ?, ?);"),
    INSERT_RECIPE("INSERT INTO Recipe (name, description, price) VALUES (?, ?, ?);"),
    INSERT_WEEK_MENU("INSERT INTO WeekMenu (name) VAlUES (?);"),
    INSERT_WEEK_MENU_RECIPE("INSERT INTO WeekMenuRecipes (WeekMenuId, RecipeId, WeekDayEnum, Quantity) VALUES (?, ?, ?, ?);"),
    INSERT_PRODUCT_CATEGORY("INSERT INTO ProductCategory (name) VALUES (?);"),
    INSERT_RECIPE_PRODUCT("INSERT INTO RecipeProduct (recipeId, productId, quantity) VALUES (?, ?, ?);"),
    PRODUCT_ID_WHERE_CATEGORY("SELECT id FROM Product WHERE category = ?;"),
    UPDATE_PRODUCT_CATEGORY("UPDATE ProductCategory SET name = ? , status = ? WHERE id = ?;"),
    UPDATE_RECIPE_PRODUCTS("UPDATE RecipeProduct SET recipeId = ?, productId = ?, quantity = ?, status = ? WHERE id = ?;"),
    UPDATE_WEEK_MENU_ITEM("UPDATE WeekMenu SET name = ?, status = ? WHERE id = ?;"),
    UPDATE_WEEK_MENU_RECIPE("UPDATE WeekMenuRecipes SET WeekMenuId = ?, RecipeId = ?, WeekDayEnum = ?, Quantity = ?, Status = ? WHERE Id = ?"),
    UPDATE_PRODUCT("UPDATE Product SET name = ?, measure = ?, description = ?, category = ?, status = ? WHERE id = ?;"),
    UPDATE_RECIPES("UPDATE Recipe SET name = ?, description = ?, price = ?, status = ? WHERE id = ?"),
    PRODUCT_PURCHASE_LIST("SELECT Product.Id, Product.Name, \n" +
            "SUM(WeekMenuRecipes.Quantity * RecipeProduct.quantity) AS TotalQuant,\n" +
            "Product.Measure,\n" +
            "Product.Description,\n" +
            "Product.Category\n" +
            "FROM WeekMenuRecipes     \n" +
            "INNER JOIN RecipeProduct ON WeekMenuRecipes.RecipeId=RecipeProduct.recipeId\n" +
            "INNER JOIN Product ON RecipeProduct.productId=Product.Id\n" +
            "Where WeekMenuRecipes.WeekMenuId = ? AND RecipeProduct.status = 1 AND WeekMenuRecipes.Status = 1 AND Product.Status = 1\n" +
            "GROUP BY Product.Id\n" +
            "ORDER BY Product.Category ASC");

    String statement;

    SqlStatement(String statement) {
        this.statement = statement;
    }
    public String getStatement() {
        return statement;
    }
}


