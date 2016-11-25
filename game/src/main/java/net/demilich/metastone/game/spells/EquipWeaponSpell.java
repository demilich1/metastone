package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class EquipWeaponSpell extends Spell {

	public static SpellDesc create(String weaponCardName) {
		Map<SpellArg, Object> arguments = SpellDesc.build(EquipWeaponSpell.class);
		arguments.put(SpellArg.CARD, weaponCardName);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		// if (targetPlayer != null) {
		// arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		// }
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(TargetPlayer targetPlayer, WeaponCard weaponCard) {
		Map<SpellArg, Object> arguments = SpellDesc.build(EquipWeaponSpell.class);
		arguments.put(SpellArg.CARD, weaponCard);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		if (targetPlayer != null) {
			arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		}
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(WeaponCard weaponCard) {
		return create(TargetPlayer.SELF, weaponCard);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		String weaponCardName = (String) desc.get(SpellArg.CARD);
		WeaponCard weaponCard = (WeaponCard) context.getCardById(weaponCardName);
		Weapon weapon = weaponCard.getWeapon();
		context.getLogic().equipWeapon(player.getId(), weapon);
	}

}
