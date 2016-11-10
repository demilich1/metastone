package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SpellUtils;

public class HoldsCardCondition extends Condition {

	public HoldsCardCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		CardType cardType = (CardType) desc.get(ConditionArg.CARD_TYPE);
		if (cardType != null && !SpellUtils.holdsCardOfType(player, cardType)) {
			return false;
		}
		Race race = (Race) desc.get(ConditionArg.RACE);
		if (race != null && !SpellUtils.holdsMinionOfRace(player, race)) {
			return false;
		}
		return true;
	}

}
