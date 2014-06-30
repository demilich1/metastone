package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BladeFlurry extends SpellCard {

	public BladeFlurry() {
		super("Blade Flurry", Rarity.RARE, HeroClass.ROGUE, 2);
		setDescription("Destroy your weapon and deal its damage to all enemies.");

		setSpell(new BladeFlurrySpell());
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (!super.canBeCast(context, player)) {
			return false;
		}
		return player.getHero().getWeapon() != null;
	}

	private class BladeFlurrySpell extends DamageSpell {

		public BladeFlurrySpell() {
			super(0);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Weapon weapon = player.getHero().getWeapon();
			if (weapon == null) {
				return;
			}

			setDamage(weapon.getWeaponDamage());
			super.onCast(context, player, target);
		}

	}

}
