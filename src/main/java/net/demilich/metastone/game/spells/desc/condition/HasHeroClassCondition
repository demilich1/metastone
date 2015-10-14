package net.demilich.metastone.game.spells.desc.condition;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class HasHeroClassCondition extends Condition {

	public HasHeroClassCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
    HeroClass heroClass = player.getHero().getHeroClass();
    String className = (String) heroClass == null ? "ANY" : desc.get(ConditionArg.HERO_CLASS);
    return heroClass.toString().equalsIgnoreCase(className);
	}

}
