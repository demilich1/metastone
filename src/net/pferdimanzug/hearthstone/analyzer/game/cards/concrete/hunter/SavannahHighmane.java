package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class SavannahHighmane extends MinionCard {

	public SavannahHighmane() {
		super("Savannah Highmane", 6, 5, Rarity.RARE, HeroClass.HUNTER, 6);
		setDescription("Deathrattle: Summon two 2/2 Hyenas.");
		setTag(GameTag.RACE, Race.BEAST);
	}

	@Override
	public Minion summon() {
		Minion savannahHighmane = createMinion();
		savannahHighmane.addDeathrattle(new SummonSpell(new Hyena(), new Hyena()));
		return savannahHighmane;
	}
	
	private class Hyena extends MinionCard {

		public Hyena() {
			super("Hyena", 2, 2, Rarity.RARE, HeroClass.HUNTER, 2);
			setCollectible(false);
			setTag(GameTag.RACE, Race.BEAST);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}

}
