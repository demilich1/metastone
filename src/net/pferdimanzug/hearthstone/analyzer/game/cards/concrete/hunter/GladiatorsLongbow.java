package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class GladiatorsLongbow extends WeaponCard {

	public GladiatorsLongbow() {
		super("Gladiator's Longbow", Rarity.EPIC, HeroClass.HUNTER, 7);
		setDescription("Your hero is Immune while attacking.");
	}

	@Override
	public int getTypeId() {
		return 35;
	}

	@Override
	public Weapon getWeapon() {
		Weapon gladiatorsLongbow = new Weapon(this, 5, 2) {

			@Override
			public void onEquip(GameContext context, Player player) {
				player.getHero().setTag(GameTag.IMMUNE_WHILE_ATTACKING);
			}

			@Override
			public void onUnequip(GameContext context, Player player) {
				player.getHero().removeTag(GameTag.IMMUNE_WHILE_ATTACKING);
			}
		};

		return gladiatorsLongbow;
	}
}
