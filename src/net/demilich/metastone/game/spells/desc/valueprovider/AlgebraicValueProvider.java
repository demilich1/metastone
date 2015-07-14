package net.demilich.metastone.game.spells.desc.valueprovider;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;

public class AlgebraicValueProvider extends ValueProvider {

	private static int evaluateOperation(int value1, int value2, AlgebraicOperation operation) {
		switch (operation) {
		case ADD:
			return value1 + value2;
		case SUBTRACT:
			return value1 - value2;
		default:
			break;
		}
		
		throw new RuntimeException("Invalid AlgebraicOperation: " + operation);
	}

	public AlgebraicValueProvider(ValueProviderDesc desc) {
		super(desc);
	}
	
	@Override
	protected int provideValue(GameContext context, Player player, Entity target) {
		ValueProvider valueProvider1 = (ValueProvider) desc.get(ValueProviderArg.VALUE_PROVIDER_1);
		ValueProvider valueProvider2 = (ValueProvider) desc.get(ValueProviderArg.VALUE_PROVIDER_2);
		int value1 = valueProvider1.getValue(context, player, target);
		int value2 = valueProvider2.getValue(context, player, target);
		AlgebraicOperation operation = (AlgebraicOperation) desc.get(ValueProviderArg.OPERATION);
		return evaluateOperation(value1, value2, operation);
	}

}
