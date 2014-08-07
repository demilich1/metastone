package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Vanish extends SpellCard {

	public Vanish() {
		super("Vanish", Rarity.FREE, HeroClass.ROGUE, 6);
		setDescription("Return all minions to their owner's hand. ");
		setSpell(ReturnMinionToHandSpell.create());
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.ENEMY_MINIONS);
	}



	@Override
	public int getTypeId() {
		return 308;
	}
}
