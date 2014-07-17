package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Hex extends SpellCard {

	private class Frog extends MinionCard {

		public Frog() {
			super("Frog", 0, 1, Rarity.FREE, HeroClass.ANY, 0);
			setRace(Race.BEAST);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}
		
	}
	
	public Hex() {
		super("Hex", Rarity.FREE, HeroClass.SHAMAN, 3);
		setDescription("Transform a minion into a 0/1 Frog with Taunt.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(new TransformMinionSpell(new Frog()));
	}
	



	@Override
	public int getTypeId() {
		return 323;
	}
}
