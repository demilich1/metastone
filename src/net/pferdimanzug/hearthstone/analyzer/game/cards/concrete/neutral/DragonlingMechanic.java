package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DragonlingMechanic extends MinionCard {

	private class MechanicalDragonling extends MinionCard {

		public MechanicalDragonling() {
			super("Mechanical Dragonling", Rarity.FREE, HeroClass.ANY, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(2, 1);
		}
		
	}

	public DragonlingMechanic() {
		super("Dragonling Mechanic", Rarity.FREE, HeroClass.ANY, 4);
	}
	
	@Override
	public Minion summon() {
		Minion dragonlingMechanic = createMinion(2, 4);
		Battlecry battlecry = Battlecry.createBattlecry(new SummonSpell(new MechanicalDragonling()), TargetSelection.NONE);
		dragonlingMechanic.setTag(GameTag.BATTLECRY, battlecry);
		return dragonlingMechanic;
	}

}
