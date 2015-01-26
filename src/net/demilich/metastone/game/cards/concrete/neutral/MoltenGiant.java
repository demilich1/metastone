package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class MoltenGiant extends MinionCard {

	public MoltenGiant() {
		super("Molten Giant", 8, 8, Rarity.EPIC, HeroClass.ANY, 20);
		setDescription("Costs (1) less for each damage your hero has taken.");
	}

	@Override
	public int getManaCost(GameContext context, Player player) {
		int hpDiff = player.getHero().getMaxHp() - player.getHero().getHp();
		return super.getManaCost(context, player) - hpDiff;
	}

	@Override
	public int getTypeId() {
		return 168;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
