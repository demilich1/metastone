package net.demilich.metastone.gui.trainingmode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.trainingmode.TrainingData;
import net.demilich.metastone.utils.ResourceInputStream;
import net.demilich.metastone.utils.ResourceLoader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.demilich.nittygrittymvc.Proxy;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.WeightedFeature;

public class TrainingProxy extends Proxy<GameNotification> {

	public static final String NAME = "TrainingProxy";
	private static final String TRAINING_FOLDER = "training";

	private static Logger logger = LoggerFactory.getLogger(TrainingProxy.class);

	private final HashMap<String, FeatureVector> trainingData = new HashMap<String, FeatureVector>();

	public TrainingProxy() {
		super(NAME);
		if (new File(BuildConfig.USER_HOME_METASTONE + File.separator + TRAINING_FOLDER).mkdir()) {
			logger.info(BuildConfig.USER_HOME_METASTONE + File.separator + TRAINING_FOLDER + " folder created");
		}
		try {
			loadTrainingData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TrainingData getTrainingData(String deckName) {
		return trainingData.containsKey(deckName) ? new TrainingData(deckName, trainingData.get(deckName)) : null;
	}

	public void loadTrainingData() throws IOException, URISyntaxException {
		trainingData.clear();

		// load training from resources jar on the classpath
		Collection<ResourceInputStream> inputStreams = ResourceLoader.loadJsonInputStreams(TRAINING_FOLDER, false);

		// load cards from ~/metastone/training folder on the filesystem
		if (Paths.get(BuildConfig.USER_HOME_METASTONE + File.separator + TRAINING_FOLDER).toFile().exists()) {
			inputStreams.addAll((ResourceLoader.loadJsonInputStreams(BuildConfig.USER_HOME_METASTONE + File.separator + TRAINING_FOLDER, true)));
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		HashMap<String, Object>  map;
		Reader reader;
		for (ResourceInputStream resourceInputStream : inputStreams) {
			reader = new InputStreamReader(resourceInputStream.inputStream);
			map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {}.getType());

			final String DECK_NAME = "deck";
			if (!map.containsKey(DECK_NAME)) {
				logger.error("Training data {} does not specify a value for '{}' and is therefor not valid", resourceInputStream.fileName, DECK_NAME);
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
			filename = BuildConfig.USER_HOME_METASTONE + File.separator + TRAINING_FOLDER + File.separator + filename + ".json";
			Files.write(Paths.get(filename), jsonData.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
