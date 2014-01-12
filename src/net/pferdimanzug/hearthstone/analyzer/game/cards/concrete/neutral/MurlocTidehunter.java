package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MurlocTidehunter extends MinionCard {

	private class MurlocScout extends MinionCard {

		public MurlocScout() {
			super("Murloc Scout", Rarity.FREE, HeroClass.ANY, 0);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1, Race.MURLOC);
		}

	}

	public MurlocTidehunter() {
		super("Murloc Tidehunter", Rarity.FREE, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion murlocTidehunter = createMinion(2, 1, Race.MURLOC);
		Battlecry battlecry = Battlecry.createBattlecry(new SummonSpell(new MurlocScout()), TargetSelection.NONE);
		murlocTidehunter.setTag(GameTag.BATTLECRY, battlecry);
		return murlocTidehunter;
	}

}
