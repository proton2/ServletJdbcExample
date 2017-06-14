package com.java.servlets.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by proton2 on 01.01.2017.
 */
public class SysHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysHelper.class);

    public static String getFieldNameFromAlias(String alias) {
        int i = alias.indexOf('_');
        if (i > 0) {
            return alias.substring(i+1);
        }
        return alias;
    }

    public static String getClassNameFromAlias(String alias) {
        int i = alias.indexOf('_');
        if (i > 0) {
            return alias.substring(0, i);
        }
        return alias;
    }

    public static String getFileExt(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    public static void deleteFile(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileName1(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.lastIndexOf("\\") + 1);
            }
        }
        return "";
    }
}
