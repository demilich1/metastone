package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class EquipWeaponSpell implements ISpell {
	
	private final WeaponCard weaponCard;

	public EquipWeaponSpell(WeaponCard weaponCard) {
		this.weaponCard = weaponCard;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Weapon weapon = weaponCard.getWeapon();
		player.getHero().setWeapon(weapon);
		for (SpellTrigger spellTrigger : weapon.getSpellTriggers()) {
			context.getEventManager().registerGameEventListener(spellTrigger);
		}
		
	}

}
