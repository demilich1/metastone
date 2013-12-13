package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class Succubus extends MinionCard {

	public Succubus() {
		super("Succubus", Rarity.FREE, HeroClass.WARLOCK, 2);
	}

	@Override
	public Minion summon() {
		Minion succubus = createMinion(4, 3, Race.DEMON);
		succubus.setTag(GameTag.BATTLECRY, new BattlecrySuccubus());
		return succubus;
	}
	
	private class BattlecrySuccubus extends Battlecry {

		@Override
		public void execute(GameContext context, Player player) {
			Card randomHandCard = player.getHand().getRandom();
			if (randomHandCard == null) {
				return;
			}
			player.getHand().remove(randomHandCard);
		}
		
	}

}
