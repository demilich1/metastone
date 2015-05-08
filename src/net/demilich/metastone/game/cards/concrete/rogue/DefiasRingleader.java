package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.rogue.DefiasBandit;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.SummonSpell;

public class DefiasRingleader extends MinionCard {

	public DefiasRingleader() {
		super("Defias Ringleader", 2, 2, Rarity.COMMON, HeroClass.ROGUE, 2);
		setDescription("Combo: Summon a 2/1 Defias Bandit.");
	}

	@Override
	public int getTypeId() {
		return 292;
	}

	@Override
	public Minion summon() {
		Minion defiasRingleader = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(SummonSpell.create(RelativeToSource.RIGHT, new DefiasBandit()));
		//battlecry.setCondition((context, player) -> player.getHero().hasStatus(GameTag.COMBO));
		battlecry.setResolvedLate(true);
		defiasRingleader.setBattlecry(battlecry);
		return defiasRingleader;
	}
}
