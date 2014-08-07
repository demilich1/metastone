package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ShadowflameSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
