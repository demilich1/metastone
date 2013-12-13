package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySummonMinion;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class MurlocTidehunter extends MinionCard {

	public MurlocTidehunter() {
		super("Murloc Tidehunter", Rarity.FREE, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion murlocTidehunter = createMinion(2, 1, Race.MURLOC);
		murlocTidehunter.setTag(GameTag.BATTLECRY, new BattlecrySummonMinion(new MurlocScout()));
		return murlocTidehunter;
	}
	
	private class MurlocScout extends MinionCard {

		public MurlocScout() {
			super("Murloc Scout", Rarity.FREE, HeroClass.ANY, 0);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1, Race.MURLOC);
		}
		
	}

}
