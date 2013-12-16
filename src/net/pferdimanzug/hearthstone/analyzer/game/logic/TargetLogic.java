package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class TargetLogic {

	// Blue post:
	// Heroes and minions can attack any opposing character, be it minion or
	// hero.
	// Friendly minions cannot attack each other, but may target each other with
	// Battlecry effects as long as the ability does not specify friendly or
	// opposing.
	// http://www.blizzposts.com/topic/en/216948/the-consistency-of-rules
	public List<Entity> getValidTargets(GameContext context, Player player, GameAction action) {
		TargetRequirement targetRequirement = action.getTargetRequirement();
		ActionType actionType = action.getActionType();
		Player opponent = context.getOpponent(player);

		// if there is a minion with TAUNT and the action is of type basic
		// attack only allow corresponding minions as targets
		if (actionType == ActionType.PHYSICAL_ATTACK
				&& (targetRequirement == TargetRequirement.ENEMY_CHARACTERS || targetRequirement == TargetRequirement.ENEMY_MINIONS)
				&& containsTaunters(opponent.getMinions())) {
			return getTaunters(opponent.getMinions());
		}
		List<Entity> potentialTargets = getEntities(context, player, action.getTargetRequirement()); 
		return filterTargets(action, potentialTargets);
	}

	private List<Entity> getEntities(GameContext context, Player player, TargetRequirement targetRequirement) {
		Player opponent = context.getOpponent(player);
		List<Entity> entities = new ArrayList<>();
		if (targetRequirement == TargetRequirement.ENEMY_HERO
				|| targetRequirement == TargetRequirement.ENEMY_CHARACTERS
				|| targetRequirement == TargetRequirement.ANY) {
			entities.add(opponent.getHero());
		}
		if (targetRequirement == TargetRequirement.ENEMY_MINIONS
				|| targetRequirement == TargetRequirement.ENEMY_CHARACTERS
				|| targetRequirement == TargetRequirement.ANY) {
			entities.addAll(opponent.getMinions());
		}
		if (targetRequirement == TargetRequirement.FRIENDLY_HERO || targetRequirement == TargetRequirement.FRIENDLY_CHARACTERS
				|| targetRequirement == TargetRequirement.ANY) {
			entities.add(player.getHero());
		}
		if (targetRequirement == TargetRequirement.FRIENDLY_MINIONS || targetRequirement == TargetRequirement.FRIENDLY_CHARACTERS
				|| targetRequirement == TargetRequirement.ANY) {
			entities.addAll(player.getMinions());
		}
		return entities;
	}
	
	private List<Entity> filterTargets(GameAction action, List<Entity> potentialTargets) {
		List<Entity> validTargets = new ArrayList<>();
		for (Entity entity : potentialTargets) {
			if (action.canBeExecutedOn(entity)) {
				validTargets.add(entity);
			}
		}
		return validTargets;
	}

	private boolean containsTaunters(List<Minion> minions) {
		for (Entity entity : minions) {
			if (entity.hasTag(GameTag.TAUNT)) {
				return true;
			}
		}
		return false;
	}

	private List<Entity> getTaunters(List<Minion> entities) {
		List<Entity> taunters = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.hasTag(GameTag.TAUNT)) {
				taunters.add(entity);
			}
		}
		return taunters;
	}

}
