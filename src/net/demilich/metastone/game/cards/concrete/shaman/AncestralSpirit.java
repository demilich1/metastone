package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.AncestralSpiritSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AncestralSpirit extends SpellCard {

	public AncestralSpirit() {
		super("Ancestral Spirit", Rarity.RARE, HeroClass.SHAMAN, 2);
		setDescription("Choose a minion. When that minion is destroyed, return it to the battlefield.");

		setSpell(AncestralSpiritSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 311;
	}

	
}
