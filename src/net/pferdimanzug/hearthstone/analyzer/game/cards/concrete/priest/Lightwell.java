package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Lightwell extends MinionCard {

	private class LightwellSpell extends HealingSpell {

		public LightwellSpell(int healing) {
			super(healing);
		}

		@Override
		public void cast(GameContext context, Player player, List<Entity> targets) {
			List<Entity> validTargets = new ArrayList<Entity>();
			for (Entity entity : targets) {
				Actor actor = (Actor) entity;
				if (actor.isWounded()) {
					validTargets.add(actor);
				}
			}
			
			if (validTargets.isEmpty()) {
				return;
			}
			
			super.onCast(context, player, getRandomTarget(validTargets));

		}

	}

	public Lightwell() {
		super("Lightwell", 0, 5, Rarity.RARE, HeroClass.PRIEST, 2);
		setDescription("At the start of your turn, restore 3 Health to a damaged friendly character.");
	}
	
	@Override
	public Minion summon() {
		Minion lightwell = createMinion();
		Spell healRandomSpell = new LightwellSpell(3);
		healRandomSpell.setTarget(EntityReference.FRIENDLY_CHARACTERS);
		lightwell.setSpellTrigger(new SpellTrigger(new TurnStartTrigger(), healRandomSpell));
		return lightwell;
	}

}
