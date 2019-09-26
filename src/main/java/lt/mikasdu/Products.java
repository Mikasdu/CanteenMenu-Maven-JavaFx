package lt.mikasdu;


import lt.mikasdu.ui.sqlConnection.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Products implements Database {
    private int id;
    private String name;
    private String measure;
    private String description;
    private ProductCategories categories;
    private BigDecimal quantity;
    private boolean status;



    public Products() {
        this.setName("New");
    }

    public Products(int id, String name, String measure, String description, int productCategoryId) {
        setId(id);
        setName(name);
        setMeasure(measure);
        setDescription(description);
        setCategories(productCategoryId);
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

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategories() {
        return categories.getName();
    }

    public int getCategoriesId() {
        return categories.getId();
    }

    public void setCategories(int productCategoryId) {
        String productCategoryName = SqlConnection.getProductCategory(productCategoryId);
        this.categories = new ProductCategories(productCategoryId, productCategoryName);
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @Override
    public void saveToDatabase() {
        SqlStatement sql = SqlStatement.INSERT_PRODUCT;
        SqlConnection.updateDatabase(sql, this.getName(), this.getMeasure(), this.getDescription(), this.getCategoriesId());
    }

    @Override
    public void updateDatabase() {
        SqlStatement sql = SqlStatement.UPDATE_PRODUCT;
        SqlConnection.updateDatabase(sql,
                this.getName(),
                this.getMeasure(),
                this.getDescription(),
                this.getCategoriesId(),
                this.getStatus(),
                this.getId()
        );
    }
    @Override
    public void removeFromDatabase() {
        this.setName(this.getName() + "(nebenaudojamas)");
        this.setStatus(false);
        updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt("Id"));
        this.setName(resultSet.getString("Name"));
        this.setMeasure(resultSet.getString("Measure"));
        this.setDescription(resultSet.getString("Description"));
        this.setCategories(resultSet.getInt("Category"));
        this.setStatus(resultSet.getBoolean("Status"));
    }

    @Override
    public String getObjectSqlStatement() {
        return SqlStatement.PRODUCT_BY_ID.getStatement();
    }

}
