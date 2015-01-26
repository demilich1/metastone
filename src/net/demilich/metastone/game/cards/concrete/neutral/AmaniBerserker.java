package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.enrage.Enrage;

public class AmaniBerserker extends MinionCard {

	public static final int BASE_ATTACK = 2;
	public static final int ENRAGE_ATTACK_BONUS = 3;

	public AmaniBerserker() {
		super("Amani Berserker", BASE_ATTACK, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Enrage: +3 Attack");
	}

	@Override
	public int getTypeId() {
		return 82;
	}

	@Override
	public Minion summon() {
		Minion amaniBerserker = createMinion();
		amaniBerserker.setTag(GameTag.ENRAGE_SPELL, Enrage.create(ENRAGE_ATTACK_BONUS));
		return amaniBerserker;
	}
}
