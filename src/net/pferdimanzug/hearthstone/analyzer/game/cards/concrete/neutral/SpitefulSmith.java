package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.EnrageBuffWeapon;

public class SpitefulSmith extends MinionCard {

	public SpitefulSmith() {
		super("Spiteful Smith", 4, 6, Rarity.COMMON, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion spitefulSmith = createMinion();
		spitefulSmith.setTag(GameTag.ENRAGE_SPELL, new EnrageBuffWeapon(2));
		return spitefulSmith;
	}

}
