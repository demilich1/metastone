package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.SwipeSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
