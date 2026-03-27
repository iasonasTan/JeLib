package com.je.io.configuration;

import com.je.core.AlreadyInitializedException;
import com.je.core.JeLib;
import com.je.core.NotInitializedException;
import com.je.core.util.Bundle;
import com.je.io.bundle.BundleIO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Supplies methods that stores/reads files from user's config directory.
 * This class automatically detects the OS the app runs on.<br>
 * <b>WARNING:</b> Clas must be initialized before use.
 * @see #init(String)
 * @see Configuration#createConfigFile(String)
 */
public final class Configuration {
    /**
     * After {@link #init(String)} is called, this field holds the Application's
     * config directory that's inside user's config directory as {@link String}.
     * Before {@link #init(String)} is called, this field holds value {@code null}
     * to flag that the utility is not initialized.
     * @see Configuration#check()
     */
    private static String sConfigDirectory = null;

    /**
     * Initializes utility class.
     * Finds user's config directory based on current OS.
     * Creates application's configuration folder.
     */
    public static void init(String appName) {
        if(sConfigDirectory!=null)
            throw new AlreadyInitializedException();
        Path systemConfigDir;
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        systemConfigDir = switch (osName) {
            case String s when s.contains("win") -> Paths.get(System.getenv("APPDATA"));
            case String s when s.contains("mac") -> Paths.get(userHome, "Library", "Application Support");
            default                              -> Paths.get(userHome, ".config"); // Unix, Linux
        };
        Path configDir = Paths.get(systemConfigDir.toAbsolutePath().toString(), appName);
        if(!Files.exists(configDir) || !Files.isDirectory(configDir)) {
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                throw new UncheckedIOException("Cannot create configuration directory.", e);
            }
        }
        sConfigDirectory = configDir.toAbsolutePath().toString();
    }

    /**
     * Creates {@link OutputStream} from the given path.
     * If file doesn't exist, method creates it.
     * @param path Config file's path as {@link String}.
     * @return {@link OutputStream} from the given path.
     * @see #createConfigFile
     */
    public static OutputStream getConfigOutputStream(String path) {
        check();
        try {
            return Files.newOutputStream(createConfigFile(path));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Creates {@link InputStream} from the given path.
     * If file doesn't exist, method creates it.
     * @param path Config file's path as {@link String}.
     * @param createFile If {@code true}, method will create file if it doesn't exist.
     * @return {@link InputStream} from the given path.
     * @see #createConfigFile(String)
     */
    public static InputStream getConfigInputStream(String path, boolean createFile) {
        check();
        Path out = Paths.get(sConfigDirectory, path);
        if(createFile)
            createConfigFile(path);
        try {
            return Files.newInputStream(out);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Creates file with given path in user config directory and returns it's {@link Path}.
     * @param filePath path to file as {@link String}.
     * @return {@link Path} to file inside application's config directory.
     */
    public static Path createConfigFile(String filePath) {
        check();
        Path out = Paths.get(sConfigDirectory, filePath);
        try {
            Files.createDirectories(out.getParent());
            if (!Files.exists(out)) {
                Files.createFile(out);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return out;
    }

    /**
     * Loads properties from given file.
     * @param filePath File path inside of user's config folder to load.
     * @return Returns {@link InputProperties} loaded with properties from given file.
     * @deprecated use {@link BundleIO#loadBundle(InputStream)} instead.
     */
    @Deprecated
    public static InputProperties loadProperties(String filePath) {
        return loadProperties(filePath, new InputProperties());
    }

    /**
     * Loads properties from given file.
     * @param properties {@link InputProperties} to load to.
     * @param filePath File path inside of user's config folder to load.
     * @return Returns the given {@link InputProperties} loaded with properties from given file.
     * @deprecated use {@link BundleIO#loadBundle(InputStream)} instead.
     */
    @Deprecated
    public static InputProperties loadProperties(String filePath, InputProperties properties) {
        try(InputStream inputStream = getConfigInputStream(filePath, true)) {
             properties.load(inputStream);
        } catch (IOException e) {
            JeLib.console().error("Could not load properties from configuration file "+sConfigDirectory+"/"+filePath);
        }
        return properties;
    }

    /**
     * Stores properties from given {@link OutputProperties} to file in given path.
     * @param filePath   Path as {@link String} to store the properties to.
     * @param properties {@link OutputProperties} to store in given file.
     * @deprecated use {@link BundleIO#storeBundle(OutputStream, Bundle)} instead.
     */
    @Deprecated
    public static void storeProperties(String filePath, OutputProperties properties) {
        try (OutputStream stream = getConfigOutputStream(filePath)) {
            properties.store(stream);
        } catch (IOException e) {
            JeLib.console().error("Could not store properties to configuration file "+sConfigDirectory+"/"+filePath);
        }
    }

    /**
     * Loads a bundle from file with given path inside user's configuration folder.
     * If the file doesn't exist, method creates it.
     * @param  filePath Path of the file as string.
     * @return Returns Loaded bundle.
     * @see #getConfigInputStream(String, boolean)
     */
    public static Bundle loadBundle(String filePath) {
        try(InputStream inputStream = getConfigInputStream(filePath, true)) {
            return BundleIO.loadBundle(inputStream);
        } catch (IOException e) {
            JeLib.console().error("Could not close input stream.");
            JeLib.console().exception(e);
        }
        return null;
    }

    /**
     * Stores a bundle in file with given path inside user's configuration folder.
     * If the file doesn't exist, method creates it.
     * @param filePath Path of the file as string.
     * @param bundle   Bundle to store.
     */
    public static void storeBundle(String filePath, Bundle bundle) {
        try (OutputStream outputStream = getConfigOutputStream(filePath)) {
            BundleIO.storeBundle(outputStream, bundle);
        } catch (IOException e) {
            JeLib.console().error("Could not close stream.");
            JeLib.console().exception(e);
        }
    }

    /**
     * Checks if utility is initialized.
     * If not, {@link NotInitializedException} is being thrown.
     * @see #sConfigDirectory
     * @see #init(String)
     */
    private static void check() {
        if(sConfigDirectory == null)
            throw new NotInitializedException();
    }

    /**
     * Private constructor prevents instantiating.
     */
    private Configuration(){}
}
