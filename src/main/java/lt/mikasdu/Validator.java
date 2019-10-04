package lt.mikasdu;

import java.io.File;

public class Validator {
    public static boolean stringValid(String text, int min, int max) {
            return (text != null && text.length() >= min && text.length() <= max);
    }

    public static boolean directoryExists(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }
}
