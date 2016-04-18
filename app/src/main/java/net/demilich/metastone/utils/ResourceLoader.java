package net.demilich.metastone.utils;

import net.demilich.metastone.game.cards.CardProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class ResourceLoader {

    // the number of dirs levels to traverse on the given path
    private static final int DIR_LEVELS = 3;

    private static Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

    /**
     * Loads all the json files from the given rootPath into a collection of ResourceInputStreams
     * @param rootPath the Path root from where to load the json files
     * @param fromFileSystem True if the rootPath is on the filesystem, False if the rootPath is in the Resources dir
     * @return Collections of ResourceInputStreams pointing to the json files
     * @throws URISyntaxException
     * @throws IOException
     */
    public static  Collection<ResourceInputStream> loadJsonInputStreams(String rootPath, boolean fromFileSystem) throws URISyntaxException, IOException {
        URI uri;
        if (fromFileSystem) {
            uri = new File(rootPath).toURI();
        } else {
            try {
                uri = Object.class.getResource(rootPath).toURI();
            } catch (NullPointerException ex) {
                logger.error(rootPath + " directory not found");
                throw new RuntimeException(rootPath + " directory not found");
            }
        }

        Path cardsPath;
        boolean cardsInJar = uri.getScheme().equals("jar");
        if (cardsInJar) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            cardsPath = fileSystem.getPath(rootPath);
        } else {
            cardsPath = Paths.get(uri);
        }

        Collection<ResourceInputStream> inputStreams = new ArrayList<>();

        Path filePath;
        Stream<Path> walk = Files.walk(cardsPath, DIR_LEVELS);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
            filePath = it.next();

            if (!filePath.toString().endsWith("json")) continue;

            InputStream inputStream;
            if (cardsInJar) {
                inputStream = Object.class.getResourceAsStream(filePath.toString());
            } else {
                inputStream = new FileInputStream(new File(filePath.toString()));
            }

            inputStreams.add(new ResourceInputStream(filePath.getFileName().toString(), inputStream, fromFileSystem));
        }

        return inputStreams;
    }
}
