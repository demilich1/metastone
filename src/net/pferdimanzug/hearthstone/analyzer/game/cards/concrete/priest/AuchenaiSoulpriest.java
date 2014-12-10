package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
