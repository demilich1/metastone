package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid.Treant;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
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
		SpellDesc buffSpell = BuffSpell.create(2, 2);
		buffSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		return Battlecry.createBattlecry(buffSpell);
	}

	@Override
	protected Battlecry getBattlecry2() {
		SpellDesc summonSpell = SummonSpell.create(new Treant(GameTag.TAUNT), new Treant(GameTag.TAUNT));
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
}
