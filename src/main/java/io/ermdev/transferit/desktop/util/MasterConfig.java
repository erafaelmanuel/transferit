package io.ermdev.transferit.desktop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MasterConfig {

    final private ClassLoader classLoader = getClass().getClassLoader();

    public Property getUserConfig() throws PropertyException {
        final Properties properties = new Properties();
        final Property property = new Property();
        try {
            properties.load(classLoader.getResourceAsStream("config/application.properties"));

            final String path = properties.getProperty("app.user_config.path");
            final String name = properties.getProperty("app.user_config.name");
            final File file = new File(path + name);
            if (file.exists()) {
                properties.clear();
                properties.load(new FileInputStream(file));
                try {
                    property.setDir(properties.getProperty("app.dir"));
                } catch (Exception e) {
                    property.setDir("files");
                }
                try {
                    property.setPort(Integer.parseInt(properties.getProperty("app.port")));
                } catch (Exception e) {
                    property.setPort(1);
                }
                try {
                    property.setCover(Integer.parseInt(properties.getProperty("app.cover")));
                } catch (Exception e) {
                    property.setCover(-1);
                }
                return property;
            } else {
                throw new PropertyException("No user config found!");
            }
        } catch (Exception e) {
            throw new PropertyException("No user config found!");
        }
    }

    public Property getDefaultConfig() {
        final Properties properties = new Properties();
        final Property property = new Property();
        try {
            properties.load(classLoader.getResourceAsStream("config/application.properties"));
            try {
                property.setDir(properties.getProperty("app.dir"));
            } catch (Exception e) {
                property.setDir("files");
            }
            try {
                property.setPort(Integer.parseInt(properties.getProperty("app.port")));
            } catch (Exception e) {
                property.setPort(1);
            }
            try {
                property.setCover(Integer.parseInt(properties.getProperty("app.cover")));
            } catch (Exception e) {
                property.setCover(-1);
            }
            return property;
        } catch (Exception e) {
            property.setDir("files");
            property.setPort(1);
            property.setCover(-1);
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

    public int getCoverOrRandom() {
        try {
            final int cover = getUserConfig().getCover();
            if (cover > -1) {
                return cover;
            } else {
                return (int) ((Math.random() * 5));
            }
        } catch (PropertyException e) {
            return (int) ((Math.random() * 5));
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
