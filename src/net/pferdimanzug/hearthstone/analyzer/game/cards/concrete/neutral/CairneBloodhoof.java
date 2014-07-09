package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class CairneBloodhoof extends MinionCard {

	public CairneBloodhoof() {
		super("Cairne Bloodhoof", 4, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Deathrattle: Summon a 4/5 Baine Bloodhoof.");
	}

	@Override
	public Minion summon() {
		Minion cairneBloodhoof = createMinion();
		cairneBloodhoof.addDeathrattle(new SummonSpell(new BaineBloodhoof()));
		return cairneBloodhoof;
	}

	private class BaineBloodhoof extends MinionCard {

		public BaineBloodhoof() {
			super("Baine Bloodhoof", 4, 5, Rarity.LEGENDARY, HeroClass.ANY, 4);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

}
