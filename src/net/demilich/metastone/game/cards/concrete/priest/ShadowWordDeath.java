package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ShadowWordDeath extends SpellCard {
	
	public ShadowWordDeath() {
		super("Shadow Word: Death", Rarity.FREE, HeroClass.PRIEST, 3);
		setDescription("Destroy a minion with an Attack of 5 or more.");
		setSpell(DestroySpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		Actor targetActor = (Actor) target;
		return targetActor.getAttack() >= 5;
	}



	@Override
	public int getTypeId() {
		return 279;
	}
}
