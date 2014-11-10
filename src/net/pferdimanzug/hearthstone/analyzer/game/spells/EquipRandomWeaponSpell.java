package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class EquipRandomWeaponSpell extends Spell {

	public static SpellDesc create(TargetPlayer targetPlayer) {
		SpellDesc desc = new SpellDesc(EquipRandomWeaponSpell.class);
		desc.setTargetPlayer(targetPlayer);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		switch (desc.getTargetPlayer()) {
		case BOTH:
			context.getLogic().equipWeapon(player.getId(), getRandomWeapon());
			context.getLogic().equipWeapon(opponent.getId(), getRandomWeapon());
			break;
		case OPPONENT:
			context.getLogic().equipWeapon(opponent.getId(), getRandomWeapon());
			break;
		case SELF:
			context.getLogic().equipWeapon(player.getId(), getRandomWeapon());
			break;
		default:
			break;
		}

	}

	private Weapon getRandomWeapon() {
		CardCollection allWeapons = CardCatalogue.query(CardType.WEAPON);
		WeaponCard weaponCard = (WeaponCard) allWeapons.getRandom();
		return weaponCard.getWeapon();
	}

}
