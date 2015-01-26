package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.heroes.powers.ArmorUp;

public class ArmorUpValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		return ArmorUp.ARMOR_BONUS;
	}

}
