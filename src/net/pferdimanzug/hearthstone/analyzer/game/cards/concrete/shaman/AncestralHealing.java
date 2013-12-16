package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class AncestralHealing extends SpellCard {

	public AncestralHealing() {
		super("Ancestral Healing", Rarity.FREE, HeroClass.SHAMAN, 0);
		setEffectHint(EffectHint.POSITIVE);
		setTargetRequirement(TargetRequirement.MINIONS);
		setSpell(new AncestralHealingSpell());
	}

	private class AncestralHealingSpell implements ISpell {

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			int missingHp = target.getMaxHp() - target.getHp();
			context.getLogic().heal(target, missingHp);
			target.setTag(GameTag.TAUNT);
		}

	}

}
