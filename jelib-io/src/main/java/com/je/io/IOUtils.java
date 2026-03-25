package com.je.io;

import com.je.core.JeLib;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Supplier;

/**
 * Utils related to I/O stuff.
 */
public final class IOUtils {
    /**
     * Fallback image to return if requested image does not exist.
     * @see #loadImage(String, Class)
     */
    private static BufferedImage sCannotLoadImage;

    /**
     * Method initializes class and loads fallback image.
     */
    public static void init() {
        try {
            InputStream inputStream = IOUtils.class.getResourceAsStream("/cannotloadimage.png");
            if(inputStream!=null)
                sCannotLoadImage = ImageIO.read(inputStream);
        } catch (IOException ignored) {
            // ignore
        }
    }

    /**
     * Loads audio input stream file ready to play.
     * @param clazz class to get resource from.
     * @param  path path of file to load as {@link String}
     * @return Returns .wav file loaded as {@link Clip}.
     */
    public static AudioInputStream loadAudioInputStream(String path, Class<?> clazz) {
        try {
            URL rsrcUrl = clazz.getResource(path);
            if (rsrcUrl == null) {
                JeLib.console().error("Failed to load sound " + path);
                return null;
            }
            return AudioSystem.getAudioInputStream(rsrcUrl);
        } catch (UnsupportedAudioFileException | IOException e) {
            JeLib.console().error("Failed to load sound " + path + " (Exception thrown)");
            JeLib.console().exception(e);
        }
        return null;
    }
    
    /**
     * Method loads image from given {@code class} and given {@code path}.<br>
     * @param path  path to load image from.
     * @param clazz class to get resource from.
     * @return returns loaded or fallback image as {@link BufferedImage}.
     */
    public static BufferedImage loadImage(final String path, Class<?> clazz) {
        final Supplier<BufferedImage> fallbackImageSupplier = () -> {
            JeLib.console().error("Cannot load image "+path);
            return sCannotLoadImage;
        };
        try {
            URL rsrcUrl = clazz.getResource(path);
            if (rsrcUrl == null) {
                return fallbackImageSupplier.get();
            }
            BufferedImage image = ImageIO.read(rsrcUrl);
            return image == null ? fallbackImageSupplier.get() : image;
        } catch (IOException e) {
            return fallbackImageSupplier.get();
        }
    }
    
    /**
     * Loads text from given file.
     * @param path  path of file to read.
     * @param clazz class to get resource form.
     * @return returns contents of file at given {@code path} as {@link String}.
     */
    public static String loadText(String path, Class<?> clazz) {
        try (InputStream inputStream = clazz.getResourceAsStream(path)) {
            StringBuilder messageBuilder = new StringBuilder();
            if(inputStream==null)
                throw new NullPointerException(clazz.getName()+".getResourceAsStream(\""+path+"\") returned null"); // goto catch
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null) {
                messageBuilder.append(line).append('\n');
            }
            return messageBuilder.toString();
        } catch (IOException | NullPointerException e) {
            JeLib.console().exception(e);
            return "COULD NOT LOAD RESOURCE";
        }
    }

    /**
     * Private constructor prevents instantiation.
     */
    private IOUtils(){
    }
}
