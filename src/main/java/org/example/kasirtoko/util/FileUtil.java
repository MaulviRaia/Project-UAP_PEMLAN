package org.example.kasirtoko.util;

import java.io.*;

public class FileUtil {

    private static final String FOLDER = "data";

    static {
        File dir = new File(FOLDER);
        if (!dir.exists()) dir.mkdir();
    }

    public static File getFile(String name) {
        return new File(FOLDER + File.separator + name);
    }
}
