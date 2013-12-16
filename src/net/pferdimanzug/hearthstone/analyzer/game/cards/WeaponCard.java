package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public abstract class WeaponCard extends Card {

	public WeaponCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.WEAPON, rarity, classRestriction, manaCost);
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			
			@Override
			protected void cast(GameContext context, Player player) {
				player.getHero().setWeapon(getWeapon());
			}
		};
	}

	public abstract Weapon getWeapon();
	
	protected Weapon createWeapon(int weaponDamage, int durability) {
		return new Weapon(this, weaponDamage, durability);
	}

}
