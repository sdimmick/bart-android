package com.stevodimmick.bart.util;

/**
 * Basic utility methods for the Android logger
 * @author sdimmick
 */
public class LogUtils {
    
    /**
     * Generates a tag for the Android logger based on the specified class
     * @param clazz the {@link Class} to generate a tag for
     * @return a unique identifier to serve as the logger tag for the specified {@link Class}
     */
    public static String getTag(@SuppressWarnings("rawtypes") Class clazz) {
        return clazz.getSimpleName();
    }

}
