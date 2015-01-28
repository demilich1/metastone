package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.ChooseBattlecryCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.druid.Treant;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Cenarius extends ChooseBattlecryCard {

	public Cenarius() {
		super("Cenarius", 5, 8, Rarity.LEGENDARY, HeroClass.DRUID, 9);
		setDescription("Choose One - Give your other minions +2/+2; or Summon two 2/2 Treants with Taunt.");
	}

	@Override
	protected String getAction1Suffix() {
		return "+2/2";
	}

	@Override
	protected String getAction2Suffix() {
		return "2 Treants";
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
