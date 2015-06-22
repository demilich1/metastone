package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;

public class MinionCardDesc extends ActorCardDesc {
	
	public int baseAttack;
	public int baseHp;
	public Race race;
	public CardCostModifierDesc cardCostModifier;

	@Override
	public Card createInstance() {
		return new MinionCard(this);
	}

}
