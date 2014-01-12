package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DrainLife extends SpellCard {

	public DrainLife() {
		super("Drain Life", Rarity.FREE, HeroClass.WARLOCK, 3);
		Spell damage = new DamageSpell(2);
		Spell heal = new HealingSpell(2);
		heal.setTarget(TargetKey.FRIENDLY_HERO);
		setSpell(new MetaSpell(damage, heal));
		//TODO: can this be cast on own hero?
		setTargetRequirement(TargetSelection.ANY);
		
	}

}
