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
        assertFalse(Validator.stringValid("", 0, 0));
    }

    @Test
    public void validatorStringValidMinMax() {
        assertFalse(Validator.stringValid("asdafa", 5, 2));
    }

}