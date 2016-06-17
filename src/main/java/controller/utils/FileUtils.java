package controller.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class FileUtils {

    public String getFiles(String fileName) {

        String result = "";

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
