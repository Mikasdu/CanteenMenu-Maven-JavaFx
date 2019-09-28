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
    private Properties configProps = new Properties();
    private String propAppName = "appName";
    private String propUserName = "userName";
    private String propFilesPath = "filePath";
    private String propFullScreen = "fullScreen";
    private String propAppHeight = "appHeight";
    private String propAppWidth = "appWidth";


    public Settings() {
        this.setAppName();
        this.setUserName("Dienos Pietūs");
        this.setFilesPath(System.getProperty("user.home") + "\\Documents\\" + this.getAppName());
        this.setFullScreen(false);
        this.setAppWidth("800");
        this.setAppHeight("600");

        this.loadConfigFile();
    }

    private void loadConfigFile() {
        Path path = Paths.get(CONFIG_FILE_PATH);
        if (configFile.exists()) {
            try {
                InputStream inputStream = new FileInputStream(configFile);
                configProps.load(inputStream);
                inputStream.close();
            } catch (IOException e) {
                System.err.println("Failed to read config file" + e.getMessage());
            }
        } else {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println("Path or file error: " + e.getMessage());
            }
        }
    }

    public String getFilesPath() {
        return this.configProps.getProperty(propFilesPath);
    }

    public void setFilesPath(String path) {
        this.configProps.setProperty(propFilesPath, path);
    }

    public String getUserName() {
        return this.configProps.getProperty(propUserName);
    }

    public void setUserName(String newUserName) {
        this.configProps.setProperty(propUserName, newUserName);
    }

    public String getAppName() {
        return this.configProps.getProperty(propAppName);
    }

    private void setAppName() {
        this.configProps.setProperty(propAppName, "Canteen Menu " + Calendar.getInstance().get(Calendar.YEAR));
    }

    public void setFullScreen(boolean bool) {
        this.configProps.setProperty(propFullScreen, String.valueOf(bool));
    }

    public boolean isFullScreen() {
        return this.configProps.getProperty(propFullScreen).equals("true");
    }

    public void setAppWidth(String newAppWidth) {
        this.configProps.setProperty(propAppWidth, newAppWidth);
    }

    public String getAppWidth() {
        return this.configProps.getProperty(propAppWidth);
    }

    public void setAppHeight(String newAppHeight) {
        this.configProps.setProperty(propAppHeight, newAppHeight);
    }

    public String getAppHeight() {
        return this.configProps.getProperty(propAppHeight);
    }

    public void saveConfigFile() {
        try {
            OutputStream outputStream = new FileOutputStream(configFile);
            configProps.store(outputStream, "App settings");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
