package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.filter.RaceFilter;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class TotemicMight extends SpellCard {

	public TotemicMight() {
		super("Totemic Might", Rarity.FREE, HeroClass.SHAMAN, 0);
		setDescription("Give your Totems +2 Health.");
		SpellDesc totemBuff = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, 0, +2, new RaceFilter(Race.TOTEM), false);
		setSpell(totemBuff);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 330;
	}

}
