package lt.mikasdu;

import lt.mikasdu.ui.sqlConnection.Database;
import lt.mikasdu.ui.sqlConnection.SqlConnection;
import lt.mikasdu.ui.sqlConnection.SqlStatement;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeekMenuRecipes implements Database {

    private int id;
    private int weekMenuId;
    private String recipeName;
    private BigDecimal recipePrice;
    private WeekDaysLt weekDay;
    private BigInteger quantity;
    private boolean status;
    private Recipes recipe;


    public WeekMenuRecipes(int weekMenuId) {
        setWeekMenuId(weekMenuId);
        this.recipe = new Recipes(0, "", "", new BigDecimal("0"), true);
    }

    public WeekMenuRecipes(int id, int weekMenuId, int recipeId, int weekDay, BigInteger quantity, boolean status) {
        this(weekMenuId);
        setId(id);
        setRecipe(recipeId);
        setWeekDay(weekDay);
        setQuantity(quantity);
        setStatus(status);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getWeekMenuId() {
        return weekMenuId;
    }

    public void setWeekMenuId(int weekMenuId) {
        this.weekMenuId = weekMenuId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    private void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public BigDecimal getRecipePrice() {
        return recipePrice;
    }

    private void setRecipePrice(BigDecimal recipePrice) {
        this.recipePrice = recipePrice;
    }

    public String getWeekDay() {
        return this.weekDay.getName();
    }
    public int getWeekDayNumber() {
        return this.weekDay.getDayNumber();
    }
    public void setWeekDay(int weekDay) {
        this.weekDay = WeekDaysLt.getById(weekDay);
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Recipes getRecipe() {
        return recipe;
    }

    private void setRecipe(int recipeId) {
        SqlConnection.getObjectById(recipeId, this.recipe);
        setRecipeName(recipe.getName());
        setRecipePrice(recipe.getPrice());
    }

    @Override
    public void saveToDatabase() {
        SqlConnection.updateDatabase(
                SqlStatement.INSERT_WEEK_MENU_RECIPE,
                getWeekMenuId(),
                this.recipe.getId(),
                getWeekDayNumber(),
                getQuantity()
        );
    }

    @Override
    public void updateDatabase() {
        SqlConnection.updateDatabase(
                SqlStatement.UPDATE_WEEK_MENU_RECIPE,
                getWeekMenuId(),
                recipe.getId(),
                weekDay.getDayNumber(),
                getQuantity(),
                isStatus(),
                getId()
        );
    }

    @Override
    public void removeFromDatabase() {
        setStatus(false);
        updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {

    }

    @Override
    public String getObjectSqlStatement() {
        return null;
    }

}
