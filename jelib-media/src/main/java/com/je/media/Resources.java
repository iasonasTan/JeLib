package com.je.media;

import com.je.core.JeLib;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Supplier;

/**
 * This class handles app-level resources.
 * <br><b>WARNING:</b> Class must be initialized before use.
 * @see #init(Class)
 */
public final class Resources {
    /**
     * Fallback image to return if requested image does not exist.
     * @see #loadImage(String, Class)
     */
    private static BufferedImage sCannotLoadImage;

    /**
     * This is the class used to load resources from resources root.
     */
    private static Class<?> sResourceLoader;

    /**
     * Method initializes class and loads fallback image.
     */
    public static void init(Class<?> resLoader) {
        try {
            sResourceLoader = resLoader;
            InputStream inputStream = Resources.class.getResourceAsStream("/cannotloadimage.png");
            if(inputStream!=null)
                sCannotLoadImage = ImageIO.read(inputStream);
        } catch (IOException ignored) {
            // ignore
        }
    }

    /**
     * Method loads image from class {@link Resources} and given {@code path}.<br>
     * Used for loading resources from project root.
     * <br><b>NOTE:</b> To load resources from specific package, use {@link #loadImage(String, Class)}.
     * @param path path to load image from.
     * @return returns loaded or fallback image as {@link BufferedImage}.
     */
    public static BufferedImage loadImage(String path) {
        return loadImage(path, sResourceLoader);
    }

    /**
     * Method loads image from given {@code class} and given {@code path}.<br>
     * @param path  path to load image from.
     * @param clazz class to get resource from.
     * @return returns loaded or fallback image as {@link BufferedImage}.
     */
    public static BufferedImage loadImage(final String path, Class<?> clazz) {
        final Supplier<BufferedImage> errorHandler = () -> {
            System.err.println("Cannot load image "+path);
            return sCannotLoadImage;
        };
        try {
            URL rsrcUrl = clazz.getResource(path);
            if (rsrcUrl == null) {
                System.err.println("Failed to load image '"+path+"'");
                return errorHandler.get();
            }
            BufferedImage image = ImageIO.read(rsrcUrl);
            return image == null ? errorHandler.get() : image;
        } catch (IOException e) {
            return errorHandler.get();
        }
    }

    /**
     * Loads .wav file ready to play.
     * Used for loading files from project root.
     * To load files from another package or directory, use {@link #loadClip(String, Class)}
     * @param  path path of file to load as {@link String}
     * @return Returns .wav file loaded as {@link Clip}.
     * @see Sound
     */
    public static Clip loadClip(String path) {
        return loadClip(path, sResourceLoader);
    }

    /**
     * Loads .wav file ready to play.
     * @param clazz class to get resource from.
     * @param  path path of file to load as {@link String}
     * @return Returns .wav file loaded as {@link Clip}.
     * @see Sound
     */
    public static Clip loadClip(String path, Class<?> clazz) {
        try {
            URL rsrcUrl = clazz.getResource(path);
            if (rsrcUrl == null) {
                System.err.println("Failed to load sound " + path);
                return null;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(rsrcUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.err.println("Failed to load sound " + path + " (Exception thrown)");
            throw new CannotLoadClipException();
        }
    }

    /**
     * Loads .wav file ready to play one and only one time.
     * The returned {@link Clip} closes itself after finishing playing.
     * Used for loading files from project root.
     * To load files from another package or directory, use {@link #loadOneUseClip(String, Class)}
     * @param  path path of file to load as {@link String}
     * @return Returns .wav file loaded as {@link Clip}.
     * @see Sound
     */
    public static Clip loadOneUseClip(String path) {
        return loadOneUseClip(path, sResourceLoader);
    }

    /**
     * Loads .wav file ready to play one and only one time.
     * The returned {@link Clip} closes itself after finishing playing.
     * @param  path path of file to load as {@link String}
     * @param clazz class to get resource from.
     * @return Returns .wav file loaded as {@link Clip}.
     * @see Sound
     */
    public static Clip loadOneUseClip(String path, Class<?> clazz) {
        Clip clip = loadClip(path, clazz);
        if (clip == null) return null;
        clip.addLineListener(l -> {
            if (l.getType() == LineEvent.Type.STOP && clip.isOpen()) {
                clip.close();
            }
        });
        return clip;
    }

    /**
     * Loads text from given file.
     * Used for loading files from project root.
     * To load files from another package or directory, use {@link #loadText(String, Class)}
     * @param path path of file to load.
     * @return Returns contents of file at given {@code path} as {@link String}.
     */
    public static String loadText(String path) {
        return loadText(path, sResourceLoader);
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
    private Resources() {}
}