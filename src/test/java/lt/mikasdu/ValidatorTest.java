package lt.mikasdu;


import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {


    @Test
    public void validatorStringValidNull() {
        assertFalse(Validator.stringValid(null, 1, 2));
    }

    @Test
    public void validatorStringValidEmpty() {
        assertTrue(Validator.stringValid("", 0, 0));
    }

    @Test
    public void validatorStringValidMinMax() {
        assertFalse(Validator.stringValid("asdafa", 5, 2));
    }

    @Test
    public void validatorNotExistingDirectory() {
        assertFalse(Validator.directoryExists("src\\main\\java\\nonExistingDirectory"));
    }

    @Test
    public void validatorExistingDirectory() {
        assertTrue(Validator.directoryExists("src\\main\\java"));
    }

    @Test
    public void validatorExistingFileNotAsDirectory() {
        assertFalse(Validator.directoryExists("src\\main\\java\\lt\\mikasdu\\App.java"));
    }

}