package lt.mikasdu;

import javafx.scene.control.TextFormatter;

import java.util.regex.Pattern;

public class Formatter {

    private static Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");

    public static TextFormatter<Object> formatNumbers() {
        return new TextFormatter<>(change ->
                pattern.matcher(change.getControlNewText()).matches() ? change : null);
    }

}
