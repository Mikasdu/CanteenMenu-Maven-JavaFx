package lt.mikasdu;

import lt.mikasdu.ui.sqlConnection.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeProduct implements Database {
    private int id;
    private int recipeId;
    private int productId;
    private BigDecimal quantity;
    private boolean status;
    private Products product;

    private String productName;
    private String productMeasure;
    private String productDescription;
    private String productCategory;

    private RecipeProduct(int productId) {
        this.setProductId(productId);
        this.product = new Products(); //perziureti Products konstruktoriu

    }

    public RecipeProduct(int id, int recipeId, int productId, BigDecimal quantity) {
        this(productId);
        setId(id);
        setRecipeId(recipeId);
        setQuantity(quantity);
        setStatus(true);
        setProduct(this.productId);

        this.productName = this.product.getName();
        this.productMeasure = this.product.getMeasure();
        this.productDescription = this.product.getDescription();
        this.productCategory = this.product.getCategories();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        BigDecimal val = quantity.setScale(3, RoundingMode.HALF_EVEN);
        this.quantity = val;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductMeasure() {
        return productMeasure;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(int id) {
        SqlConnection.getObjectById(id, this.product);
    }


    @Override
    public void saveToDatabase() {
        SqlStatement sql = SqlStatement.INSERT_RECIPE_PRODUCT;
        SqlConnection.updateDatabase(sql,
                this.getRecipeId(),
                this.getProductId(),
                this.getQuantity()
        );
    }

    @Override
    public void updateDatabase() {
        SqlStatement sql = SqlStatement.UPDATE_RECIPE_PRODUCTS;
        SqlConnection.updateDatabase(sql,
                this.getRecipeId(),
                this.getProductId(),
                this.getQuantity(),
                this.getStatus(),
                this.getId()
        );
    }

    @Override
    public void removeFromDatabase() {
        this.setStatus(false);
        this.updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {

    }

    @Override
    public String getObjectSqlStatement() {
        return null;
    }


}
