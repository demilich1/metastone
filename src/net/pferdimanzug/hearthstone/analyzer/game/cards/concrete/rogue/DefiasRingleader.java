package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.NullSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DefiasRingleader extends MinionCard {

	private class DefiasBandit extends MinionCard {

		public DefiasBandit() {
			super("Defias Bandit", 2, 1, Rarity.FREE, HeroClass.ROGUE, 2);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

	public DefiasRingleader() {
		super("Defias Ringleader", 2, 2, Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("Combo: Summon a 2/1 Defias Bandit.");
	}

	@Override
	public Minion summon() {
		Minion defiasRingleader = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new ComboSpell(new NullSpell(), new SummonSpell(new DefiasBandit())),
				TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		defiasRingleader.setBattlecry(battlecry);
		return defiasRingleader;
	}

}
