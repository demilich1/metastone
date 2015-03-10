package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class SpellUtils {

	public static Card drawFromDeck(GameContext context, Player player) {
		return context.getLogic().drawCard(player.getId());
	}

	public static CardCollection getCards(CardCollection source, Predicate<Card> filter) {
		CardCollection result = new CardCollection();
		for (Card card : source) {
			if (filter.test(card)) {
				result.add(card);
			}
		}
		return result;
	}

	public static Card getRandomCard(CardCollection source, Predicate<Card> filter) {
		CardCollection result = getCards(source, filter);
		if (result.isEmpty()) {
			return null;
		}
		return result.getRandom();
	}

	public static <T> T getRandomTarget(List<T> targets) {
		int randomIndex = ThreadLocalRandom.current().nextInt(targets.size());
		return targets.get(randomIndex);
	}

	public static List<Actor> getValidRandomTargets(List<Entity> targets) {
		List<Actor> validTargets = new ArrayList<Actor>();
		for (Entity entity : targets) {
			Actor actor = (Actor) entity;
			if (!actor.isDead() || actor.getEntityType() == EntityType.HERO) {
				validTargets.add(actor);
			}

		}
		return validTargets;
	}

	public static List<Entity> getValidTargets(List<Entity> allTargets, Predicate<Entity> filter) {
		if (filter == null) {
			return allTargets;
		}
		List<Entity> validTargets = new ArrayList<>();
		for (Entity entity : allTargets) {
			if (filter.test(entity)) {
				validTargets.add(entity);
			}
		}
		return validTargets;
	}

	public static int hasHowManyOfRace(Player player, Race race) {
		int count = 0;
		for (Minion minion : player.getMinions()) {
			if (minion.getRace() == race) {
				count++;
			}
		}
		return count;
	}

	public static boolean hasMinionOfRace(Player player, Race race) {
		for (Minion minion : player.getMinions()) {
			if (minion.getRace() == race) {
				return true;
			}
		}
		return false;
	}

	public static boolean holdsMinionOfRace(Player player, Race race) {
		for (Card card : player.getHand()) {
			if (card.getTag(GameTag.RACE) == race) {
				return true;
			}
		}
		return false;
	}

	private SpellUtils() {
	}

}
