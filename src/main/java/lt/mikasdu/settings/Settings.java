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

    private String propUserName = "userName";
    private String propFilesPath = "filePath";
    private String propFullScreen = "fullScreen";
    private String propAppHeight = "appHeight";
    private String propAppWidth = "appWidth";


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

    public String getUserName() {
        return this.configProps.getProperty(propUserName);
    }

    public String getFilesPath() {
        return this.configProps.getProperty(propFilesPath);
    }

    public boolean isFullScreen() {
        return this.configProps.getProperty(propFullScreen).equals("true");

    }

    public void setAppWidth(String newAppWidth) {
        configProps.setProperty(propAppWidth, newAppWidth);
    }

    public String getAppWidth() {
        return this.configProps.getProperty(propAppWidth);
    }

    public String getAppHeight() {
        return this.configProps.getProperty(propAppHeight);
    }

    private void defaultConfigProperties() {
        configProps.setProperty("appName", "Canteen Menu " + Calendar.getInstance().get(Calendar.YEAR));
        configProps.setProperty(propUserName, "Dienos Pietūs");
        configProps.setProperty(propFilesPath,
                System.getProperty("user.home") + "\\Documents\\" + configProps.getProperty("userName"));
        configProps.setProperty(propFullScreen, "false");
        configProps.setProperty(propAppWidth, "800");
        configProps.setProperty(propAppHeight, "600");

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
