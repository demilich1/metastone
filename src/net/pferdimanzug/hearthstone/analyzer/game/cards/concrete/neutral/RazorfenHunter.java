package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class RazorfenHunter extends MinionCard {

	public RazorfenHunter() {
		super("Razorfen Hunter", 2, 3, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Battlecry: Summon a 1/1 Boar.");
	}

	@Override
	public int getTypeId() {
		return 189;
	}

	@Override
	public Minion summon() {
		Minion razorfenHunter = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(new Boar()), TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		razorfenHunter.setBattlecry(battlecry);
		return razorfenHunter;
	}



	private class Boar extends MinionCard {

		public Boar() {
			super("Boar", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
			setRace(Race.BEAST);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}
}
