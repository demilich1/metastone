package net.demilich.metastone.game.logic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class TargetLogic {

	private static Logger logger = LoggerFactory.getLogger(TargetLogic.class);

	private static List<Entity> singleTargetAsList(Entity target) {
		ArrayList<Entity> list = new ArrayList<>(1);
		list.add(target);
		return list;
	}

	private boolean containsTaunters(List<Minion> minions) {
		for (Entity entity : minions) {
			if (entity.hasAttribute(Attribute.TAUNT) && !entity.hasAttribute(Attribute.STEALTH) && !entity.hasAttribute(Attribute.IMMUNE)) {
				return true;
			}
		}
		return false;
	}

	private List<Entity> filterTargets(GameContext context, Player player, GameAction action, List<Entity> potentialTargets) {
		List<Entity> validTargets = new ArrayList<>();
		for (Entity entity : potentialTargets) {
			// special case for 'SYSTEM' action, which are used in Sandbox Mode
			// we do not want to restrict those actions by STEALTH or
			// UNTARGETABLE_BY_SPELLS
			if (action.getActionType() == ActionType.SYSTEM && action.canBeExecutedOn(context, player, entity)) {
				validTargets.add(entity);
				continue;
			}
			if ((action.getActionType() == ActionType.SPELL || action.getActionType() == ActionType.HERO_POWER)
					&& entity.hasAttribute(Attribute.UNTARGETABLE_BY_SPELLS)) {
				continue;
			}

			if (entity.getOwner() != player.getId() && (entity.hasAttribute(Attribute.STEALTH) || entity.hasAttribute(Attribute.IMMUNE))) {
				continue;
			}
			if (entity.getOwner() != player.getId() && entity instanceof Hero && context.getLogic().hasAttribute(context.getPlayer(entity.getOwner()), Attribute.IMMUNE_HERO)) {
				continue;
			}

			if (action.canBeExecutedOn(context, player, entity)) {
				validTargets.add(entity);
			}
		}
		return validTargets;
	}

	public Entity findEntity(GameContext context, EntityReference targetKey) {
		int targetId = targetKey.getId();
		Entity environmentResult = findInEnvironment(context, targetKey);
		if (environmentResult != null) {
			return environmentResult;
		}
		for (Player player : context.getPlayers()) {
			if (player.getHero().getId() == targetId) {
				return player.getHero();
			} else if (player.getHero().getWeapon() != null && player.getHero().getWeapon().getId() == targetId) {
				return player.getHero().getWeapon();
			} else if (player.getHero().getDestroyedWeapon() != null && player.getHero().getDestroyedWeapon().getId() == targetId) {
				return player.getHero().getDestroyedWeapon();
			}

			for (Actor minion : player.getMinions()) {
				if (minion.getId() == targetId) {
					return minion;
				}
			}

			for (Entity entity : player.getGraveyard()) {
				if (entity.getId() == targetId) {
					return entity;
				}
			}
			for (Entity entity : player.getSetAsideZone()) {
				if (entity.getId() == targetId) {
					return entity;
				}
			}
		}

		Entity cardResult = findInCards(context.getPlayer1(), targetId);
		if (cardResult == null) {
			cardResult = findInCards(context.getPlayer2(), targetId);
		}
		if (cardResult != null) {
			return cardResult;
		}

		logger.error("Id " + targetId + " not found!");
		logger.error(context.toString());
		logger.error(context.getEnvironment().toString());
		throw new RuntimeException("Target not found exception: " + targetKey);
	}

	private Entity findInCards(Player player, int targetId) {
		if (player.getHero().getHeroPower().getId() == targetId) {
			return player.getHero().getHeroPower();
		}
		for (Card card : player.getHand()) {
			if (card.getId() == targetId) {
				return card;
			}
		}
		for (Card card : player.getDeck()) {
			if (card.getId() == targetId) {
				return card;
			}
		}

		return null;
	}

	private Entity findInEnvironment(GameContext context, EntityReference targetKey) {
		int targetId = targetKey.getId();
		Card pendingCard = context.getPendingCard();
		if (pendingCard != null && pendingCard.getReference().equals(targetKey)) {
			return pendingCard;
		}
		if (context.getEnvironment().containsKey(Environment.SUMMONED_WEAPON) && targetKey == context.getEnvironment().get(Environment.SUMMONED_WEAPON)) {
			Actor summonedWeapon = (Actor) context.resolveSingleTarget((EntityReference) context.getEnvironment().get(Environment.SUMMONED_WEAPON));
			if (summonedWeapon.getId() == targetId) {
				return summonedWeapon;
			}
		}
		if (!context.getEventTargetStack().isEmpty() && targetKey == EntityReference.EVENT_TARGET) {
			return context.resolveSingleTarget(context.getEventTargetStack().peek());
		}
		return null;
	}

	private List<Entity> getEntities(GameContext context, Player player, TargetSelection targetRequirement) {
		Player opponent = context.getOpponent(player);
		List<Entity> entities = new ArrayList<>();
		if (targetRequirement == TargetSelection.ENEMY_HERO || targetRequirement == TargetSelection.ENEMY_CHARACTERS
				|| targetRequirement == TargetSelection.ANY || targetRequirement == TargetSelection.HEROES) {
			entities.add(opponent.getHero());
		}
		if (targetRequirement == TargetSelection.ENEMY_MINIONS || targetRequirement == TargetSelection.ENEMY_CHARACTERS
				|| targetRequirement == TargetSelection.MINIONS || targetRequirement == TargetSelection.ANY) {
			entities.addAll(opponent.getMinions());
		}
		if (targetRequirement == TargetSelection.FRIENDLY_HERO || targetRequirement == TargetSelection.FRIENDLY_CHARACTERS
				|| targetRequirement == TargetSelection.ANY || targetRequirement == TargetSelection.HEROES) {
			entities.add(player.getHero());
		}
		if (targetRequirement == TargetSelection.FRIENDLY_MINIONS || targetRequirement == TargetSelection.FRIENDLY_CHARACTERS
				|| targetRequirement == TargetSelection.MINIONS || targetRequirement == TargetSelection.ANY) {
			entities.addAll(player.getMinions());
		}
		List<Entity> destroyedEntities = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity != null && entity.hasAttribute(Attribute.PENDING_DESTROY)) {
				destroyedEntities.add(entity);
			}
		}
		entities.removeAll(destroyedEntities);
		return entities;
	}

	private List<Entity> getTaunters(List<Minion> entities) {
		List<Entity> taunters = new ArrayList<>();
		for (Actor entity : entities) {
			if (entity.hasAttribute(Attribute.TAUNT) && !entity.hasAttribute(Attribute.STEALTH) && !entity.hasAttribute(Attribute.IMMUNE)) {
				taunters.add(entity);
			}
		}
		return taunters;
	}

	public List<Entity> getValidTargets(GameContext context, Player player, GameAction action) {
		TargetSelection targetRequirement = action.getTargetRequirement();
		ActionType actionType = action.getActionType();
		Player opponent = context.getOpponent(player);

		// if there is a minion with TAUNT and the action is of type physical
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
			List<Entity> summonTargets = getEntities(context, player, targetRequirement);
			summonTargets.add(null);
			return summonTargets;
		}
		List<Entity> potentialTargets = getEntities(context, player, targetRequirement);
		return filterTargets(context, player, action, potentialTargets);
	}

	public List<Entity> resolveTargetKey(GameContext context, Player player, Entity source, EntityReference targetKey) {
		if (targetKey == null || targetKey == EntityReference.NONE) {
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
			List<Entity> targets = getEntities(context, player, TargetSelection.FRIENDLY_MINIONS);
			targets.remove(source);
			return targets;
		} else if (targetKey == EntityReference.ALL_OTHER_CHARACTERS) {
			List<Entity> targets = getEntities(context, player, TargetSelection.ANY);
			targets.remove(source);
			return targets;
		} else if (targetKey == EntityReference.ALL_OTHER_MINIONS) {
			List<Entity> targets = getEntities(context, player, TargetSelection.MINIONS);
			targets.remove(source);
			return targets;
		} else if (targetKey == EntityReference.ADJACENT_MINIONS) {
			return new ArrayList<>(context.getAdjacentMinions(player, source.getReference()));
		} else if (targetKey == EntityReference.SELF) {
			return singleTargetAsList(source);
		} else if (targetKey == EntityReference.EVENT_TARGET) {
			return singleTargetAsList(context.resolveSingleTarget(context.getEventTargetStack().peek()));
		} else if (targetKey == EntityReference.TARGET) {
			return singleTargetAsList(context.resolveSingleTarget((EntityReference) context.getEnvironment().get(Environment.TARGET)));
		} else if (targetKey == EntityReference.KILLED_MINION) {
			return singleTargetAsList(context.resolveSingleTarget((EntityReference) context.getEnvironment().get(Environment.KILLED_MINION)));
		} else if (targetKey == EntityReference.ATTACKER_REFERENCE) {
			return singleTargetAsList(context.resolveSingleTarget((EntityReference) context.getEnvironment().get(Environment.ATTACKER_REFERENCE)));
		} else if (targetKey == EntityReference.PENDING_CARD) {
			return singleTargetAsList((Entity) context.getPendingCard());
		} else if (targetKey == EntityReference.FRIENDLY_WEAPON) {
			if (player.getHero().getWeapon() != null) {
				return singleTargetAsList(player.getHero().getWeapon());
			} else {
				return new ArrayList<>();
			}
		} else if (targetKey == EntityReference.ENEMY_WEAPON) {
			Player opponent = context.getOpponent(player);
			if (opponent.getHero().getWeapon() != null) {
				return singleTargetAsList(opponent.getHero().getWeapon());
			} else {
				return new ArrayList<>();
			}
		} else if (targetKey == EntityReference.FRIENDLY_HAND) {
			return new ArrayList<>(player.getHand().toList());
		} else if (targetKey == EntityReference.ENEMY_HAND) {
			return new ArrayList<>(context.getOpponent(player).getHand().toList());
		}

		return singleTargetAsList(findEntity(context, targetKey));
	}

}
