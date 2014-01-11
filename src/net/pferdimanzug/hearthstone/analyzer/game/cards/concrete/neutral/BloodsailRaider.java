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
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class BloodsailRaider extends MinionCard {

	private class CopyWeaponAttack implements ISpell {
		
		private Entity self;

		public CopyWeaponAttack(Entity self) {
			this.self = self;
		}
		
		@Override
		public void cast(GameContext context, Player player, Entity target) {
			Weapon weapon = player.getHero().getWeapon();
			if (weapon != null) {
				self.modifyTag(GameTag.ATTACK_BONUS, +weapon.getWeaponDamage());
			}
		}
		
		
	}

	public BloodsailRaider() {
		super("Bloodsail Raider", Rarity.COMMON, HeroClass.ANY, 2);
	}
	
	@Override
	public Minion summon() {
		Minion bloodsailRaider = createMinion(2, 3, Race.PIRATE);
		Battlecry battlecry = Battlecry.createBattlecry(new CopyWeaponAttack(bloodsailRaider));
		bloodsailRaider.setTag(GameTag.BATTLECRY, battlecry);
		return bloodsailRaider;
	}
	
	

}
