package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Execute extends SpellCard {

	public Execute() {
		super("Execute", Rarity.FREE, HeroClass.WARRIOR, 1);
		setDescription("Destroy a damaged enemy minion.");
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
		setSpell(DestroySpell.create());
	}
	
	@Override
	public boolean canBeCastOn(Entity target) {
		Actor actor = (Actor) target;
		return actor.isWounded();
	}

	@Override
	public int getTypeId() {
		return 368;
	}
}
