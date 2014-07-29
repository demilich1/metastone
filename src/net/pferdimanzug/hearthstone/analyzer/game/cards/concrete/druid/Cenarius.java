package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Cenarius extends ChooseBattlecryCard {

	public Cenarius() {
		super("Cenarius", 5, 8, Rarity.LEGENDARY, HeroClass.DRUID, 9);
		setDescription("Choose One - Give your other minions +2/+2; or Summon two 2/2 Treants with Taunt.");
	}

	@Override
	protected String getAction1Suffix() {
		return "friendly minions +2/2";
	}

	@Override
	protected String getAction2Suffix() {
		return "summon 2 Treants";
	}

	@Override
	protected Battlecry getBattlecry1() {
		Spell buffSpell = new BuffSpell(2, 2);
		buffSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		return Battlecry.createBattlecry(buffSpell);
	}

	@Override
	protected Battlecry getBattlecry2() {
		Spell summonSpell = new SummonSpell(new Treant(), new Treant());
		return Battlecry.createBattlecry(summonSpell);
	}

	@Override
	public int getTypeId() {
		return 4;
	}
	
	@Override
	public Minion summon() {
		return createMinion();
	}



	private class Treant extends MinionCard {

		public Treant() {
			super("Treant", 2, 2, Rarity.FREE, HeroClass.DRUID, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}
		
	}
}
