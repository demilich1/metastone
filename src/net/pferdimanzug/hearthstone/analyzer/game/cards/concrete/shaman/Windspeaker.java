package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.WindfurySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Windspeaker extends MinionCard {

	public Windspeaker() {
		super("Windspeaker", 3, 3, Rarity.FREE, HeroClass.SHAMAN, 4);
		setDescription("Battlecry: Give a friendly minion Windfury.");
	}

	@Override
	public Minion summon() {
		Minion windspeaker = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new WindfurySpell(), TargetSelection.FRIENDLY_MINIONS);
		windspeaker.setBattlecry(battlecry);
		return windspeaker;
	}

}
