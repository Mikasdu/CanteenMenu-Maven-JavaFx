package lt.mikasdu;


import lt.mikasdu.ui.sqlConnection.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Products implements Database {
    private int id;
    private String name;
    private MeasureUnit measure;
    private String description;
    private ProductCategories categories;
    private BigDecimal quantity;
    private boolean isActive;

    public Products() {
        this.name = "";
        isActive = true;
    }

    public Products(int id, String name, int measure, String description, int productCategoryId) {
        this.id = id;
        this.name = name;
        setMeasure(measure);
        this.description = description;
        setCategories(productCategoryId);
        setActive(true);
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
        return this.measure.getName();
    }

    public MeasureUnit getMeasureObj() {
        return this.measure;
    }

    public void setMeasure(int measure) {
        this.measure = MeasureUnit.getById(measure);
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
        SqlStatement sql = SqlStatement.INSERT_PRODUCT;
        SqlConnection.updateDatabase(sql, getName(), this.measure.getId(), getDescription(), getCategoriesId());
    }

    @Override
    public void updateDatabase() {
        SqlStatement sql = SqlStatement.UPDATE_PRODUCT;
        SqlConnection.updateDatabase(sql,
                getName(),
                this.measure.getId(),
                getDescription(),
                getCategoriesId(),
                isActive(),
                getId()
        );
    }

    @Override
    public void removeFromDatabase() {
        setActive(false);
        updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {
        setId(resultSet.getInt("Id"));
        setName(resultSet.getString("Name"));
        setMeasure(resultSet.getInt("Measure"));
        setDescription(resultSet.getString("Description"));
        setCategories(resultSet.getInt("Category"));
        setActive(resultSet.getBoolean("Status"));
    }

    @Override
    public String getObjectSqlStatement() {
        return SqlStatement.PRODUCT_BY_ID.getStatement();
    }

}
