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
		case MULTIPLY:
			return value1 * value2;
		case DIVIDE:
			if (value2 != 0) {
				return value1 / value2;
			}
			return value1;
		case SET:
			return value1 == value2 ? 1 : 0;
		case NEGATE:
			return -value1;
		case MODULO:
			if (value2 != 0) {
				return value1 % value2;
			}
			return value1;
		default:
			break;
		}

		throw new RuntimeException("Invalid AlgebraicOperation: " + operation);
	}

	public AlgebraicValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target, Entity source) {
		int value1 = desc.getValue(ValueProviderArg.VALUE_1, context, player, target, null, 1);
		int value2 = desc.getValue(ValueProviderArg.VALUE_2, context, player, target, null, 1);
		AlgebraicOperation operation = (AlgebraicOperation) desc.get(ValueProviderArg.OPERATION);
		return evaluateOperation(value1, value2, operation);
	}

}
