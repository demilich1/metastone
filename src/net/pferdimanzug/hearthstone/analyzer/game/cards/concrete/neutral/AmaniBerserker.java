package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class AmaniBerserker extends MinionCard {
	
	public static final int BASE_ATTACK = 2;
	public static final int ENRAGE_ATTACK_BONUS = 3;

	public AmaniBerserker() {
		super("Amani Berserker", BASE_ATTACK, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Enrage: +3 Attack");
	}

	@Override
	public Minion summon() {
		Minion amaniBerserker = createMinion();
		amaniBerserker.setTag(GameTag.ENRAGE_SPELL, new Enrage(ENRAGE_ATTACK_BONUS));
		return amaniBerserker;
	}

}
