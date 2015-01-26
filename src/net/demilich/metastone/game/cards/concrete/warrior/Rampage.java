package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Rampage extends SpellCard {

	public Rampage() {
		super("Rampage", Rarity.COMMON, HeroClass.WARRIOR, 2);
		setDescription("Give a damaged minion +3/+3.");
		setSpell(BuffSpell.create(3, 3));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		Actor targetActor = (Actor) target;
		return targetActor.isWounded();
	}
	
	



	@Override
	public int getTypeId() {
		return 377;
	}
}
