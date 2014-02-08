package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public abstract class WeaponCard extends Card {

	public WeaponCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.WEAPON, rarity, classRestriction, manaCost);
	}

	protected Weapon createWeapon(int weaponDamage, int durability) {
		return new Weapon(this, weaponDamage, durability);
	}

	public abstract Weapon getWeapon();
	
	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			
			{
				setActionType(ActionType.EQUIP_WEAPON);
			}
			
			@Override
			protected void play(GameContext context, int playerId) {
				context.getLogic().equipWeapon(playerId, getWeapon());
			}
		};
	}

}
