package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Windspeaker extends MinionCard {

	public Windspeaker() {
		super("Windspeaker", 3, 3, Rarity.FREE, HeroClass.SHAMAN, 4);
		setDescription("Battlecry: Give a friendly minion Windfury.");
	}

	@Override
	public Minion summon() {
		Minion windspeaker = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new ApplyTagSpell(GameTag.WINDFURY), TargetSelection.FRIENDLY_MINIONS);
		windspeaker.setTag(GameTag.BATTLECRY, battlecry);
		return windspeaker;
	}

}
