package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageAndDrawCardSpell;

public class HammerOfWrath extends SpellCard {

	public HammerOfWrath() {
		super("Hammer of Wrath", Rarity.FREE, HeroClass.PALADIN, 4);
		setTargetRequirement(TargetRequirement.ANY);
		setSpell(new DamageAndDrawCardSpell(3));
	}
	

}
