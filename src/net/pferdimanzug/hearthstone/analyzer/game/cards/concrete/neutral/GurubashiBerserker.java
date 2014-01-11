package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.DamageReceivedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class GurubashiBerserker extends MinionCard {
	
	private class GurubashiBerserkerTrigger extends DamageReceivedTrigger {

		private final Entity target;

		public GurubashiBerserkerTrigger(Entity target) {
			this.target = target;
		}

		@Override
		public Entity getTarget(GameContext context, Entity host) {
			return target;
		}		
		
	}
	public static final int BASE_ATTACK = 2;

	public static final int ATTACK_BONUS = 3;

	public GurubashiBerserker() {
		super("Gurubashi Berserker", Rarity.FREE, HeroClass.ANY, 5);
	}
	
	@Override
	public Minion summon() {
		Minion gurubashiBerserker = createMinion(BASE_ATTACK, 7);
		SpellTrigger trigger = new SpellTrigger(new GurubashiBerserkerTrigger(gurubashiBerserker), new BuffSpell(3, 0));
		gurubashiBerserker.addSpellTrigger(trigger);
		return gurubashiBerserker;
	}
	

}
