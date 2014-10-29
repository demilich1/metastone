package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class BasicWeaponValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		PlayCardAction playCardAction = (PlayCardAction) action;
		WeaponCard weaponCard = (WeaponCard) context.resolveCardReference(playCardAction.getCardReference());
		Weapon weapon = weaponCard.getWeapon();
		return weapon.getAttack() * weapon.getDurability() - weaponCard.getBaseManaCost();
	}

}
