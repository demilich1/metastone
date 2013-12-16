package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;

public class Hex extends SpellCard {

	public Hex() {
		super("Hex", Rarity.FREE, HeroClass.SHAMAN, 3);
		setTargetRequirement(TargetRequirement.MINIONS);
		setSpell(new TransformMinionSpell(new Frog()));
		
	}
	
	private class Frog extends MinionCard {

		public Frog() {
			super("Frog", Rarity.FREE, HeroClass.ANY, 0);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(0, 1, Race.BEAST, GameTag.TAUNT);
		}
		
	}
	
	

}
