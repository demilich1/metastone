package net.demilich.metastone.gui.trainingmode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.decks.Deck;

public class TrainingConfig {

	private int numberOfGames;
	private final Deck deckToTrain;
	private final List<Deck> decks = new ArrayList<Deck>();

	public TrainingConfig(Deck deckToTrain) {
		this.deckToTrain = deckToTrain;
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public Deck getDeckToTrain() {
		return deckToTrain;
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
