package lt.mikasdu;

import lt.mikasdu.ui.sqlConnection.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Recipes implements Database {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isActive;

    public Recipes() {
        this.isActive = true;
    }
    public Recipes(int id, String name, String description, BigDecimal price, boolean isActive) {
        setId(id);
        setName(name);
        setDescription(description);
        setPrice(price);
        setActive(isActive);
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getPrice() + " €";
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        BigDecimal val = price.setScale(2, RoundingMode.HALF_EVEN);
        this.price = val;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        if(isActive){
            this.name = this.name.replaceAll("\\(nenaudojamas\\)","");
        } else if (!name.contains("(nenaudojamas)")){
            this.name += "(nenaudojamas)";
        }
        this.isActive = isActive;
    }



    @Override
    public void saveToDatabase() {
        SqlStatement sql = SqlStatement.INSERT_RECIPE;
        SqlConnection.updateDatabase(sql, this.getName(), this.getDescription(), this.getPrice());
    }

    @Override
    public void updateDatabase() {
        SqlStatement sql = SqlStatement.UPDATE_RECIPES;
        SqlConnection.updateDatabase(sql,
                this.getName(),
                this.getDescription(),
                this.getPrice(),
                this.isActive(),
                this.getId()
        );
    }

    @Override
    public void removeFromDatabase() {
        setName(this.name + "(nebenaudojamas)");
        setActive(false);
        updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("Id"));
        this.setName(resultSet.getString("Name"));
        this.setDescription(resultSet.getString("Description"));
        this.setPrice(new BigDecimal(resultSet.getString("Price")));
        this.setActive(resultSet.getBoolean("Status"));
    }

    @Override
    public String getObjectSqlStatement() {
        return SqlStatement.RECIPE_BY_ID.getStatement();
    }

}
