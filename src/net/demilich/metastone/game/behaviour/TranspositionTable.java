package net.demilich.metastone.game.behaviour;

import java.util.HashMap;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;

public class TranspositionTable {

	private static int hash(Entity entity) {
		int hash = entity.getName().hashCode();
		for (Attribute tag : entity.getAttributes().keySet()) {
			Object value = entity.getAttributes().get(tag);
			if (!(value instanceof Integer)) {
				continue;
			}
			hash = mergeHashes(hash, entity.getAttribute(tag).hashCode());
		}
		return hash;
	}

	private static int mergeHashes(int hash1, int hash2) {
		int hash = 0;
		hash = hash * 33 ^ hash1;
		hash = hash * 33 ^ hash2;
		return hash;
	}

	private HashMap<Integer, Double> knownScores = new HashMap<Integer, Double>();

	// private HashMap<Integer, GameContext> debug = new HashMap<Integer,
	// GameContext>();
	private int cachedKey;

	private GameContext cachedState;

	public void clear() {
		knownScores.clear();
	}

	public double getScore(GameContext context) {
		return knownScores.get(hash(context));
	}

	private int hash(GameContext context) {
		if (context == cachedState) {
			return cachedKey;
		}

		int hash = context.getActivePlayerId();

		for (Player player : context.getPlayers()) {
			hash = mergeHashes(hash, player.getMana());
			hash = mergeHashes(hash, player.getMaxMana());
			hash = mergeHashes(hash, hash(player.getHero()));
			for (Card handCard : player.getHand()) {
				hash = mergeHashes(hash, hash(handCard));
			}

			for (Card deckCard : player.getDeck()) {
				hash = mergeHashes(hash, hash(deckCard));
			}
			for (Entity minion : player.getMinions()) {
				hash = mergeHashes(hash, hash(minion));
				hash = mergeHashes(hash, context.getTriggersAssociatedWith(minion.getReference()).size());
			}

		}
		cachedState = context;
		cachedKey = hash;
		return hash;
	}

	public boolean known(GameContext context) {
		int key = hash(context);
		// if (knownScores.containsKey(key)) {
		// System.out.println("Identical game states found!");
		// System.out.println("New: " + context);
		// System.out.println("Existing: " + debug.get(key));
		// }
		return knownScores.containsKey(key);
	}

	public void save(GameContext context, double score) {
		int key = hash(context);
		knownScores.put(key, score);
		// debug.put(key, context);
	}

}
