package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class EquipWeaponSpell extends Spell {
	
	public static SpellDesc create(WeaponCard weaponCard) {
		SpellDesc desc = new SpellDesc(EquipWeaponSpell.class);
		desc.set(SpellArg.CARD, weaponCard);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		WeaponCard weaponCard = (WeaponCard) desc.get(SpellArg.CARD);
		Weapon weapon = weaponCard.getWeapon();
		context.getLogic().equipWeapon(player.getId(), weapon);
	}

}
