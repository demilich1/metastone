package net.demilich.metastone.gui.trainingmode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.pferdimanzug.nittygrittymvc.Proxy;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.WeightedFeature;

public class TrainingProxy extends Proxy<GameNotification> {

	public static final String NAME = "TrainingProxy";

	private static Logger logger = LoggerFactory.getLogger(TrainingProxy.class);

	private final HashMap<String, FeatureVector> trainingData = new HashMap<String, FeatureVector>();

	public TrainingProxy() {
		super(NAME);
		if (new File("./training/").mkdir()) {
			logger.info("./training folder created");
		}
		try {
			loadTrainingData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public TrainingData getTrainingData(String deckName) {
		return trainingData.containsKey(deckName) ? new TrainingData(deckName, trainingData.get(deckName)) : null;
	}

	public void loadTrainingData() throws FileNotFoundException {
		trainingData.clear();
		File folder = new File("./training/");
		if (!folder.exists()) {
			logger.warn("/training directory not found");
			return;
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		for (File file : FileUtils.listFiles(folder, new String[] { "json" }, true)) {
			FileReader reader = new FileReader(file);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());

			final String DECK_NAME = "deck";
			if (!map.containsKey(DECK_NAME)) {
				logger.error("Training data {} does not speficy a value for '{}' and is therefor not valid", file.getName(), DECK_NAME);
				continue;
			}

			String deckName = (String) map.get(DECK_NAME);
			map.remove(DECK_NAME);

			FeatureVector featureVector = new FeatureVector();
			for (String element : map.keySet()) {
				try {
					WeightedFeature feature = WeightedFeature.valueOf(element);
					double value = (double) map.get(element);
					featureVector.set(feature, value);
				} catch (IllegalArgumentException ex) {
					logger.warn("Illegal argument in training data: " + element + " Value: " + map.get(element));
				}
			}

			trainingData.put(deckName, featureVector);
		}
	}

	public void saveTrainingData(TrainingData trainingData) {
		String deckName = trainingData.getDeckName();
		FeatureVector featureVector = trainingData.getFeatureVector();
		this.trainingData.put(deckName, featureVector);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		HashMap<String, Object> saveData = new HashMap<String, Object>();
		saveData.put("deck", deckName);
		for (WeightedFeature feature : featureVector.getValues().keySet()) {
			double value = featureVector.get(feature);
			saveData.put(feature.toString(), value);
		}
		String jsonData = gson.toJson(saveData);
		try {
			String filename = deckName.toLowerCase();
			filename = filename.replaceAll(" ", "_");
			filename = filename.replaceAll("\\W+", "");
			filename = "./training/" + filename + ".json";
			Files.write(Paths.get(filename), jsonData.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
