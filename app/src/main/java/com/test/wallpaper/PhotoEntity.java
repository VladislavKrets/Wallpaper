package com.test.wallpaper;

import java.io.InputStream;

public class PhotoEntity {
    private InputStream filePath;
    private String name;

    public PhotoEntity(InputStream filePath, String name) {
        this.filePath = filePath;
        this.name = name;
    }

    public InputStream getFilePath() {
        return filePath;
    }

    public String getName() {
        return name;
    }
}
