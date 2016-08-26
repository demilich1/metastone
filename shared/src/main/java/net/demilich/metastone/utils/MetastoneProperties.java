package net.demilich.metastone.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read and write from the Metastone properties file.
 */
public class MetastoneProperties {
    private static final String METASTONE_PROPERTIES_FILE = UserHomeMetastone.getPath() + File.separator + "metastone.properties";
    private static MetastoneProperties INSTANCE;
    private static Logger logger = LoggerFactory.getLogger(MetastoneProperties.class);
    private final File propertiesFile;
    private final Properties prop = new Properties();

    private  MetastoneProperties(String propertiesFilePath) throws IOException {
        propertiesFile = new File(propertiesFilePath);
        if (!propertiesFile.exists()) {
            // ensure that the metastone.properties file exists
            try {
                propertiesFile.createNewFile();
            } catch (IOException e) {
                logger.error("Could not create property file: " +  propertiesFilePath);
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * Searches for the property with the specified key within the Metastone properties file.
     * If the key is not found in the property file, the method returns {@code null}.
     *
     * @param   key   the property key.
     * @return  the value in the Metastone properties file with the specified key value.
     * @throws  IOException if the Metastone properties file cannot be loaded.
     */
    public static String getProperty(String key) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new MetastoneProperties(METASTONE_PROPERTIES_FILE);
        }
        // ensure we have the latest properties values loaded
        INSTANCE.load();
        return INSTANCE.prop.getProperty(key);
    }

    /**
     * Convenience method to parse the value at the given key to a boolean value
     * by calling Boolean.parseBoolean(getProperty(key))
     *
     * @param key the property key
     * @return the boolean value in the Metastone properties file with the specified key.
     * @throws IOException if the Metastone properties file cannot be loaded.
     */
    public static boolean getBoolean(String key) throws IOException {
        return Boolean.parseBoolean(getProperty(key));
    }
    
    /**
     * Convenience method to parse the value at the given key to a int value
     * by calling Integer.parseInt(getProperty(key))
     *
     * @param key the property key
     * @param defaultValue the value which is returned when the key is not present
     * @return the int value in the Metastone properties file with the specified key.
     * @throws IOException if the Metastone properties file cannot be loaded.
     */
    public static int getInt(String key, int defaultValue) throws IOException {
    	String propertyValue = getProperty(key);
    	if (propertyValue == null) {
    		return defaultValue;
    	}
    	return Integer.parseInt(propertyValue);
    }
    
    /**
     * Set the value for the given property key.
     *
     * @param key the key to be placed into this property list.
     * @param value the value corresponding to <tt>key</tt>.
     * @return     the previous value of the specified key in the Metastone properties file
     *             or {@code null} if it did not have one.
     * @throws  IOException if the Metastone properties file cannot be written to.
     */
    public static synchronized Object setProperty(String key, String value) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new MetastoneProperties(METASTONE_PROPERTIES_FILE);
        }

        // ensure we have the latest properties values loaded
        INSTANCE.load();
        // set the new properties value for the given key
        Object previousValue = INSTANCE.prop.setProperty(key, value);
        // write out the properties file to disk
        INSTANCE.store();

        return previousValue;
    }

    /**
     * Convenience method to set the boolean value for the given property key.
     *
     * @param key the key to be placed into this property list.
     * @param value the boolean value corresponding to <tt>key</tt>.
     * @return     the previous value of the specified key in the Metastone properties file
     *             or {@code null} if it did not have one.
     * @throws  IOException if the Metastone properties file cannot be written to.
     */
    public static Object setBoolean(String key, boolean value) throws IOException {
        return setProperty(key, Boolean.valueOf(value).toString());
    }

    private void load() throws IOException {
        FileInputStream input = new FileInputStream(propertiesFile);

        // load properties file
        prop.load(input);
        input.close();
    }

    private void store() throws IOException {
        FileOutputStream output = new FileOutputStream(propertiesFile);

        // write properties file
        prop.store(output, null);
        output.close();
    }
}
