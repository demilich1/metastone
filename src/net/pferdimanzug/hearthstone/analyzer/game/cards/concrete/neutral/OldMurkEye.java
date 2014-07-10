package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.BoardChangedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class OldMurkEye extends MinionCard {

	private class OldMurkEyeSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Actor targetActor = (Actor) target;
			targetActor.setTag(GameTag.ATTACK_BONUS, 0);
			List<Entity> allMinions = context.resolveTarget(player, targetActor, EntityReference.ALL_MINIONS);
			int attackBonus = 0;
			for (Entity entity : allMinions) {
				Minion minion = (Minion) entity;
				// Old Murk-Eye is a Murloc himself, but should not count towards attack
				if (minion == targetActor) {
					continue;
				}
				if (minion.getRace() == Race.MURLOC) {
					attackBonus++;
				}
			}
			targetActor.modifyTag(GameTag.ATTACK_BONUS, attackBonus);
		}

	}

	public OldMurkEye() {
		super("Old Murk-Eye", 2, 4, Rarity.LEGENDARY, HeroClass.ANY, 4);
		setDescription("Charge. Has +1 Attack for each other Murloc on the battlefield.");
		setRace(Race.MURLOC);
	}

	@Override
	public Minion summon() {
		Minion oldMurkEye = createMinion(GameTag.CHARGE);
		Spell buffSpell = new OldMurkEyeSpell();
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new BoardChangedTrigger(), buffSpell);
		oldMurkEye.setSpellTrigger(trigger);
		return oldMurkEye;
	}

}
