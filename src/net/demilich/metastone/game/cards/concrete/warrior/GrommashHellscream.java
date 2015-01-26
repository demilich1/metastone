package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.enrage.Enrage;

public class GrommashHellscream extends MinionCard {

	public GrommashHellscream() {
		super("Grommash Hellscream", 4, 9, Rarity.LEGENDARY, HeroClass.WARRIOR, 8);
		setDescription("Charge. Enrage: +6 Attack ");
	}

	@Override
	public int getTypeId() {
		return 372;
	}

	@Override
	public Minion summon() {
		Minion grommashHellscream = createMinion(GameTag.CHARGE);
		grommashHellscream.setTag(GameTag.ENRAGE_SPELL, Enrage.create(6));
		return grommashHellscream;
	}
}
