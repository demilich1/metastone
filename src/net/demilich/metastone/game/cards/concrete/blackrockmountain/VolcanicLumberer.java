package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SpellUtils;

public class VolcanicLumberer extends MinionCard {

	public VolcanicLumberer() {
		super("Volcanic Lumberer", 7, 8, Rarity.RARE, HeroClass.DRUID, 9);
		setDescription("Taunt. Costs (1) less for each minion that died this turn.");
	}
	
	@Override
	public int getManaCost(GameContext context, Player player) {
		return super.getManaCost(context, player) - SpellUtils.howManyMinionsDiedThisTurn(context);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}



	@Override
	public int getTypeId() {
		return 647;
	}
}
