package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

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
