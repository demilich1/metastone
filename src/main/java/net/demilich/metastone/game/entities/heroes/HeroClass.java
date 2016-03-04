package net.demilich.metastone.game.entities.heroes;

public enum HeroClass {
	ANY,
	DECK_COLLECTION,

	DRUID,
	HUNTER,
	MAGE,
	PALADIN,
	PRIEST,
	ROGUE,
	SHAMAN,
	WARLOCK,
	WARRIOR,
	OPPONENT,
	
	BOSS;

	public boolean isBaseClass() {
		HeroClass[] nonBaseClasses = {ANY, DECK_COLLECTION, OPPONENT, BOSS};
		for (int i=0; i<nonBaseClasses.length; i++) {
			if (nonBaseClasses[i] == this) {
				return false;
			}
		}
		return true;
	}
}
