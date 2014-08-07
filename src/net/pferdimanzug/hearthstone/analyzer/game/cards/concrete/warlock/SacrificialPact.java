package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SacrificialPact extends SpellCard {

	public SacrificialPact() {
		super("Sacrificial Pact", Rarity.FREE, HeroClass.WARLOCK, 0);
		setDescription("Destroy a Demon. Restore #5 Health to your hero.");
		SpellDesc destroyDemon = DestroySpell.create();
		SpellDesc heal = HealingSpell.create(5);
		heal.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(MetaSpell.create(destroyDemon, heal));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		return target.getTag(GameTag.RACE) == Race.DEMON;
	}

	@Override
	public int getTypeId() {
		return 348;
	}
}
