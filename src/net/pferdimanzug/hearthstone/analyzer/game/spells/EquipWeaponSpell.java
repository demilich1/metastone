package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class EquipWeaponSpell extends Spell {
	
	public static SpellDesc create(WeaponCard weaponCard) {
		SpellDesc desc = new SpellDesc(EquipWeaponSpell.class);
		desc.set(SpellArg.CARD, weaponCard);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		WeaponCard weaponCard = (WeaponCard) desc.get(SpellArg.CARD);
		Weapon weapon = weaponCard.getWeapon();
		context.getLogic().equipWeapon(player.getId(), weapon);
		
	}

}
