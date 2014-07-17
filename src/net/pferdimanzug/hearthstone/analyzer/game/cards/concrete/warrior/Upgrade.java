package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Upgrade extends SpellCard {

	private class HeavyAxe extends WeaponCard {

		public HeavyAxe() {
			super("Heavy Axe", Rarity.RARE, HeroClass.WARRIOR, 1);
			setCollectible(false);
		}

		@Override
		public Weapon getWeapon() {
			return createWeapon(1, 3);
		}

	}

	private class UpgradeSpell extends BuffWeaponSpell {

		public UpgradeSpell() {
			super(1, 1);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			if (player.getHero().getWeapon() == null) {
				EquipWeaponSpell equipWeaponSpell = new EquipWeaponSpell(new HeavyAxe());
				context.getLogic().castSpell(player.getId(), equipWeaponSpell);
			} else {
				super.onCast(context, player, target);
			}

		}
	}

	public Upgrade() {
		super("Upgrade!", Rarity.RARE, HeroClass.WARRIOR, 1);
		setDescription("If you have a weapon, give it +1/+1.  Otherwise equip a 1/3 weapon.");

		setSpell(new UpgradeSpell());
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 381;
	}
}
