package net.pferdimanzug.hearthstone.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class DevCheckCardCompleteness {

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
				//System.out.println("Card found: " + card);
			} else {
				missing++;
				System.out.println("Card missing: " + card);
			}
		}
		System.out.println("There are " + missing + " cards missing");
	}

	public static void printImplementedCards(String path, String expression) throws IOException {
		File folder = new File(path);
		for (File file : FileUtils.listFiles(folder, new String[] { "java" }, true)) {
			String cardName = file.getName().replace(".java", "");
			System.out.println(String.format(expression, cardName));
		}
	}
	
	private static String toCanonName(String name) {
		return name.toLowerCase().replace(".java", "").replace(".png", "").replace("_", "").replace("-", "");
	}

}
