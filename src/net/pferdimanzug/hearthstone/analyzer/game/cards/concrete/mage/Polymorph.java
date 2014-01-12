package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Polymorph extends SpellCard {

	private class Sheep extends MinionCard {

		public Sheep() {
			super("Sheep", Rarity.FREE, HeroClass.ANY, 0);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1, Race.BEAST);
		}
		
	}
	
	public Polymorph() {
		super("Polymorph", Rarity.FREE, HeroClass.MAGE, 4);
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(new TransformMinionSpell(new Sheep()));
	}

}
