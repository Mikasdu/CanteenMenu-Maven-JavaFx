package lt.mikasdu;

import lt.mikasdu.ui.sqlConnection.*;
import lt.mikasdu.ui.sqlConnection.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCategories implements Database {
    private int id;
    private String name;
    private Boolean status;

    public ProductCategories(int id, String name) {
        setId(id);
        setName(name);
        setStatus(true);
    }
    public ProductCategories(String name) {
         setName(name);
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public void saveToDatabase() {
        SqlStatement sql = SqlStatement.INSERT_PRODUCT_CATEGORY;
        SqlConnection.updateDatabase(sql, this.getName());
    }
    @Override
    public void updateDatabase() {
        SqlStatement sql = SqlStatement.UPDATE_PRODUCT_CATEGORY;
        SqlConnection.updateDatabase(sql, this.getName(), this.getStatus(), this.getId());
    }
    public void removeFromDatabase() {
        this.setName(this.getName() + "(nebenaudojamas)");
        this.setStatus(false);
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
