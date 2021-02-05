package com.sustainabledev.utilities.utils;

import com.sustainabledev.utilities.exceptions.IllegalOperationException;

import java.io.File;

public class FileUtils {

    public static String getExtension(File file) {
        if(file.isFile()) {
            String name = file.getName();
            return name.substring(name.lastIndexOf('.') + 1);
        } else {
            throw new IllegalOperationException("Extension is extracted only from a file. Not a file object is invalid");
        }
    }
}
