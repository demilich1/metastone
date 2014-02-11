package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class TaurenWarrior extends MinionCard {

	public TaurenWarrior() {
		super("Tauren Warrior", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Taunt. Enrage: +3 Attack");
	}

	@Override
	public Minion summon() {
		Minion taurenWarrior = createMinion(GameTag.TAUNT);
		taurenWarrior.setTag(GameTag.ENRAGE_SPELL, new Enrage(3));
		return taurenWarrior;
	}

}
