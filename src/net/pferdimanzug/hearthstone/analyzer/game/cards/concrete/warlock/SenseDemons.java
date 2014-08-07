package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.SenseDemonsSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SenseDemons extends SpellCard {

	public SenseDemons() {
		super("Sense Demons", Rarity.COMMON, HeroClass.WARLOCK, 3);
		setDescription("Put 2 random Demons from your deck into your hand.");
		setSpell(SenseDemonsSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 349;
	}

}
