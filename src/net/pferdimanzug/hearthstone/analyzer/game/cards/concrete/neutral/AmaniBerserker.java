package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class AmaniBerserker extends MinionCard {
	
	public static final int BASE_ATTACK = 2;
	public static final int ENRAGE_ATTACK_BONUS = 3;

	public AmaniBerserker() {
		super("Amani Berserker", Rarity.COMMON, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion amaniBerserker = createMinion(BASE_ATTACK, 3);
		amaniBerserker.setTag(GameTag.ENRAGE_SPELL, new Enrage(ENRAGE_ATTACK_BONUS));
		return amaniBerserker;
	}

}
