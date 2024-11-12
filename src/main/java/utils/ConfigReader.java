package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
	public static String getProperty(String key) {
	    String value = properties.getProperty(key);
	    if (value == null) {
	        throw new RuntimeException("Key " + key + " not found in configuration file.");
	    }
	    return value;
	}
	
 public static String getCheckoutDataFilePath() {
    String relativePath = properties.getProperty("checkoutDataFile");
    System.out.println("File Path: " + relativePath);

    return Paths.get(System.getProperty("user.dir"), relativePath).toString();

}
 
}