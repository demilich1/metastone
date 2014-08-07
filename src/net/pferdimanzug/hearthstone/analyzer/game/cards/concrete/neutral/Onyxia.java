package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class Onyxia extends MinionCard {

	public Onyxia() {
		super("Onyxia", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("Battlecry: Summon 1/1 Whelps until your side of the battlefield is full.");
		setRace(Race.DRAGON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 181;
	}
	
	private MinionCard[] getWhelps() {
		MinionCard[] whelps = new MinionCard[GameLogic.MAX_MINIONS];
		for (int i = 0; i < whelps.length; i++) {
			whelps[i] = new Whelp();
		}
		return whelps;
	}
	
	@Override
	public Minion summon() {
		Minion onyxia = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(getWhelps()));
		battlecry.setResolvedLate(true);
		onyxia.setBattlecry(battlecry);
		return onyxia;
	}



	private class Whelp extends MinionCard {

		public Whelp() {
			super("Whelp", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
			setRace(Race.DRAGON);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}
}
