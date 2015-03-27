package net.demilich.metastone.game.cards.desc;

class ParseUtils {
	
	public static String toCamelCase(String input) {
		String inputLowerCase = input.toLowerCase();
		StringBuilder sb = new StringBuilder();
		final char delim = '_';
		char value;
		boolean capitalize = false;
		for (int i = 0; i < inputLowerCase.length(); ++i) {
			value = inputLowerCase.charAt(i);
			if (value == delim) {
				capitalize = true;
			} else if (capitalize) {
				sb.append(Character.toUpperCase(value));
				capitalize = false;
			} else {
				sb.append(value);
			}
		}

		return sb.toString();
	}

}
