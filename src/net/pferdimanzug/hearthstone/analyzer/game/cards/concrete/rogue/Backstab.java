package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class Backstab extends SpellCard {

	public Backstab() {
		super("Backstab", Rarity.FREE, HeroClass.ROGUE, 0);
		setTargetRequirement(TargetRequirement.MINIONS);
		setEffectHint(EffectHint.NEGATIVE);
		setSpell(new SingleTargetDamageSpell(2));
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		return target.getHp() == target.getMaxHp();
	}
	
	


}
