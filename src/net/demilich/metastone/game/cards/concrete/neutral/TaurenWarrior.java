package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.enrage.Enrage;

public class TaurenWarrior extends MinionCard {

	public TaurenWarrior() {
		super("Tauren Warrior", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Taunt. Enrage: +3 Attack");
	}

	@Override
	public int getTypeId() {
		return 214;
	}

	@Override
	public Minion summon() {
		Minion taurenWarrior = createMinion(GameTag.TAUNT);
		taurenWarrior.setTag(GameTag.ENRAGE_SPELL, Enrage.create(3));
		return taurenWarrior;
	}
}
