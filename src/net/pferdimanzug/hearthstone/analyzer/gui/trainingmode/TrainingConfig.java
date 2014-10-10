package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.learning.LearningBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

public class TrainingConfig {
	
	private int numberOfGames;
	private final List<Deck> decks = new ArrayList<Deck>();
	private final LearningBehaviour learner;
	
	public TrainingConfig(LearningBehaviour learner) {
		this.learner = learner;
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public LearningBehaviour getLearner() {
		return learner;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}
	
	public Deck getRandomDeck() {
		return decks.get(ThreadLocalRandom.current().nextInt(decks.size()));
	}

	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

}
