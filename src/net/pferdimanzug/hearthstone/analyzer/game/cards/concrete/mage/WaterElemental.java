package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WaterElemental extends MinionCard {

	public WaterElemental() {
		super("Water Elemental", Rarity.FREE, HeroClass.MAGE, 4);
	}

	@Override
	public Minion summon() {
		return new WaterElementalMinion(this);
	}

	private class WaterElementalMinion extends Minion {

		public WaterElementalMinion(MinionCard sourceCard) {
			super(sourceCard);
			setBaseStats(3, 6);
		}

		@Override
		public void onAttack(Entity target) {
			target.setTag(GameTag.FROZEN);
		}

	}

}
