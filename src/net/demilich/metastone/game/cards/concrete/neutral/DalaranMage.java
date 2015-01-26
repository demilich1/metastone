package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class DalaranMage extends MinionCard {

	public DalaranMage() {
		super("Dalaran Mage", 1, 4, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Spell Damage +1");
	}

	@Override
	public int getTypeId() {
		return 111;
	}



	@Override
	public Minion summon() {
		Minion dalaranMage = createMinion();
		dalaranMage.setTag(GameTag.SPELL_POWER, 1);
		return dalaranMage;
	}
}
