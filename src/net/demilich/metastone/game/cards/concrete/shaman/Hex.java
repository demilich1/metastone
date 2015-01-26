package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.TransformMinionSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Hex extends SpellCard {

	public Hex() {
		super("Hex", Rarity.FREE, HeroClass.SHAMAN, 3);
		setDescription("Transform a minion into a 0/1 Frog with Taunt.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(TransformMinionSpell.create(new Frog()));
	}
	
	@Override
	public int getTypeId() {
		return 323;
	}
	



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
}
