package lt.mikasdu;

public class Validator {

    public static boolean stringValid(String text, int min, int max) {
        return (text != null && text.length() >= min && text.length() <= max);
    }
}
