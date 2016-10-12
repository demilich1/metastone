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
			receiveCard(player.getId(), theCoin);
		}
	}

}
