package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySingleTargetDamage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class IronforgeRifleman extends MinionCard {

	public IronforgeRifleman() {
		super("Ironforge Rifleman", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion ironforgeRifleman = createMinion(2, 2);
		ironforgeRifleman.setTag(GameTag.BATTLECRY, new BattlecrySingleTargetDamage(1));
		return ironforgeRifleman;
	}

}
