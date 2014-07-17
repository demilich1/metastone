package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Pyroblast extends SpellCard {

	public Pyroblast() {
		super("Pyroblast", Rarity.EPIC, HeroClass.MAGE, 10);
		setDescription("Deal $10 damage.");
		setSpell(new DamageSpell(10));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 71;
	}
}
