package net.demilich.metastone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;

/**
 * TODO:  not sure how this is used.
 * Paths in this file are no longer valid since cards, decks and deckformat are loaded from jar resources
 */
public class DevCardTools {

	public static void assignUniqueIdToEachCard() {
		final String path = "./src/" + Card.class.getPackage().getName().replace(".", "/") + "/concrete/";
		final String idExpression = "public int getTypeId()";
		File folder = new File(path);
		int uniqueId = 1;
		HashSet<Integer> assignedIds = new HashSet<>();
		List<File> filesWithoutId = new ArrayList<>();
		for (File file : FileUtils.listFiles(folder, new String[] { "java" }, true)) {
			try {
				// System.out.println("Processing " + file.getName() + "...");
				List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
				int lineIndex = containsExpression(lines, idExpression);
				if (lineIndex != -1) {
					// System.out.println("Skipping " + file.getName() +
					// " because it already has an id assigned");
					int id = extractId(lines.get(lineIndex + 1));
					assignedIds.add(id);
					continue;
				} else {
					filesWithoutId.add(file);
				}

			} catch (IOException e) {
				System.err.println("Error while parsing file: " + file.getName());
				e.printStackTrace();
			}
		}

		while (assignedIds.contains(uniqueId)) {
			uniqueId++;
		}

		for (File file : filesWithoutId) {
			try {
				List<String> lines;
				lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
				for (int i = lines.size() - 1; i > 0; i--) {
					String line = lines.get(i);
					if (line.contains("}")) {
						lines.add(i, "\t}");
						lines.add(i, "\t\treturn " + uniqueId + ";");
						lines.add(i, "\tpublic int getTypeId() {");
						lines.add(i, "\t@Override");
						lines.add(i, "\n");
						System.out.println("Assigning id " + uniqueId + " to " + file.getName());
						uniqueId++;
						break;
					}
				}

				Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void cardListFromImages(String path) throws IOException {
		File folder = new File(path);

		PrintWriter out = new PrintWriter(new FileWriter("cards_all"));

		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				out.println(file.getName().replace(".png", ""));
			}
		}
		out.close();
	}

	private static String changeFileNameToClassName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("File Name == null");
		}
		String className = name.replace(".java", "");

		className = className.replace('/', '.');
		className = className.replace('\\', '.');
		className = className.replace("..src.", "");

		return className;
	}

	public static void compareClassesWithCardList(String path) throws IOException {
		File folder = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader("cards_all"));
		List<String> allCards = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
			allCards.add(toCanonName(line));
		}
		reader.close();

		List<String> allClasses = new ArrayList<String>();

		for (File file : FileUtils.listFiles(folder, new String[] { "java" }, true)) {
			String canonName = toCanonName(file.getName());
			allClasses.add(canonName);
		}

		int missing = 0;
		for (String card : allCards) {
			if (allClasses.contains(card)) {
				// System.out.println("Card found: " + card);
			} else {
				missing++;
				System.out.println("Card missing: " + card);
			}
		}
		System.out.println("There are " + missing + " cards missing");
	}

	private static int containsExpression(List<String> lines, String expression) {
		int i = 0;
		for (String line : lines) {
			if (line.contains(expression)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private static int extractId(String line) {
		String result = "";
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (Character.isDigit(c)) {
				result += c;
			}
		}
		return Integer.parseInt(result);
	}

	public static void formatJsons() {
		File folder = new File("./cards/");

		Collection<File> files = FileUtils.listFiles(folder, new String[] { "json" }, true);
		int i = 0;
		for (File file : files) {
			try {
				System.out.println("Processing " + file.getName() + " (" + ++i + " of " + files.size() + " files)");
				prettyPrintFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static List<String> getImplementedCardsAsLines() {
		final String expression = "cards.add(new %s());";
		final String path = "./src/" + Card.class.getPackage().getName().replace(".", "/") + "/concrete/";
		List<String> lines = new ArrayList<String>();
		File folder = new File(path);
		for (File file : FileUtils.listFiles(folder, new String[] { "java" }, true)) {
			String cardFileName = file.getPath();
			String cardClassName = changeFileNameToClassName(cardFileName);
			// System.out.println(changeFileNameToClassName(cardName));
			lines.add(String.format(expression, cardClassName));
		}
		return lines;
	}

	private static void prettyPrintFile(File file) throws IOException {
		Path path = Paths.get(file.getPath());
		String content = new String(Files.readAllBytes(path));

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");

		scriptEngine.put("jsonString", content);
		try {
			scriptEngine.eval("result = JSON.stringify(JSON.parse(jsonString), null, \"\t\")");
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String prettyPrintedJson = (String) scriptEngine.get("result");
		Files.write(path, prettyPrintedJson.getBytes());
	}

	private static String toCanonName(String name) {
		return name.toLowerCase().replace(".java", "").replace(".png", "").replace("_", "").replace("-", "");
	}

	public static void updateCardCatalogue() {
		final String cataloguePathStr = "./src/" + CardCatalogue.class.getPackage().getName().replace(".", "/") + "/CardCatalogue.java";
		Path cataloguePath = Paths.get(cataloguePathStr);
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(cataloguePath)) {
			String line = null;
			List<String> implementedCards = getImplementedCardsAsLines();
			boolean insideRelevantCodeBlock = false;
			while ((line = reader.readLine()) != null) {
				if (line.contains("static {")) {
					lines.add(line);
					insideRelevantCodeBlock = true;
					lines.addAll(implementedCards);
				} else if (line.contains("}")) {
					insideRelevantCodeBlock = false;
				}

				if (!insideRelevantCodeBlock) {
					lines.add(line);
				}

			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		try {
			Files.write(cataloguePath, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("CardCatalogue has been successfully updated");
	}

}
