package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class EquipWeaponSpell implements ISpell {
	
	private static Logger logger = LoggerFactory.getLogger(EquipWeaponSpell.class);
	
	private final WeaponCard weaponCard;

	public EquipWeaponSpell(WeaponCard weaponCard) {
		this.weaponCard = weaponCard;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Weapon weapon = weaponCard.getWeapon();
		logger.debug("{} equips weapon {}", player.getHero(), weapon);
		player.getHero().setWeapon(weapon);
		for (SpellTrigger spellTrigger : weapon.getSpellTriggers()) {
			context.getEventManager().registerGameEventListener(spellTrigger);
		}
		
	}

}
