package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class AuchenaiSoulpriest extends MinionCard {

	public AuchenaiSoulpriest() {
		super("Auchenai Soulpriest", 3, 5, Rarity.RARE, HeroClass.PRIEST, 4);
		setDescription("Your cards and powers that restore Health now deal damage instead.");
	}

	@Override
	public int getTypeId() {
		return 259;
	}

	@Override
	public Minion summon() {
		Minion auchenaiSoulpriest = createMinion(GameTag.INVERT_HEALING);
		return auchenaiSoulpriest;
	}
}
