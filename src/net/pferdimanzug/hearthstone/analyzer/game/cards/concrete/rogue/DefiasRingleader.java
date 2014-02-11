package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DefiasRingleader extends MinionCard {

	public DefiasRingleader() {
		super("DefiasRingleader", 2, 2, Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("Combo: Summon a 2/1 Defias Bandit.");
	}

	@Override
	public Minion summon() {
		Battlecry battlecry = Battlecry.createBattlecry(new ComboSpell(null, new SummonSpell(new DefiasBandit())), TargetSelection.NONE);
		Minion defiasRingleader = createMinion();
		defiasRingleader.setTag(GameTag.BATTLECRY, battlecry);
		return defiasRingleader;
	}
	
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

}
