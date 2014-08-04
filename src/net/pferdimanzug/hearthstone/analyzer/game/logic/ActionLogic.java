package net.pferdimanzug.hearthstone.analyzer.game.logic;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.EndTurnAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.IChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;

public class ActionLogic {

	private final TargetLogic targetLogic = new TargetLogic();

	private List<GameAction> getHeroAttackActions(GameContext context, Player player) {
		List<GameAction> heroAttackActions = new ArrayList<GameAction>();
		Hero hero = player.getHero();
		if (!hero.canAttackThisTurn()) {
			return heroAttackActions;
		}
		
		for (Entity validTarget : targetLogic.getValidTargets(context, player, new PhysicalAttackAction(hero.getReference()))) {
			GameAction heroAttackAction = new PhysicalAttackAction(hero.getReference());
			heroAttackAction.setTarget(validTarget);
			heroAttackActions.add(heroAttackAction);
		}
		return heroAttackActions;
	}

	private List<GameAction> getHeroPowerActions(GameContext context, Player player) {
		List<GameAction> heroPowerActions = new ArrayList<GameAction>();
		Hero hero = player.getHero();
		CardReference heroPowerReference = new CardReference(player.getId(), CardLocation.HERO_POWER, hero.getHeroPower().getId());
		if (!context.getLogic().canPlayCard(player.getId(), heroPowerReference)) {
			return heroPowerActions;
		}
		for (Entity validTarget : targetLogic.getValidTargets(context, player, hero.getHeroPower().play())) {
			GameAction heroPowerAction = hero.getHeroPower().play();
			heroPowerAction.setTarget(validTarget);
			heroPowerActions.add(heroPowerAction);
		}
		return heroPowerActions;
	}

	private List<GameAction> getPhysicalAttackActions(GameContext context, Player player) {
		List<GameAction> physicalAttackActions = new ArrayList<GameAction>();
		physicalAttackActions.addAll(getHeroAttackActions(context, player));
		
		for (Minion minion : player.getMinions()) {
			if (!minion.canAttackThisTurn()) {
				continue;
			}
			
			for (Entity validTarget : targetLogic.getValidTargets(context, player, new PhysicalAttackAction(minion.getReference()))) {
				PhysicalAttackAction minionAttackAction = new PhysicalAttackAction(minion.getReference());
				minionAttackAction.setTarget(validTarget);
				physicalAttackActions.add(minionAttackAction);
			}
		}
		return physicalAttackActions;
	}
	
	private List<GameAction> getPlayCardActions(GameContext context, Player player) {
		List<GameAction> playCardActions = new ArrayList<GameAction>();
		playCardActions.addAll(getHeroPowerActions(context, player));
		
		for (Card card : player.getHand()) {
			CardReference cardReference = new CardReference(player.getId(), CardLocation.HAND, card.getId());
			if (!context.getLogic().canPlayCard(player.getId(), cardReference)) {
				continue;
			}

			if (card.hasTag(GameTag.CHOOSE_ONE)) {
				IChooseOneCard chooseOneCard = (IChooseOneCard) card;
				for (Entity validTarget : targetLogic.getValidTargets(context, player, chooseOneCard.playOption1())) {
					GameAction playCardAction = chooseOneCard.playOption1();
					playCardAction.setTarget(validTarget);
					playCardActions.add(playCardAction);
				}
				for (Entity validTarget : targetLogic.getValidTargets(context, player, chooseOneCard.playOption2())) {
					GameAction playCardAction = chooseOneCard.playOption2();
					playCardAction.setTarget(validTarget);
					playCardActions.add(playCardAction);
				}
			} else {
				for (Entity validTarget : targetLogic.getValidTargets(context, player, card.play())) {
					GameAction playCardAction = card.play();
					playCardAction.setTarget(validTarget);
					playCardActions.add(playCardAction);
				}
				
			}
		}
		return playCardActions;
	}
	
	public List<GameAction> getValidActions(GameContext context, Player player) {
		List<GameAction> validActions = new ArrayList<GameAction>();
		validActions.addAll(getPhysicalAttackActions(context, player));
		validActions.addAll(getPlayCardActions(context, player));
		validActions.add(new EndTurnAction());
		return validActions;
	}

}
