package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.ChooseBattlecryCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.druid.Treant;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
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
	protected BattlecryAction getBattlecry1() {
		SpellDesc buffSpell = BuffSpell.create(EntityReference.FRIENDLY_MINIONS, 2, 2);
		return BattlecryAction.createBattlecry(buffSpell);
	}

	@Override
	protected BattlecryAction getBattlecry2() {
		SpellDesc summonSpell1 = SummonSpell.create(RelativeToSource.LEFT, new Treant(GameTag.TAUNT));
		SpellDesc summonSpell2 = SummonSpell.create(RelativeToSource.RIGHT, new Treant(GameTag.TAUNT));
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MetaSpell.create(summonSpell1, summonSpell2));
		battlecry.setResolvedLate(true);
		return battlecry;
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
