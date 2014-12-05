package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.BouncingBladeSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BouncingBlade extends SpellCard {

	public BouncingBlade() {
		super("Bouncing Blade", Rarity.EPIC, HeroClass.WARRIOR, 3);
		setDescription("Deals $1 damage to a random minion. Repeat until a minion dies.");
		
		setSpell(BouncingBladeSpell.create());
		setPredefinedTarget(EntityReference.ALL_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 603;
	}
}
