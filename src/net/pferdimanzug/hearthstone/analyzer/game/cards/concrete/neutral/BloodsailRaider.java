package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BloodsailRaider extends MinionCard {

	private class CopyWeaponAttack extends BuffSpell {
		
		public CopyWeaponAttack() {
			super(0, 0);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Weapon weapon = player.getHero().getWeapon();
			if (weapon != null) {
				setAttackBonus(weapon.getWeaponDamage());
			}
			super.onCast(context, player, target);
		}
		
	}

	public BloodsailRaider() {
		super("Bloodsail Raider", 2, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Gain Attack equal to the Attack of your weapon.");
		setTag(GameTag.RACE, Race.PIRATE);
	}
	
	@Override
	public Minion summon() {
		Minion bloodsailRaider = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new CopyWeaponAttack(), TargetSelection.SELF);
		battlecry.setResolvedLate(true);
		bloodsailRaider.setBattlecry(battlecry);
		return bloodsailRaider;
	}
	
	

}
