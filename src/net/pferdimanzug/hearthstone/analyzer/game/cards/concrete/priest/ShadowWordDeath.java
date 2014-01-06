package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;

public class ShadowWordDeath extends SpellCard {
	
	public ShadowWordDeath() {
		super("Shadow Word: Death", Rarity.FREE, HeroClass.PRIEST, 3);
		setSpell(new DestroySpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		return target.getAttack() >= 5;
	}

}
