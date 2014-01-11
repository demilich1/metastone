package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;


public class DaggerMastery extends HeroPower {

	private class WickedKnife extends WeaponCard {

		public WickedKnife() {
			super("Wicked Knife", Rarity.FREE, HeroClass.ROGUE, 1);
			setCollectible(false);
		}

		@Override
		public Weapon getWeapon() {
			return createWeapon(1, 2);
		}
		
	}
	
	public DaggerMastery() {
		super("Dagger Mastery");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new EquipWeaponSpell(new WickedKnife()));
		
	}


}
