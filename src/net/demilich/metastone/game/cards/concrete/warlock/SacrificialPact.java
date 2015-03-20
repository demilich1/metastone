package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SacrificialPact extends SpellCard {

	public SacrificialPact() {
		super("Sacrificial Pact", Rarity.FREE, HeroClass.WARLOCK, 0);
		setDescription("Destroy a Demon. Restore #5 Health to your hero.");
		SpellDesc destroyDemon = DestroySpell.create();
		SpellDesc heal = HealingSpell.create(EntityReference.FRIENDLY_HERO, 5);
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
