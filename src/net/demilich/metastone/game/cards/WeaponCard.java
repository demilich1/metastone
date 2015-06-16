package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayWeaponCardAction;
import net.demilich.metastone.game.cards.desc.WeaponCardDesc;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;

public class WeaponCard extends Card {
	
	private final WeaponCardDesc desc;

	public WeaponCard(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.WEAPON, rarity, classRestriction, manaCost);
		this.desc = null;
	}
	
	public WeaponCard(WeaponCardDesc desc) {
		super(desc);
		this.desc = desc;
	}

	protected Weapon createWeapon(int weaponDamage, int durability) {
		return new Weapon(this, weaponDamage, durability);
	}

	public Weapon getWeapon() {
		Weapon weapon = new Weapon(this, desc.damage, desc.durability);
		// assign battlecry if there is one specified
		BattlecryDesc battlecry = desc.battlecry;
		if (battlecry != null) {
			BattlecryAction battlecryAction = BattlecryAction.createBattlecry(battlecry.spell, battlecry.getTargetSelection());
			battlecryAction.setResolvedLate(battlecry.resolvedLate);
			if (battlecry.condition != null) {
				battlecryAction.setCondition(battlecry.condition.create());
			}

			weapon.setBattlecry(battlecryAction);
		}

		if (desc.deathrattle != null) {
			weapon.addDeathrattle(desc.deathrattle);
		}
		if (desc.trigger != null) {
			weapon.setSpellTrigger(desc.trigger.create());
		}
		weapon.setOnEquip(desc.onEquip);
		weapon.setOnUnequip(desc.onUnequip);
		return weapon;
	}

	@Override
	public PlayCardAction play() {
		return new PlayWeaponCardAction(getCardReference());
	}

}
