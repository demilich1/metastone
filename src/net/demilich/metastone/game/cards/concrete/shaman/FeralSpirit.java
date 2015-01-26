package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.shaman.SpiritWolf;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FeralSpirit extends SpellCard {

	public FeralSpirit() {
		super("Feral Spirit", Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("Summon two 2/3 Spirit Wolves with Taunt. Overload: (2)");

		setTag(GameTag.OVERLOAD, 2);
		setSpell(SummonSpell.create(new SpiritWolf(), new SpiritWolf()));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 318;
	}
}
