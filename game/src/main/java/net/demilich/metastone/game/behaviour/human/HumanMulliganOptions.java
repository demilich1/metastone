package net.demilich.metastone.game.behaviour.human;

import java.util.List;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;

public class HumanMulliganOptions {

	private final Player player;
	private final HumanBehaviour behaviour;
	private final List<Card> offeredCards;

	public HumanMulliganOptions(Player player, HumanBehaviour behaviour, List<Card> offeredCards) {
		this.player = player;
		this.behaviour = behaviour;
		this.offeredCards = offeredCards;
	}

	public HumanBehaviour getBehaviour() {
		return behaviour;
	}

	public List<Card> getOfferedCards() {
		return offeredCards;
	}

	public Player getPlayer() {
		return player;
	}

}
