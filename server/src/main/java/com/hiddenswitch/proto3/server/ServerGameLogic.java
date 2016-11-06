package com.hiddenswitch.proto3.server;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.ProceduralPlayer;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.human.HumanBehaviour;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ServerGameLogic extends GameLogic {
	
	@Override
	protected void mulligan(Player player, boolean begins) {
		ServerGameContext serverContext = (ServerGameContext) context;
		int numberOfStarterCards = begins ? STARTER_CARDS : STARTER_CARDS + 1;
		List<Card> starterCards = new ArrayList<>();
		for (int j = 0; j < numberOfStarterCards; j++) {
			Card randomCard = player.getDeck().getRandom();
			if (randomCard != null) {
				player.getDeck().remove(randomCard);
				log("Player {} been offered card {} for mulligan", player.getName(), randomCard);
				starterCards.add(randomCard);
			}
		}
		serverContext.mulligan(player, starterCards, new Consumer<List<Card>>(){
			@Override
			public void accept(List<Card> discardedCards) {
				// remove player selected cards from starter cards
				for (Card discardedCard : discardedCards) {
					log("Player {} mulligans {} ", player.getName(), discardedCard);
					starterCards.remove(discardedCard);
				}

				// draw random cards from deck until required starter card count is
				// reached
				while (starterCards.size() < numberOfStarterCards) {
					Card randomCard = player.getDeck().getRandom();
					player.getDeck().remove(randomCard);
					starterCards.add(randomCard);
				}

				// put the mulligan cards back in the deck
				for (Card discardedCard : discardedCards) {
					player.getDeck().add(discardedCard);
				}

				for (Card starterCard : starterCards) {
					if (starterCard != null) {
						receiveCard(player.getId(), starterCard);
					}
				}

				// second player gets the coin additionally
			
				if (!begins) {
					Card theCoin = CardCatalogue.getCardById("spell_the_coin");
					receiveCard(player.getId(), theCoin);
				}			
				
			}
		});
	}
	
	@Override
	protected void resolveBattlecry(int playerId, Actor actor) {
		BattlecryAction battlecry = actor.getBattlecry();
		Player player = context.getPlayer(playerId);
		if (!battlecry.canBeExecuted(context, player)) {
			return;
		}

		GameAction battlecryAction = null;
		battlecry.setSource(actor.getReference());
		if (battlecry.getTargetRequirement() != TargetSelection.NONE) {
			List<Entity> validTargets = targetLogic.getValidTargets(context, player, battlecry);
			if (validTargets.isEmpty()) {
				return;
			}

			List<GameAction> battlecryActions = new ArrayList<>();
			for (Entity validTarget : validTargets) {
				GameAction targetedBattlecry = battlecry.clone();
				targetedBattlecry.setTarget(validTarget);
				battlecryActions.add(targetedBattlecry);
			}
			ServerGameContext serverContext = (ServerGameContext) context;
			serverContext.requestBattlecry(player, battlecryActions);
			while (serverContext.battlecryAction == null){
				serverContext.cycleLock();
			}
			battlecryAction = serverContext.battlecryAction;
		} else {
			battlecryAction = battlecry;
		}
		if (hasAttribute(player, Attribute.DOUBLE_BATTLECRIES) && actor.getSourceCard().hasAttribute(Attribute.BATTLECRY)) {
			// You need DOUBLE_BATTLECRIES before your battlecry action, not after.
			performGameAction(playerId, battlecryAction);
			performGameAction(playerId, battlecryAction);
		} else {
			performGameAction(playerId, battlecryAction);
		}
	}


}
