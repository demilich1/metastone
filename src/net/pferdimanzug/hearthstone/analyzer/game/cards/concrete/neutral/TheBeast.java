package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;

public class TheBeast extends MinionCard {

	public TheBeast() {
		super("The Beast", 9, 7, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Deathrattle: Summon a 3/3 Finkle Einhorn for your opponent.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 215;
	}

	@Override
	public Minion summon() {
		Minion theBeast = createMinion();
		Spell deathrattle = new SummonSpell(TargetPlayer.OPPONENT, new FinkleEinhorn());
		theBeast.addDeathrattle(deathrattle);
		return theBeast;
	}



	private class FinkleEinhorn extends MinionCard {

		public FinkleEinhorn() {
			super("Finkle Einhorn", 3, 3, Rarity.LEGENDARY, HeroClass.ANY, 2);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}
}
