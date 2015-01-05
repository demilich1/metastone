package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman.SpiritWolf;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
