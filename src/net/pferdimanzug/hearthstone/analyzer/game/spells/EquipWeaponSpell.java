package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class EquipWeaponSpell extends Spell {

	public EquipWeaponSpell(WeaponCard weaponCard) {
		setCloneableData(weaponCard);
	}

	public WeaponCard getWeaponCard() {
		return (WeaponCard) getCloneableData()[0];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Weapon weapon = getWeaponCard().getWeapon();
		context.getLogic().equipWeapon(player.getId(), weapon);
		
	}

}
