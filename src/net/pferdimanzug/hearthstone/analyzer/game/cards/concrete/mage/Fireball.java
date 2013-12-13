package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class Fireball extends SpellCard {
	
	public Fireball() {
		super("Fireball", Rarity.FREE, HeroClass.MAGE, 4);
		setTargetRequirement(TargetRequirement.ANY);
		setSpell(new SingleTargetDamageSpell(6));
	}

}
