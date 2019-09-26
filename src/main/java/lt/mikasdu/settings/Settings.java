package lt.mikasdu.settings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Properties;

public class Settings {
    private final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "\\src\\settings\\config.properties";
    private File configFile = new File(CONFIG_FILE_PATH);
    public Properties configProps = new Properties();

    public Settings() {
        this.loadSettings();
    }

    private void loadSettings() {
        Path path = Paths.get(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println("Path or file error: " + e.getMessage());
            }
        }
        loadConfigFile();
    }

    private void defaultConfigProperties() {
        configProps.setProperty("appName", "Dienos Pietūs " + Calendar.getInstance().get(Calendar.YEAR));
        configProps.setProperty("userName", "Dienos Pietūs");
        configProps.setProperty("filePath",
                System.getProperty("user.home") + "\\Documents\\" + configProps.getProperty("userName"));
        configProps.setProperty("fullScreen", "false");
        configProps.setProperty("appWidth", "800");
        configProps.setProperty("appHeight", "600");
    }

    private void loadConfigFile() {
        defaultConfigProperties();
        try {
            InputStream inputStream = new FileInputStream(configFile);
            configProps.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            System.err.println("Failed to read config file" + e.getMessage());
        }
    }

    private void checkRepairConfiguration() {
        //configProps.getProperty("name").isEmpty();
    }
    //todo return values by method
    private void saveConfigFile() {
        try {
            OutputStream outputStream = new FileOutputStream(configFile);
            configProps.store(outputStream, "App settings");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. Sukuriamas Settings objektas
     * 2. Patikrina ar failas yra jei nera sukuria !
     * 3. Sukuria @configProperies su default reiksmem, tuo atveju jei faile nebutu nustatyta
     * 4. uzkrauna faila jeigu yra
     * 5. Patikrina visas properties per nauja, nustatyti default kur blogai faile buvo
     * 6. Patikrinti ar failu direktorija yra, jei ne sukurti
     * <p>
     * <p>
     * 2. Nustatomi default laukai @configProperties
     * 2. Patikrina ar yra settings failas jei nera sukuria
     * 3. Uzloadina settings failo duomenis i config properties
     * 4. Patikrina ar visi settingsai nustatyti teistingai jei ne nustato i default ismeta error.
     * Kad nebutu galima pakeisti isoriskai, isaugoma i faila viskas is @configProps
     */


    private void setDefaultSettings() {

    }

    public void loadProperties() throws IOException {
//        Properties defaultProps = new Properties();
//
//        // sets default properties
//        defaultProps.setProperty("host", "www.codejava.net");
//        defaultProps.setProperty("port", "3306");
//        defaultProps.setProperty("user", "root");
//        defaultProps.setProperty("pass", "secret");
//
//        configProps = new Properties(defaultProps);
//
//        // loads properties from file
//        InputStream inputStream = new FileInputStream(configFile);
//        configProps.load(inputStream);
//        inputStream.close();
    }


}
