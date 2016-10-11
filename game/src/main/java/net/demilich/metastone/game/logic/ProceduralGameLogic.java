package net.demilich.metastone.game.logic;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.ProceduralPlayer;
import net.demilich.metastone.game.behaviour.human.HumanBehaviour;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.events.GameStartEvent;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;

public class ProceduralGameLogic extends GameLogic {
	public static final int STARTER_CARDS = 4;
	public static final int BENCH_SIZE = 4;

	
	@Override
	public void init(int playerId, boolean begins) {
		Player player = context.getPlayer(playerId);
		player.getHero().setId(idFactory.generateId());
		player.getHero().setOwner(player.getId());
		player.getHero().setMaxHp(player.getHero().getAttributeValue(Attribute.BASE_HP));
		player.getHero().setHp(player.getHero().getAttributeValue(Attribute.BASE_HP));

		player.getHero().getHeroPower().setId(idFactory.generateId());
		assignCardIds(player.getDeck());
		assignCardIds(player.getHand());

		log("Setting hero hp to {} for {}", player.getHero().getHp(), player.getName());
		player.getDeck().shuffle();

		mulligan(player, begins);

		for (Card card : player.getDeck()) {
			if (card.getAttribute(Attribute.DECK_TRIGGER) != null) {
				TriggerDesc triggerDesc = (TriggerDesc) card.getAttribute(Attribute.DECK_TRIGGER);
				addGameEventListener(player, triggerDesc.create(), card);
			}
		}
		for (Card card : player.getHand()) {
			if (card.getAttribute(Attribute.DECK_TRIGGER) != null) {
				TriggerDesc triggerDesc = (TriggerDesc) card.getAttribute(Attribute.DECK_TRIGGER);
				addGameEventListener(player, triggerDesc.create(), card);
			}
		}

		GameStartEvent gameStartEvent = new GameStartEvent(context, player.getId());
		context.fireGameEvent(gameStartEvent);
	}

	
	@Override
	public Card drawCard(int playerId, Entity source) {
		Player player = context.getPlayer(playerId);
		CardCollection deck = player.getDeck();
		if (deck.isEmpty()) {
			Hero hero = player.getHero();
			int fatigue = player.hasAttribute(Attribute.FATIGUE) ? player.getAttributeValue(Attribute.FATIGUE) : 0;
			fatigue++;
			player.setAttribute(Attribute.FATIGUE, fatigue);
			damage(player, hero, fatigue, hero);
			log("{}'s deck is empty, taking {} fatigue damage!", player.getName(), fatigue);
			player.getStatistics().fatigueDamage(fatigue);
			return null;
		}

		Card card = deck.getRandom();
		return drawCard(playerId, card, source);
	}

	@Override
	public Card drawCard(int playerId, Card card, Entity source) {
		Player player = context.getPlayer(playerId);
		player.getStatistics().cardDrawn();
		player.getDeck().remove(card);
		receiveCard(playerId, card, source, true);
		return card;
	}
	
	@Override
	protected void mulligan(Player player, boolean begins) {
		int numberOfStarterCards = begins ? STARTER_CARDS : STARTER_CARDS;
		List<Card> starterCards = new ArrayList<>();
		ProceduralPlayer proceduralPlayer = (ProceduralPlayer) player;

		// draw cards from the bench on start.
		while (starterCards.size() < numberOfStarterCards) {
			Card randomCard = proceduralPlayer.getBench().getRandom();
			proceduralPlayer.getBench().remove(randomCard);
			starterCards.add(randomCard);
		}

		for (Card starterCard : starterCards) {
			if (starterCard != null) {
				receiveCard(proceduralPlayer.getId(), starterCard);
			}
		}
		if (!(proceduralPlayer.getBehaviour() instanceof HumanBehaviour)){
			//We still need this line to not break the AI's.
			proceduralPlayer.getBehaviour().mulligan(context, player, starterCards);
		}

		// second player gets the coin additionally
		if (!begins) {
			Card theCoin = CardCatalogue.getCardById("spell_the_coin");
			//receiveCard(player.getId(), theCoin);
		}
	}

}
