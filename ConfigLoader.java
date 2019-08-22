package config;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {

    public static void main(String[] args) {
        if (args.length > 0) {
            String path = args[0];
            Config config = ConfigLoader.get(path);
            System.out.println(config.readProp());
            System.out.println(config.setProp("new", "144").save());
        }
    }

    public static Config get(String path) {
        return new Config(path);
    }

    public static class Config {
        private final String path;
        private Properties prop;

        public Config(String path) {
            this.path = path;
            prop = loadProp(path);
        }

        public Properties readProp() {
            return this.prop;
        }

        public Config setProp(String key, String val) {
            this.prop.setProperty(key, val);
            return this;
        }

        public boolean save() {
            try (OutputStream output = new FileOutputStream(path)) {
                prop.store(output, null);
                System.out.println(prop);
                return true;
            } catch (IOException io) {
                io.printStackTrace();
                return false;
            }
        }

        public Properties loadProp(String path) {
//            try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(path)) {
            try (InputStream input = new FileInputStream(path)) {
                Properties prop = new Properties();
                if (input == null) {
                    System.out.println("Sorry, unable to find config.properties");
                    return null;
                } else {
                    //load a properties file from class path, inside static method
                    prop.load(input);
                }
                return prop;
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }


    }

}
