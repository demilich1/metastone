package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Execute extends SpellCard {

	public Execute() {
		super("Execute", Rarity.FREE, HeroClass.WARRIOR, 1);
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
		setSpell(new DestroySpell());
	}
	
	public boolean canBeCastOn(Entity target) {
		return target.getHp() < target.getMaxHp();
	}

}
