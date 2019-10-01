package lt.mikasdu;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductCategoriesTest {

    @Test
    public void createEmptyProductCategory() {
        ProductCategories prCat = new ProductCategories();
        assertEquals(0, prCat.getId());
    }

}