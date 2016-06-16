package net.demilich.metastone.utils;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import net.demilich.metastone.BuildConfig;

/**
 * Singleton data class that holds the platform specific path to the metastone user home dir.
 */
public class UserHomeMetastone {

    private static UserHomeMetastone INSTANCE;
    
    static {
    	UserHomeMetastone.init((FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
                + File.separator + BuildConfig.NAME).replace("\\", "\\\\"));
    }
    
    
    private String dirPath;

    private UserHomeMetastone(String path) {
        dirPath = path;
    }

    private static void init(String path) {
        if(path == null) {
            throw new NullPointerException("UserHomeMetastone.init(path) cannot be initialized with null!");
        }

        if (INSTANCE == null) {
            INSTANCE = new UserHomeMetastone(path);
        } else {
            INSTANCE.dirPath = path;
        }
    }

    public static String getPath() {
        if (INSTANCE == null) {
            throw new RuntimeException("UserHomeMetastone must first be initialized!");
        }

        return INSTANCE.dirPath;
    }
}