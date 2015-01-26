package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

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
