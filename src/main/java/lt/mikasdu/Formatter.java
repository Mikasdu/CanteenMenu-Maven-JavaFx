package lt.mikasdu;

import javafx.scene.control.TextFormatter;

import java.util.regex.Pattern;

public class Formatter {

    private static Pattern doubleWithTwoDecimals = Pattern.compile("[0-9]{1,3}[.]*[0-9]{0,2}");
    public static TextFormatter<Object> formatDecimalNumbers() {
        return new TextFormatter<>(change ->
                doubleWithTwoDecimals.matcher(change.getControlNewText()).matches() ? change : null);
    }

    private static Pattern integerNotNull = Pattern.compile("[1-9][0-9]{0,5}");
    public static TextFormatter<Object> formatIntegerNumbers() {
        return new TextFormatter<>(change ->
                integerNotNull.matcher(change.getControlNewText()).matches() ? change : null);
    }

}
