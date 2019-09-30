package lt.mikasdu;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Formatter {

    private static Pattern doubleWithTwoDecimals = Pattern.compile("[0-9]{0,3}[.]?[0-9]{0,2}");
    private static Pattern doubleWithThreeDecimals = Pattern.compile("[0-9]{0,3}[.]?[0-9]{0,3}");
    private static Pattern integerNotNull = Pattern.compile("[1-9]?[0-9]{0,5}");
    private static String datePattern = "yyyy-MM-dd";

    public static TextFormatter<Object> formatDecimalQuantityNumbers() {
        return formatDecimalNumbers(doubleWithThreeDecimals);
    }

    public static TextFormatter<Object> formatDecimalPriceNumbers() {
        return formatDecimalNumbers(doubleWithTwoDecimals);
    }

    private static TextFormatter<Object> formatDecimalNumbers(Pattern doubleWithTwoDecimals) {
        return new TextFormatter<>(change ->
                doubleWithTwoDecimals.matcher(change.getControlNewText()).matches() ? change : null);
    }

    public static TextFormatter<Object> formatIntegerNumbers() {
        return new TextFormatter<>(change ->
                integerNotNull.matcher(change.getControlNewText()).matches() ? change : null);
    }


    public static StringConverter<LocalDate> formatDate() {
        return new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateFormatter.format(object);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }


}
