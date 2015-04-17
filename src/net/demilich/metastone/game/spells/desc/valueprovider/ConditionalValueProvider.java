package net.demilich.metastone.game.spells.desc.valueprovider;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.condition.Condition;

public class ConditionalValueProvider extends ValueProvider {

	public ConditionalValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target) {
		int ifTrue = desc.getInt(ValueProviderArg.IF_TRUE);
		int ifFalse = desc.getInt(ValueProviderArg.IF_FALSE);

		Condition condition = (Condition) desc.get(ValueProviderArg.CONDITION);
		return condition.isFulfilled(context, player) ? ifTrue : ifFalse;
	}

}
