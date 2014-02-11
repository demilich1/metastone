package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ShadowWordPain extends SpellCard {

	public ShadowWordPain() {
		super("Shadow Word: Pain", Rarity.FREE, HeroClass.PRIEST, 2);
		setSpell(new DestroySpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCastOn(Actor target) {
		return target.getAttack() <= 3;
	}
	
	

}
