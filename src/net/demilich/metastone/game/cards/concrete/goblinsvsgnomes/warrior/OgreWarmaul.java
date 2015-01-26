package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warrior;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class OgreWarmaul extends WeaponCard {

	public OgreWarmaul() {
		super("Ogre Warmaul", Rarity.COMMON, HeroClass.WARRIOR, 3);
		setDescription("50% chance to attack the wrong enemy.");
	}

	@Override
	public int getTypeId() {
		return 606;
	}

	@Override
	public Weapon getWeapon() {
		Weapon ogreWarmaul = new Weapon(this, 4, 2) {
			@Override
			public void onEquip(GameContext context, Player player) {
				player.getHero().setTag(GameTag.FUMBLE);
			}

			@Override
			public void onUnequip(GameContext context, Player player) {
				player.getHero().removeTag(GameTag.FUMBLE);
			}
		};
		return ogreWarmaul;
	}
}
