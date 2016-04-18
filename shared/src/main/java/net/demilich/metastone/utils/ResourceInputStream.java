package net.demilich.metastone.utils;

import java.io.InputStream;

/**
 * Data object that holds a filename, inputstream and a boolean flag to indicate
 * if the file is on the filesystem or in the applications resource bundled dir.
 */
public class ResourceInputStream {

    public final String fileName;
    public final InputStream inputStream;
    public final boolean fromFilesystem;

    public ResourceInputStream(String fileName, InputStream inputStream, boolean filesytem) {
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.fromFilesystem = filesytem;
    }
}