package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetLogic {

	private static Logger logger = LoggerFactory.getLogger(TargetLogic.class);

	private boolean containsTaunters(List<Minion> minions) {
		for (Entity entity : minions) {
			if (entity.hasTag(GameTag.TAUNT)) {
				return true;
			}
		}
		return false;
	}

	private List<Actor> filterTargets(GameAction action, List<Actor> potentialTargets) {
		List<Actor> validTargets = new ArrayList<>();
		for (Actor entity : potentialTargets) {
			if ((action.getActionType() == ActionType.SPELL || action.getActionType() == ActionType.HERO_POWER)
					&& entity.hasTag(GameTag.UNTARGETABLE_BY_SPELLS)) {
				continue;
			}

			if (action.canBeExecutedOn(entity)) {
				validTargets.add(entity);
			}
		}
		return validTargets;
	}

	private Actor findEntity(GameContext context, EntityReference targetKey) {
		int targetId = targetKey.getId();
		for (Actor entity : context.getPendingEntities()) {
			if (entity.getId() == targetId) {
				return entity;
			}
		}
		for (Player player : context.getPlayers()) {

			if (player.getHero().getId() == targetId) {
				return player.getHero();
			} else if (player.getHero().getWeapon() != null && player.getHero().getWeapon().getId() == targetId) {
				return player.getHero().getWeapon();
			}

			for (Actor minion : player.getMinions()) {
				if (minion.getId() == targetId) {
					return minion;
				}
			}
		}

		logger.error("Id " + targetId + " not found!");
		throw new RuntimeException("Target not found exception");
		// return null;
	}

	private List<Actor> getEntities(GameContext context, Player player, TargetSelection targetRequirement) {
		Player opponent = context.getOpponent(player);
		List<Actor> entities = new ArrayList<>();
		if (targetRequirement == TargetSelection.ENEMY_HERO || targetRequirement == TargetSelection.ENEMY_CHARACTERS
				|| targetRequirement == TargetSelection.ANY) {
			entities.add(opponent.getHero());
		}
		if (targetRequirement == TargetSelection.ENEMY_MINIONS || targetRequirement == TargetSelection.ENEMY_CHARACTERS
				|| targetRequirement == TargetSelection.MINIONS || targetRequirement == TargetSelection.ANY) {
			entities.addAll(opponent.getMinions());
		}
		if (targetRequirement == TargetSelection.FRIENDLY_HERO
				|| targetRequirement == TargetSelection.FRIENDLY_CHARACTERS || targetRequirement == TargetSelection.ANY) {
			entities.add(player.getHero());
		}
		if (targetRequirement == TargetSelection.FRIENDLY_MINIONS
				|| targetRequirement == TargetSelection.FRIENDLY_CHARACTERS
				|| targetRequirement == TargetSelection.MINIONS || targetRequirement == TargetSelection.ANY) {
			entities.addAll(player.getMinions());
		}
		return entities;
	}

	private List<Actor> getTaunters(List<Minion> entities) {
		List<Actor> taunters = new ArrayList<Actor>();
		for (Actor entity : entities) {
			if (entity.hasTag(GameTag.TAUNT)) {
				taunters.add(entity);
			}
		}
		return taunters;
	}

	// Blue post:
	// Heroes and minions can attack any opposing character, be it minion or
	// hero.
	// Friendly minions cannot attack each other, but may target each other with
	// Battlecry effects as long as the ability does not specify friendly or
	// opposing.
	// http://www.blizzposts.com/topic/en/216948/the-consistency-of-rules
	public List<Actor> getValidTargets(GameContext context, Player player, GameAction action) {
		TargetSelection targetRequirement = action.getTargetRequirement();
		ActionType actionType = action.getActionType();
		Player opponent = context.getOpponent(player);

		// if there is a minion with TAUNT and the action is of type basic
		// attack only allow corresponding minions as targets
		if (actionType == ActionType.PHYSICAL_ATTACK
				&& (targetRequirement == TargetSelection.ENEMY_CHARACTERS || targetRequirement == TargetSelection.ENEMY_MINIONS)
				&& containsTaunters(opponent.getMinions())) {
			return getTaunters(opponent.getMinions());
		}
		if (actionType == ActionType.SUMMON) {
			// you can summon next to any friendly minion or provide no target
			// (=null)
			// in which case the minion will appear to the very right of your
			// board
			List<Actor> summonTargets = getEntities(context, player, action.getTargetRequirement());
			summonTargets.add(null);
			return summonTargets;
		}
		List<Actor> potentialTargets = getEntities(context, player, action.getTargetRequirement());
		return filterTargets(action, potentialTargets);
	}

	public List<Actor> resolveTargetKey(GameContext context, Player player, Actor source, EntityReference targetKey) {
		if (targetKey == null) {
			return null;
		}
		if (targetKey == EntityReference.ALL_CHARACTERS) {
			return getEntities(context, player, TargetSelection.ANY);
		} else if (targetKey == EntityReference.ALL_MINIONS) {
			return getEntities(context, player, TargetSelection.MINIONS);
		} else if (targetKey == EntityReference.ENEMY_CHARACTERS) {
			return getEntities(context, player, TargetSelection.ENEMY_CHARACTERS);
		} else if (targetKey == EntityReference.ENEMY_HERO) {
			return getEntities(context, player, TargetSelection.ENEMY_HERO);
		} else if (targetKey == EntityReference.ENEMY_MINIONS) {
			return getEntities(context, player, TargetSelection.ENEMY_MINIONS);
		} else if (targetKey == EntityReference.FRIENDLY_CHARACTERS) {
			return getEntities(context, player, TargetSelection.FRIENDLY_CHARACTERS);
		} else if (targetKey == EntityReference.FRIENDLY_HERO) {
			return getEntities(context, player, TargetSelection.FRIENDLY_HERO);
		} else if (targetKey == EntityReference.FRIENDLY_MINIONS) {
			return getEntities(context, player, TargetSelection.FRIENDLY_MINIONS);
		} else if (targetKey == EntityReference.OTHER_FRIENDLY_MINIONS) {
			List<Actor> targets = getEntities(context, player, TargetSelection.FRIENDLY_MINIONS);
			targets.remove(source);
			return targets;
		} else if (targetKey == EntityReference.ADJACENT_MINIONS) {
			System.out.println("Getting adjacent minions for " + source);
			return context.getAdjacentMinions(player, (Minion) source);
		} else if (targetKey == EntityReference.NONE) {
			return null;
		}

		ArrayList<Actor> target = new ArrayList<>(1);
		target.add(findEntity(context, targetKey));
		return target;

	}

}
