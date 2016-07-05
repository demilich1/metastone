package net.demilich.metastone.game.spells.desc.valueprovider;

public enum AlgebraicOperation {

	ADD,
	SUBTRACT,
	MULTIPLY,
	DIVIDE,
	SET,
	NEGATE,
	MODULO,
	MINIMUM,
	MAXIMUM,
	;

	public int performOperation(int num1, int num2) {
		switch (this) {
		case ADD:
			return num1 + num2;
		case DIVIDE:
			if (num2 == 0) {
				num2 = 1;
			}
			return num1 / num2;
		case MAXIMUM:
			return num2 < num1 ? num2 : num1;
		case MINIMUM:
			return num2 > num1 ? num2 : num1;
		case MODULO:
			if (num2 == 0) {
				num2 = 1;
			}
			return num1 % num2;
		case MULTIPLY:
			return num1 * num2;
		case NEGATE:
			return -num1;
		case SET:
			return num2;
		case SUBTRACT:
			return num1 - num2;
		default:
			return num1;
		}
	}
}
