package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SteamwheedleSniper extends MinionCard {

	public SteamwheedleSniper() {
		super("Steamwheedle Sniper", 2, 3, Rarity.EPIC, HeroClass.HUNTER, 2);
		setDescription("Your Hero Power can target minions.");
	}

	@Override
	public Minion summon() {
		Minion steamwheedleSniper = createMinion(GameTag.HERO_POWER_CAN_TARGET_MINIONS);
		return steamwheedleSniper;
	}

}
