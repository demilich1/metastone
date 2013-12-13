package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySingleTargetHealing;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class EarthenRingFarseer extends MinionCard {

	public EarthenRingFarseer() {
		super("Earthen Ring Farseer", Rarity.COMMON, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion earthenRingFarseer = createMinion(3, 3);
		earthenRingFarseer.setTag(GameTag.BATTLECRY, new BattlecrySingleTargetHealing(3));
		return earthenRingFarseer;
	}

}
