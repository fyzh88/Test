package com.ydhdj.fyzh.utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Common configuration manager <br>
 * 
 * <p>
 * Create on : 2014-04-14<br>
 * <p>
 * </p>
 * <br>
 * .
 * 
 * @author yuj-b@grandsoft.com.cn<br>
 * @version glw_core v1.0
 *          <p>
 *          <br>
 *          <strong>Modify History:</strong><br>
 *          user modify_date modify_content<br>
 *          -------------------------------------------<br>
 *          <br>
 */
public class DefaultConfiguration {

    private static final String PATTERN_SEP = "\\s*{0}\\s*";
    private String name;
    protected Properties properties = new Properties();

    /**
     * Constructors.
     * 
     * @param propertiesName
     *            Properties file name
     */
    public DefaultConfiguration(String propertiesName) {
        name = propertiesName;
    }

    /**
     * Constructors.
     * 
     * @param propertiesName
     *            Properties file name
     * @param load
     *            Is load the properties file.
     * @throws FileNotFoundException
     *             the file not found exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public DefaultConfiguration(String propertiesName, boolean load) throws FileNotFoundException, IOException {
        name = propertiesName;
        if (load) {
            load();
        }
    }

    /**
     * Get a attribute value.
     * 
     * @param key
     *            Key
     * @param defaultValue
     *            Default value
     * @return the string
     */
    public String get(String key, String defaultValue) {
        return get(key) == null ? defaultValue : get(key);
    }

    /**
     * Get a attribute values of array.
     * 
     * @param key
     *            Key
     * @param separator
     *            Separator
     * @param defaultValue
     *            Default value
     * @return the array
     */
    public String[] getArray(String key, char separator, String[] defaultValue) {
        if (get(key) == null) {
            return defaultValue;
        }
        return get(key).split(MessageFormat.format(PATTERN_SEP, separator));
    }

    /**
     * Get a attribute value of {@link Boolean}.
     * 
     * @param key
     *            Key
     * @param defaultValue
     *            Default value
     * @return the boolean
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        if (get(key) == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(get(key));
    }

    /**
     * Get a attribute value of integer.
     * 
     * @param key
     *            Key
     * @param defaultValue
     *            Default value
     * @return the int
     */
    public int getInt(String key, int defaultValue) {
        if (get(key) == null) {
            return defaultValue;
        }
        return Integer.parseInt(get(key));
    }

    /**
     * Get a attribute values of {@link Map}.
     * 
     * @param key
     *            Key
     * @param separator
     *            level 1 separator
     * @param separator2
     *            level 2 separator
     * @param defaultValue
     *            Default value
     * @return the map
     */
    public Map<String, String> getMap(String key, char separator, char separator2, Map<String, String> defaultValue) {
        if (get(key) == null) {
            return defaultValue;
        }
        String[] elems = getArray(key, separator, null);
        Map<String, String> map = new HashMap<String, String>();
        if (elems != null) {
            for (String elem : elems) {
                String[] _arr = elem.split(MessageFormat.format(PATTERN_SEP, separator2));
                map.put(_arr[0], _arr[1]);
            }
            return map;
        }
        return null;
    }

    /**
     * Return a name of the current properties file.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Load the properties file into memory.
     * 
     * @return {@link DefaultConfiguration} instance
     * @throws FileNotFoundException
     *             the file not found exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public DefaultConfiguration load() throws FileNotFoundException, IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        if (in == null)
            throw new FileNotFoundException(name);
        properties.load(in);
        return this;
    }

    /**
     * Get a attribute value.
     * 
     * @param key
     * @return
     */
    protected String get(String key) {
        return properties.getProperty(key);
    }
}
