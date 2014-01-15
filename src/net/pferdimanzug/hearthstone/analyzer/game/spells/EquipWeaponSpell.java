package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class EquipWeaponSpell extends Spell {
	
	private final WeaponCard weaponCard;

	public EquipWeaponSpell(WeaponCard weaponCard) {
		this.weaponCard = weaponCard;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Weapon weapon = weaponCard.getWeapon();
		context.getLogic().equipWeapon(player.getId(), weapon);
		
	}

}
