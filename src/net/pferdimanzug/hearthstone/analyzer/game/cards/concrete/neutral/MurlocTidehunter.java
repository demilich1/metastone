package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MurlocTidehunter extends MinionCard {

	public MurlocTidehunter() {
		super("Murloc Tidehunter", 2, 1, Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Battlecry: Summon a 1/1 Murloc Scout.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 172;
	}

	@Override
	public Minion summon() {
		Minion murlocTidehunter = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(new MurlocScout()), TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		murlocTidehunter.setBattlecry(battlecry);
		return murlocTidehunter;
	}



	private class MurlocScout extends MinionCard {

		public MurlocScout() {
			super("Murloc Scout", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}
}
