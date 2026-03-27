package com.je.io.bundle;

import com.je.core.JeLib;
import com.je.core.util.Bundle;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class BundleIO {
    /**
     * Stores a bundle to given output stream.<br>
     * <b>WARNING: </b>Only int, double, String are getting stored.
     * Other types will be ignored.<br>
     * @param outputStream Output stream to store bundle to.
     * @param bundle       Bundle to store.
     */
    public static void storeBundle(OutputStream outputStream, Bundle bundle) {
        final Function<Class<?>, Boolean> typeChecker = t -> List.<Class<?>>of(
                Integer.class,
                Double.class,
                String.class,
                Boolean.class
        ).contains(t);

        try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            Map<String, Object> data = bundle.getRawData();
            for(Map.Entry<String, Object> entry: data.entrySet()) {
                String keyStr = entry.getKey();
                Object value = entry.getValue();
                if(!typeChecker.apply(value.getClass())) return;
                String valueStr = String.valueOf(value);
                String line = keyStr+"="+valueStr;
                bufferedWriter.write(line);
                bufferedWriter.write("\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            JeLib.console().error("Could not store bundle to given output stream.");
            JeLib.console().exception(e);
        }
    }

    /**
     * Loads a bundle from given {@link InputStream}.
     * <b>WARNING: </b>Only int, double, String are getting loaded.
     * Other types will be ignored.<br>
     * @param inputStream Input stream to load bundle from.
     * @return returns loaded bundle, empty bundle if loading failed.
     */
    public static Bundle loadBundle(InputStream inputStream) {
        Bundle bundle = new Bundle();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                if(line.startsWith("#")) continue;
                String[] lineSplit = line.split("="); // [key, value]
                String keyStr  =lineSplit[0];
                String valueStr=lineSplit[1];
                putValueIntoBundle(bundle, keyStr, valueStr);
            }
        } catch (IOException e) {
            JeLib.console().error("Could not load bundle from given input stream.");
            JeLib.console().exception(e);
        }
        return bundle;
    }

    /**
     * Find value's type, casts it, and puts it into bundle.
     * @param bundle   bundle to put values to.
     * @param keyStr   key of the value to put to the bundle.
     * @param valueStr value to cast and put to the bundle.
     */
    private static void putValueIntoBundle(Bundle bundle, String keyStr, String valueStr) {
        try{
            if(valueStr.equals("true")) { // Boolean
                bundle.put(keyStr, true);
            } else if(valueStr.equals("false")) { // Boolean
                bundle.put(keyStr, false);
            } else if(valueStr.contains(".")) { // double
                double valueDouble = Double.parseDouble(valueStr);
                bundle.put(keyStr, valueDouble);
            } else { // integer
                int valueInt = Integer.parseInt(valueStr);
                bundle.put(keyStr, valueInt);
            }
        } catch (NumberFormatException e) { // string
            bundle.put(keyStr, valueStr);
        }
    }
}
