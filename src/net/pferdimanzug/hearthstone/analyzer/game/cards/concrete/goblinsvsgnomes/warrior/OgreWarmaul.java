package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

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
