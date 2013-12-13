package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RandomDamageSpell;

public class ArcaneMissiles extends SpellCard {

	public ArcaneMissiles() {
		super("Arcane Missiles", Rarity.FREE, HeroClass.MAGE, 1);
		setTargetRequirement(TargetRequirement.NONE);
		setSpell(new RandomDamageSpell(1, 3));
	}
}
