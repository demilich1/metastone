package net.demilich.metastone.game.cards;

public enum CardType {
	HERO,
	MINION,
	SPELL,
	WEAPON,
	HERO_POWER,
	CHOOSE_ONE;
	
	public boolean isCardType(CardType cardType) {
		if (this == CHOOSE_ONE && cardType == SPELL) {
			return true;
		} else if (this == cardType) {
			return true;
		}
		return false;
	}
}
