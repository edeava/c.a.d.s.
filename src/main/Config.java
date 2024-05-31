package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "./res/app.properies";
    public static final String EXTENSION = ".rix";
    public static final long EXPLORER_TIMEOUT;
    public static final int MAX_FILE_CHUNK;
    public static final int MAX_ROW_SIZE;
    public static final String START_DIR;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
            MAX_FILE_CHUNK = Integer.parseInt(properties.getProperty("maximum_file_chunk_size", "1024"));
            MAX_ROW_SIZE = Integer.parseInt(properties.getProperty("maximum_rows_size", "3"));
            START_DIR = properties.getProperty("start_dir", "/");
            EXPLORER_TIMEOUT = Long.parseLong(properties.getProperty("timeout", "60000"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from " + CONFIG_FILE, e);
        }
    }
}
