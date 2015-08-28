package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.targeting.CardReference;

public class PlayWeaponCardAction extends PlayCardAction {

	public PlayWeaponCardAction(CardReference cardReference) {
		super(cardReference);
		setActionType(ActionType.EQUIP_WEAPON);
	}

	@Override
	public void play(GameContext context, int playerId) {
		WeaponCard weaponCard = (WeaponCard) context.getEnvironment().get(Environment.PENDING_CARD);
		context.getLogic().equipWeapon(playerId, weaponCard.getWeapon());
	}

}
