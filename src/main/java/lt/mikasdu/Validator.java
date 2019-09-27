package lt.mikasdu;

import java.io.File;

public class Validator {
    public static boolean stringValid(String text, int min, int max) {
        if (min <= max && max > 0 && min > 0)
            return (text != null && text.length() >= min && text.length() <= max);
        else return false;
    }

    public static boolean directoryExists(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }
}
