package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class EquipRandomWeaponSpell extends Spell {

	public static SpellDesc create(TargetPlayer targetPlayer) {
		SpellDesc desc = new SpellDesc(EquipRandomWeaponSpell.class);
		desc.setTargetPlayer(targetPlayer);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		CardCollection allWeapons = CardCatalogue.query(CardType.WEAPON);
		WeaponCard weaponCard = (WeaponCard) allWeapons.getRandom();
		Weapon weapon = weaponCard.getWeapon();
		weapon.setBattlecry(null);
		
		context.getLogic().equipWeapon(player.getId(), weapon);
	}

}
