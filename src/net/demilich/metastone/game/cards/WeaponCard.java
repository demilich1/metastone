package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;

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
		return new PlayCardAction(getCardReference()) {
			
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
