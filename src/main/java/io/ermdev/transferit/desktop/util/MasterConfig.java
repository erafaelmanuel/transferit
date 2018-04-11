package io.ermdev.transferit.desktop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MasterConfig {

    final private ClassLoader classLoader = getClass().getClassLoader();

    public Property getUserConfig() throws PropertyException {
        try {
            final Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("config/application.properties"));

            final String path = properties.getProperty("app.user_config.path");
            final String name = properties.getProperty("app.user_config.name");
            final File file = new File(path + name);
            if (file.exists()) {
                properties.clear();
                properties.load(new FileInputStream(file));
                String dir = properties.getProperty("app.dir");
                int port = Integer.parseInt(properties.getProperty("app.port", "0"));
                final Property property = new Property();
                property.setDir(dir);
                property.setPort(port);
                property.setCover(0);
                return property;
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
            int cover = Integer.parseInt(properties.getProperty("app.user_config.cover"));

            final Property property = new Property();
            property.setDir(dir);
            property.setPort(port);
            property.setCover(cover);
            return property;
        } catch (Exception e) {
            final Property property = new Property();
            property.setDir("files");
            property.setPort(1);
            property.setCover(0);
            return property;
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

    public int getConverOrRandom() {
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

    public void saveDir(String dir) {
        try {
            final Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("config/application.properties"));

            final File path = new File(properties.getProperty("app.user_config.path"));
            final File file = new File(path + properties.getProperty("app.user_config.name"));
            if ((path.exists() || path.mkdirs()) && (file.exists() || file.createNewFile())) {
                properties.clear();
                properties.load(new FileInputStream(file));
                properties.put("app.dir", dir);
                properties.store(new FileOutputStream(file), "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePort(int port) {
        try {
            final Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("config/application.properties"));

            final File path = new File(properties.getProperty("app.user_config.path"));
            final File file = new File(path + properties.getProperty("app.user_config.name"));
            if ((path.exists() || path.mkdirs()) && (file.exists() || file.createNewFile())) {
                properties.clear();
                properties.load(new FileInputStream(file));
                properties.put("app.port", String.valueOf(port));
                properties.store(new FileOutputStream(file), "");
            }
        } catch (IOException e) {
            e.printStackTrace();
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

        private int cover;

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

        public int getCover() {
            return cover;
        }

        public void setCover(int cover) {
            this.cover = cover;
        }
    }
}
