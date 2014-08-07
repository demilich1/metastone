package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ShadowMadnessSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ShadowMadness extends SpellCard {

	public ShadowMadness() {
		super("Shadow Madness", Rarity.RARE, HeroClass.PRIEST, 4);
		setDescription("Gain control of an enemy minion with 3 or less Attack until end of turn.");
		
		setSpell(ShadowMadnessSpell.create());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}
	
	@Override
	public boolean canBeCastOn(Entity target) {
		if (!super.canBeCastOn(target)) {
			return false;
		}
		Minion minion = (Minion) target;
		return minion.getAttack() <= 3;
	}


	@Override
	public int getTypeId() {
		return 278;
	}
	
}
