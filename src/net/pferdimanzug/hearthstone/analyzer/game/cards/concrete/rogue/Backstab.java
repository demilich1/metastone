package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Backstab extends SpellCard {

	public Backstab() {
		super("Backstab", Rarity.FREE, HeroClass.ROGUE, 0);
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(new DamageSpell(2));
	}

	@Override
	public boolean canBeCastOn(Entity target) {
		return target.getHp() == target.getMaxHp();
	}
	
	


}
