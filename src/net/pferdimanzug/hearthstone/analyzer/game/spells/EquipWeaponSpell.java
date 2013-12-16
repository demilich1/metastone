package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class EquipWeaponSpell implements ISpell {
	
	private final WeaponCard weapon;

	public EquipWeaponSpell(WeaponCard weapon) {
		this.weapon = weapon;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		player.getHero().setWeapon(weapon.getWeapon());
	}

}
