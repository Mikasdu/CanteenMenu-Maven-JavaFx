package lt.mikasdu;

import javafx.scene.control.TextFormatter;

import java.util.regex.Pattern;

public class Formatter {

    private static Pattern doubleWithTwoDecimals = Pattern.compile("[0-9]{1,3}[.]*[0-9]{0,2}");

    public static TextFormatter<Object> formatDecimalNumbers() {
        return new TextFormatter<>(change ->
                doubleWithTwoDecimals.matcher(change.getControlNewText()).matches() ? change : null);
    }

}
