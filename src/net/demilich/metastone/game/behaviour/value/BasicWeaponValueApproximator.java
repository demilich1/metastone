package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.weapons.Weapon;

public class BasicWeaponValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		PlayCardAction playCardAction = (PlayCardAction) action;
		WeaponCard weaponCard = (WeaponCard) context.resolveCardReference(playCardAction.getCardReference());
		Weapon weapon = weaponCard.getWeapon();
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		float value = weapon.getWeaponDamage() * weapon.getDurability();
		for (Minion minion : opponent.getMinions()) {
			if (Divination.couldKillWithWeapon(context, minion, weapon.getWeaponDamage())) {
				value += Values.getMinionValue(minion);
			}
		}
		return value;
	}

}
