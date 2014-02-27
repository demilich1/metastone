package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HammerOfWrath extends SpellCard {

	public HammerOfWrath() {
		super("Hammer of Wrath", Rarity.FREE, HeroClass.PALADIN, 4);
		setDescription("Deal $3 damage. Draw a card.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new MetaSpell(new DamageSpell(3), new DrawCardSpell()));
	}
	

}
