package lt.mikasdu;

import lt.mikasdu.ui.sqlConnection.*;
import lt.mikasdu.ui.sqlConnection.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCategories implements Database {
    private int id;
    private String name;
    private boolean status;

    public ProductCategories() {
        this.id = 0;
        this.name = "";
        this.status = true;
    }

    public ProductCategories(int id, String name) {
        setId(id);
        setName(name);
        setStatus(true);
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        setNameOnStatusChange();
    }
    private void setNameOnStatusChange(){
        if (!status && !name.contains("(nenaudojamas)")) {
            setName(getName() + "(nenaudojamas)");
        } else if (status) {
            setName(getName().replaceAll("\\(nenaudojamas\\)",""));
        }
    }

    @Override
    public void saveToDatabase() {
        SqlStatement sql = SqlStatement.INSERT_PRODUCT_CATEGORY;
        SqlConnection.updateDatabase(sql, getName());
    }
    @Override
    public void updateDatabase() {
        SqlStatement sql = SqlStatement.UPDATE_PRODUCT_CATEGORY;
        SqlConnection.updateDatabase(sql, getName(), getStatus(), getId());
    }
    public void removeFromDatabase() {
        setStatus(false);
        updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {
        setId(resultSet.getInt("Id"));
        setName(resultSet.getString("Name"));
        setStatus(resultSet.getBoolean("Status"));
    }

    @Override
    public String getObjectSqlStatement() {
        return SqlStatement.PRODUCT_CATEGORY_BY_ID.getStatement();
    }


}
