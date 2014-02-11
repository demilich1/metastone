package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ActionLogic {

	private final TargetLogic targetLogic = new TargetLogic();

	private GameAction getHeroAttackAction(GameContext context, Player player) {
		Hero hero = player.getHero();
		if (!hero.canAttackThisTurn()) {
			return null;
		}
		return new PhysicalAttackAction(hero.getReference());
	}

	private GameAction getHeroPowerAction(GameContext context, Player player) {
		Hero hero = player.getHero();
		CardReference heroPowerReference = new CardReference(player.getId(), CardLocation.HERO_POWER, hero.getHeroPower().getId());
		if (!context.getLogic().canPlayCard(player.getId(), heroPowerReference)) {
			return null;
		}
		return hero.getHeroPower().play();
	}

	private List<GameAction> getPhysicalAttackActions(GameContext context, Player player) {
		List<GameAction> physicalAttackActions = new ArrayList<GameAction>();
		GameAction heroAttackAction = getHeroAttackAction(context, player);
		if (validateAction(context, player, heroAttackAction)) {
			physicalAttackActions.add(heroAttackAction);
		}
		for (Minion minion : player.getMinions()) {
			if (!minion.canAttackThisTurn()) {
				continue;
			}
			PhysicalAttackAction minionAttackAction = new PhysicalAttackAction(minion.getReference());
			if (validateAction(context, player, minionAttackAction)) {
				physicalAttackActions.add(minionAttackAction);
			}
		}
		return physicalAttackActions;
	}

	private List<GameAction> getPlayCardActions(GameContext context, Player player) {
		List<GameAction> playCardActions = new ArrayList<GameAction>();
		GameAction heroPowerAction = getHeroPowerAction(context, player);
		if (validateAction(context, player, heroPowerAction)) {
			playCardActions.add(heroPowerAction);
		}
		for (Card card : player.getHand()) {
			CardReference cardReference = new CardReference(player.getId(), CardLocation.HAND, card.getId());
			if (!context.getLogic().canPlayCard(player.getId(), cardReference)) {
				continue;
			}
			
			GameAction playCardAction = card.play();
			if (validateAction(context, player, playCardAction)) {
				playCardActions.add(playCardAction);
			}
			
		}
		return playCardActions;
	}
	
	public List<GameAction> getValidActions(GameContext context, Player player) {
		List<GameAction> validActions = new ArrayList<GameAction>();
		validActions.addAll(getPhysicalAttackActions(context, player));
		validActions.addAll(getPlayCardActions(context, player));
		return validActions;
	}

	private boolean validateAction(GameContext context, Player player, GameAction action) {
		if (action == null) {
			return false;
		}
		if (action.getTargetRequirement() == TargetSelection.NONE) {
			return true;
		}
		List<Actor> validTargets = targetLogic.getValidTargets(context, player, action);
		if (validTargets.isEmpty()) {
			return false;
		}
		action.setValidTargets(validTargets);
		return true;
	}

}
