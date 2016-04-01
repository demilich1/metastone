package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class EquipRandomWeaponSpell extends Spell {

	public static SpellDesc create(TargetPlayer targetPlayer) {
		Map<SpellArg, Object> arguments = SpellDesc.build(EquipRandomWeaponSpell.class);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCollection allWeapons = CardCatalogue.query(context.getDeckFormat(), CardType.WEAPON);
		WeaponCard weaponCard = (WeaponCard) allWeapons.getRandom();
		Weapon weapon = weaponCard.getWeapon();
		weapon.setBattlecry(null);

		context.getLogic().equipWeapon(player.getId(), weapon);
	}

}
