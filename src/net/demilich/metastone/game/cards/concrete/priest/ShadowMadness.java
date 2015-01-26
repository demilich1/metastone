package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.custom.ShadowMadnessSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
