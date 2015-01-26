package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ShadowWordPain extends SpellCard {

	public ShadowWordPain() {
		super("Shadow Word: Pain", Rarity.FREE, HeroClass.PRIEST, 2);
		setDescription("Destroy a minion with 3 or less Attack.");
		setSpell(DestroySpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		Actor targetActor = (Actor) target;
		return targetActor.getAttack() <= 3;
	}
	
	



	@Override
	public int getTypeId() {
		return 280;
	}
}
