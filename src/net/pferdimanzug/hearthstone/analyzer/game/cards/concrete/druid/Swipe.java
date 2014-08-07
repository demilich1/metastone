package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.SwipeSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Swipe extends SpellCard {

	public Swipe() {
		super("Swipe", Rarity.FREE, HeroClass.DRUID, 4);
		setDescription("Deal $4 damage to an enemy and $1 damage to all other enemies.");
		setSpell(SwipeSpell.create());
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
	}

	@Override
	public int getTypeId() {
		return 23;
	}

	
}
