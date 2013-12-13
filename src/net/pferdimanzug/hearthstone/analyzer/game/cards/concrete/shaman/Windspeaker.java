package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class Windspeaker extends MinionCard {

	public Windspeaker() {
		super("Windspeaker", Rarity.FREE, HeroClass.SHAMAN, 4);
	}

	@Override
	public Minion summon() {
		Minion windspeaker = createMinion(3, 3);
		Battlecry battlecry = Battlecry.createBattlecry(new BuffSpell(GameTag.WINDFURY), TargetRequirement.FRIENDLY_MINIONS);
		windspeaker.setTag(GameTag.BATTLECRY, battlecry);
		return windspeaker;
	}

}
