package lt.mikasdu;

import lt.mikasdu.ui.sqlConnection.*;
import lt.mikasdu.ui.sqlConnection.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCategories implements Database {
    private int id;
    private String name;
    private boolean isActive = true;

    public ProductCategories() {
        // this.id = 0;
        this.name = "";
    }

    ProductCategories(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {
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
        SqlStatement sql = SqlStatement.INSERT_PRODUCT_CATEGORY;
        SqlConnection.updateDatabase(sql, getName());
    }
    @Override
    public void updateDatabase() {
        SqlStatement sql = SqlStatement.UPDATE_PRODUCT_CATEGORY;
        SqlConnection.updateDatabase(sql, getName(), getActive(), getId());
    }
    public void removeFromDatabase() {
        setActive(false);
        updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {
        setId(resultSet.getInt("Id"));
        setName(resultSet.getString("Name"));
        setActive(resultSet.getBoolean("Status"));
    }

    @Override
    public String getObjectSqlStatement() {
        return SqlStatement.PRODUCT_CATEGORY_BY_ID.getStatement();
    }


}
