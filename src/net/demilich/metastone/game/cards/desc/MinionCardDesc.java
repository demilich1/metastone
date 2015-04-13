package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MinionCardDesc extends CardDesc {
	
	public int baseAttack;
	public int baseHp;
	public Race race;
	public BattlecryDesc battlecry;
	public SpellDesc deathrattle;

	@Override
	public Card createInstance() {
		return new MinionCard(this);
	}

}
