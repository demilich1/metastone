package net.demilich.metastone.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoader {

	// the number of dirs levels to traverse on the given path
	private static final int DIR_LEVELS = 5;

	private static Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

	/**
	 * Loads all the json files from the given rootDir into a collection of
	 * ResourceInputStreams
	 * 
	 * @param rootDir
	 *            the root dir from where to start traversing to load the json
	 *            files
	 * @param fromFileSystem
	 *            True if the rootDir is on the filesystem, False if the rootDir
	 *            is in the Resources dir
	 * @return Collections of ResourceInputStreams pointing to the json files
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static Collection<ResourceInputStream> loadJsonInputStreams(String rootDir, boolean fromFileSystem)
			throws URISyntaxException, IOException {
		if (rootDir == null) {
			throw new RuntimeException("rootDir cannot be null");
		}

		PathReference pathReference;
		if (fromFileSystem) {
			pathReference = new PathReference(Paths.get(rootDir), false);
		} else { // from resources
			pathReference = getPathFromResources(rootDir);
		}

		Collection<ResourceInputStream> inputStreams = new ArrayList<>();

		Path filePath;
		Stream<Path> walk = Files.walk(pathReference.path, DIR_LEVELS);
		for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
			filePath = it.next();

			// skip over non-json files and directories
			if (!filePath.toString().endsWith("json"))
				continue;

			InputStream inputStream;
			if (pathReference.fromJar) {
				inputStream = Object.class.getResourceAsStream(filePath.toString());
			} else {
				inputStream = new FileInputStream(new File(filePath.toString()));
			}

			inputStreams.add(new ResourceInputStream(filePath.getFileName().toString(), inputStream, fromFileSystem));
		}
		walk.close();
		return inputStreams;
	}

	/**
	 * Utility method to get a PathReference from a given sourceDir that's in
	 * the Resources dir or a Jar file.
	 * 
	 * @param sourceDir
	 *            the dir of interest in the Resources dir or Jar file
	 * @return a PathReference which contains a Path and boolean indicating the
	 *         path is in a Jar fle.
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private static PathReference getPathFromResources(String sourceDir) throws URISyntaxException, IOException {
		URI uri;
		try {
			uri = Object.class.getResource("/" + sourceDir).toURI();
		} catch (NullPointerException ex) {
			logger.error(sourceDir + " directory not found in resources");
			throw new RuntimeException(sourceDir + " directory not found in resources");
		}

		// handle case where resources are on the filesystem instead of jar. ie:
		// running form within IntelliJ
		boolean fromJar = uri.getScheme().equals("jar");
		Path path;
		FileSystem fileSystem;
		if (fromJar) { // from jar file on the classpath
			try {
				fileSystem = FileSystems.getFileSystem(uri);
			} catch (FileSystemNotFoundException ex) {
				fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object> emptyMap());
			}
			path = fileSystem.getPath(sourceDir);
		} else { // from resources folder on the filesystem
			path = Paths.get(uri);
		}

		return new PathReference(path, fromJar);
	}

	/**
	 * Copy all files from the Resources sourceDir subfolder to the targetDir on
	 * the filesystem.
	 * 
	 * @param sourceDir
	 *            path to dir who's contents to copy
	 * @param targetdir
	 *            path to dir where we want to copy the files to
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void copyFromResources(final String sourceDir, final String targetdir) throws URISyntaxException, IOException {

		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader) cl).getURLs();
		URL cardsUrl = null;
		String jarFileName = null;
		for (URL url : urls) {
			jarFileName = new File(url.toURI()).getName();
			if (jarFileName.startsWith("cards")) {
				cardsUrl = url;
				break;
			}
		}

		final String cardsJarFile = jarFileName;
		final PathReference sourcePathReference = getPathFromResources(sourceDir);
		final Path targetDirPath = Paths.get(targetdir);

		logger.info("Copying resources from " + cardsUrl + " to " + targetDirPath);

		Files.walkFileTree(sourcePathReference.path, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
				//Path currentTargetDir = Paths.get(targetDirPath.toString() + File.separator + dir.getFileName());
				String relativePath = dir.toString().replace(sourcePathReference.path.toString(), "");
				Path currentTargetDir = Paths.get(targetDirPath + relativePath);
				Files.createDirectories(currentTargetDir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				String relativePath = file.toString().replace(sourcePathReference.path.toString(), "");
				Path currentTargetFile = Paths.get(targetDirPath + relativePath);

				logger.info(cardsJarFile + "!" + file + "  -->  " + currentTargetFile);
				Files.copy(file, currentTargetFile, StandardCopyOption.REPLACE_EXISTING);
				return FileVisitResult.CONTINUE;
			}

		});
	}

	/**
	 * Data tuple which holds a path and boolean flag indicating that the path
	 * is from a jar resource file.
	 */
	private static class PathReference {
		final Path path;
		final boolean fromJar;

		public PathReference(Path path, boolean fromJar) {
			this.path = path;
			this.fromJar = fromJar;
		}
	}
}
