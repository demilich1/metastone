package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class WickedKnife extends WeaponCard {

	public WickedKnife() {
		super("Wicked Knife", Rarity.FREE, HeroClass.ROGUE, 1);
		setCollectible(false);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(1, 2);
	}
	
}