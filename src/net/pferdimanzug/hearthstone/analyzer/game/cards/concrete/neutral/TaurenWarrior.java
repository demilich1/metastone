package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class TaurenWarrior extends MinionCard {

	public TaurenWarrior() {
		super("Tauren Warrior", Rarity.COMMON, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion taurenWarrior = createMinion(2, 3, GameTag.TAUNT);
		taurenWarrior.setTag(GameTag.ENRAGE_SPELL, new Enrage(3));
		return taurenWarrior;
	}

}
