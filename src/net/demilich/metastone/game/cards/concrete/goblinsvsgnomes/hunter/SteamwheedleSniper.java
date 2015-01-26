package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class SteamwheedleSniper extends MinionCard {

	public SteamwheedleSniper() {
		super("Steamwheedle Sniper", 2, 3, Rarity.EPIC, HeroClass.HUNTER, 2);
		setDescription("Your Hero Power can target minions.");
	}

	@Override
	public int getTypeId() {
		return 491;
	}



	@Override
	public Minion summon() {
		Minion steamwheedleSniper = createMinion(GameTag.HERO_POWER_CAN_TARGET_MINIONS);
		return steamwheedleSniper;
	}
}
