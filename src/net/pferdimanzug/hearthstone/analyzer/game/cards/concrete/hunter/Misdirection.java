package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.HeroTargetedByPhysicalTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Misdirection extends SecretCard {

	private class MisdirectSpell extends Spell {
		
		public MisdirectSpell() {
			setTarget(EntityReference.EVENT_TARGET);
		}
		
		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Entity attacker = (Entity) context.getEnvironment().get(Environment.ATTACKER);
			List<Entity> validTargets = context.resolveTarget(player, null, EntityReference.ALL_CHARACTERS);
			// misdirection cannot redirect to attacker
			validTargets.remove(attacker);
			// misdirection cannot redirect to original target
			validTargets.remove(target);
			
			Entity randomTarget = getRandomTarget(validTargets);
			context.getEnvironment().put(Environment.TARGET_OVERRIDE, randomTarget);
		}
	}
	
	public Misdirection() {
		super("Misdirection", Rarity.RARE, HeroClass.HUNTER, 2);
		setDescription("Secret: When a character attacks your hero, instead he attacks another random character.");
		
		setTriggerAndEffect(new HeroTargetedByPhysicalTrigger(), new MisdirectSpell());
	}



	@Override
	public int getTypeId() {
		return 40;
	}
}
