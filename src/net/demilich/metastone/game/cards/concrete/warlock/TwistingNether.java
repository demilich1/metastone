package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class TwistingNether extends SpellCard {

	public TwistingNether() {
		super("Twisting Nether", Rarity.EPIC, HeroClass.WARLOCK, 8);
		setDescription("Destroy all minions.");
		
		setSpell(DestroySpell.create(EntityReference.ALL_MINIONS));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 356;
	}
}
