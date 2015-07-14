package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SpellUtils;

public class VolcanicDrake extends MinionCard {

	public VolcanicDrake() {
		super("Volcanic Drake", 6, 4, Rarity.COMMON, HeroClass.ANY, 6);
		setDescription("Costs (1) less for each minion that died this turn.");
		setRace(Race.DRAGON);
	}
	
	@Override
	public int getManaCost(GameContext context, Player player) {
		return super.getManaCost(context, player) - SpellUtils.howManyMinionsDiedThisTurn(context);
	}

	@Override
	public int getTypeId() {
		return 646;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
