package com.nanda.aadharreader.utils;

public class TextUtils {

    public static String getString(String input) {
        return input == null || input.isEmpty() ? "NA" : input;
    }

}
