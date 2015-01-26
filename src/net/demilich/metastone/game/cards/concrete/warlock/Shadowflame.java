package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.ShadowflameSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Shadowflame extends SpellCard {

	public Shadowflame() {
		super("Shadowflame", Rarity.RARE, HeroClass.WARLOCK, 4);
		setDescription("Destroy a friendly minion and deal its Attack damage to all enemy minions.");

		setSpell(ShadowflameSpell.create());
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 351;
	}

	
}
