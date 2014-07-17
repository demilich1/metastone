package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
