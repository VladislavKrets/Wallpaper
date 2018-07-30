package com.test.wallpaper;

import java.io.InputStream;

public class UtilsInputStream {
    private static UtilsInputStream utilsInputStream;
    private InputStream inputStream;
    private UtilsInputStream() {}

    public static UtilsInputStream getInstance() {
        if (utilsInputStream == null) utilsInputStream = new UtilsInputStream();
        return utilsInputStream;
    }

    public static UtilsInputStream getUtilsInputStream() {
        return utilsInputStream;
    }

    public static void setUtilsInputStream(UtilsInputStream utilsInputStream) {
        UtilsInputStream.utilsInputStream = utilsInputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
