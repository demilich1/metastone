package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SacrificialPact extends SpellCard {

	public SacrificialPact() {
		super("Sacrificial Pact", Rarity.FREE, HeroClass.WARLOCK, 0);
		Spell destroyDemon = new DestroySpell();
		Spell heal = new HealingSpell(5);
		heal.setTarget(TargetKey.FRIENDLY_HERO);
		setSpell(new MetaSpell(destroyDemon, heal));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		return target.getRace() == Race.DEMON;
	}
	
	

}
