package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties = null;

    static {
        try {
            properties = new Properties();
            File[] files = new File("src/main/resources").listFiles();
            for (File file : files) {
                if (file.getName().endsWith("properties")) {
                    properties.load(new FileInputStream(file));
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertiesByKey(String key) {
        return properties.getProperty(key);
    }
}
