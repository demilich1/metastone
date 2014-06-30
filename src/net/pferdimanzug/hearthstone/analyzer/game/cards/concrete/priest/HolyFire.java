package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HolyFire extends SpellCard {

	public HolyFire() {
		super("Holy Fire", Rarity.RARE, HeroClass.PRIEST, 6);
		setDescription("Deal $5 damage. Restore 5 Health to your hero.");

		Spell damageSpell = new DamageSpell(5);
		Spell healHeroSpell = new HealingSpell(5);
		healHeroSpell.setTarget(EntityReference.FRIENDLY_HERO);

		setSpell(new MetaSpell(damageSpell, healHeroSpell));
		setTargetRequirement(TargetSelection.ANY);
	}

}
