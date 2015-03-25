package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class MiniMage extends MinionCard {

	public MiniMage() {
		super("Mini-Mage", 4, 1, Rarity.EPIC, HeroClass.ANY, 4);
		setDescription("Stealth. Spell Damage +1");
	}

	@Override
	public int getTypeId() {
		return 535;
	}



	@Override
	public Minion summon() {
		Minion miniMage = createMinion(GameTag.STEALTH);
		miniMage.setTag(GameTag.SPELL_POWER, +1);
		return miniMage;
	}
}
