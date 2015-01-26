package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HolyLight extends SpellCard {

	public HolyLight() {
		super("Holy Light", Rarity.FREE, HeroClass.PALADIN, 2);
		setDescription("Restore #6 Health.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(HealingSpell.create(6));
	}



	@Override
	public int getTypeId() {
		return 248;
	}
}
