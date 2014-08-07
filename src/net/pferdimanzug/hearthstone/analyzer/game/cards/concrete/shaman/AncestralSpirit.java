package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.AncestralSpiritSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
