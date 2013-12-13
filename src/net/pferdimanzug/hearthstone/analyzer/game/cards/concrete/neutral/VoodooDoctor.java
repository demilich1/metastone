package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySingleTargetHealing;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class VoodooDoctor extends MinionCard {

	public VoodooDoctor() {
		super("Voodoo Doctor", Rarity.FREE, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Minion voodooDoctor = createMinion(2, 1);
		voodooDoctor.setTag(GameTag.BATTLECRY, new BattlecrySingleTargetHealing(2));
		return voodooDoctor;
	}
	
	
}
