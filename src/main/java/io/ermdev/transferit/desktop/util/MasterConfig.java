package io.ermdev.transferit.desktop.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class MasterConfig {

    final private ClassLoader classLoader = getClass().getClassLoader();

    public Property getUserConfig() throws PropertyException {
        try {
            final Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("config/application.properties"));
            File file = new File(properties.getProperty("app.user_config"));
            if (file.exists()) {
                properties.clear();
                properties.load(new FileInputStream(file));
                String dir = properties.getProperty("app.dir");
                int port = Integer.parseInt(properties.getProperty("app.port", "0"));
                return new Property(dir, port);
            } else {
                throw new PropertyException("No user config found!");
            }
        } catch (Exception e) {
            throw new PropertyException("No user config found!");
        }
    }

    public Property getDefaultConfig() {
        try {
            final Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("config/application.properties"));
            String dir = properties.getProperty("app.dir");
            int port = Integer.parseInt(properties.getProperty("app.port"));
            return new Property(dir, port);
        } catch (Exception e) {
            return new Property("files", 1);
        }
    }

    public String getDirOrDefault() {
        try {
            String dir = getUserConfig().getDir();
            if (dir != null && !dir.isEmpty()) {
                return dir;
            } else {
                return getDefaultConfig().getDir();
            }
        } catch (PropertyException e) {
            return getDefaultConfig().getDir();
        }
    }

    public int getPortOrDefault() {
        try {
            int port = getUserConfig().getPort();
            if (port > 0 && port <= 65535) {
                return port;
            } else {
                return getDefaultConfig().getPort();
            }
        } catch (PropertyException e) {
            return getDefaultConfig().getPort();
        }
    }

    public class PropertyException extends Exception {

        PropertyException(String message) {
            super(message);
        }
    }

    public class Property {

        private String dir;

        private int port;

        public Property(String dir, int port) {
            this.dir = dir;
            this.port = port;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
