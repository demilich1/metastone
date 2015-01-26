package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class Archmage extends MinionCard {
	
	public Archmage() {
		super("Archmage", 4, 7, Rarity.FREE, HeroClass.ANY, 6);
		setDescription("Spell Damage +1");
	}

	@Override
	public int getTypeId() {
		return 88;
	}



	@Override
	public Minion summon() {
		Minion archmage = createMinion();
		archmage.setTag(GameTag.SPELL_POWER, 1);
		return archmage;
	}
}
