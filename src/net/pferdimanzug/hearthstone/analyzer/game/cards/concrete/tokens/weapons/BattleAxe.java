package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class BattleAxe extends WeaponCard {

	public BattleAxe() {
		super("Battle Axe", Rarity.FREE, HeroClass.WARRIOR, 1);
		setCollectible(false);
	}

	@Override
	public Weapon getWeapon() {
		return createWeapon(2, 2);
	}
	
}