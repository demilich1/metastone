package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DrainLife extends SpellCard {

	public DrainLife() {
		super("Drain Life", Rarity.FREE, HeroClass.WARLOCK, 3);
		setDescription("Deal $2 damage. Restore #2 Health to your hero.");
		Spell damage = new DamageSpell(2);
		Spell heal = new HealingSpell(2);
		heal.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(new MetaSpell(damage, heal));
		//TODO: can this be cast on own hero?
		setTargetRequirement(TargetSelection.ANY);
		
	}

}
