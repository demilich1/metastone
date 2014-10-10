package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.rogue.DefiasBandit;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

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
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(new DefiasBandit()));
		battlecry.setCondition((context, player) -> player.getHero().hasStatus(GameTag.COMBO));
		battlecry.setResolvedLate(true);
		defiasRingleader.setBattlecry(battlecry);
		return defiasRingleader;
	}
}
