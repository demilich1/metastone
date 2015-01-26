package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SiphonSoul extends SpellCard {

	public SiphonSoul() {
		super("Siphon Soul", Rarity.RARE, HeroClass.WARLOCK, 6);
		setDescription("Destroy a minion. Restore 3 Health to your hero.");

		SpellDesc destroySpell = DestroySpell.create();
		SpellDesc healingSpell = HealingSpell.create(3);
		healingSpell.setTarget(EntityReference.FRIENDLY_HERO);
		setSpell(MetaSpell.create(destroySpell, healingSpell));

		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 352;
	}
}
