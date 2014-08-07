package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TwistingNether extends SpellCard {

	public TwistingNether() {
		super("Twisting Nether", Rarity.EPIC, HeroClass.WARLOCK, 8);
		setDescription("Destroy all minions.");
		
		setSpell(DestroySpell.create());
		setPredefinedTarget(EntityReference.ALL_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 356;
	}
}
