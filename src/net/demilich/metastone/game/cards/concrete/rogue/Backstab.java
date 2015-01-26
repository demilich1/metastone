package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Backstab extends SpellCard {

	public Backstab() {
		super("Backstab", Rarity.FREE, HeroClass.ROGUE, 0);
		setDescription("Deal $2 damage to an undamaged minion.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(DamageSpell.create(2));
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		Actor targetActor = (Actor) target;
		return targetActor.getHp() == targetActor.getMaxHp();
	}
	
	




	@Override
	public int getTypeId() {
		return 286;
	}
}
