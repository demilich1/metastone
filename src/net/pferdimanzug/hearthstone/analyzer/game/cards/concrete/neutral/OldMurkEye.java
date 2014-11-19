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
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalAttackBonusSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IValueProvider;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.BoardChangedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class OldMurkEye extends MinionCard {

	public OldMurkEye() {
		super("Old Murk-Eye", 2, 4, Rarity.LEGENDARY, HeroClass.ANY, 4);
		setDescription("Charge. Has +1 Attack for each other Murloc on the battlefield.");
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 180;
	}

	@Override
	public Minion summon() {
		Minion oldMurkEye = createMinion(GameTag.CHARGE);
		SpellDesc buffSpell = ConditionalAttackBonusSpell.create(new IValueProvider() {
			
			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				Actor targetActor = (Actor) target;
				List<Entity> allMinions = context.resolveTarget(player, targetActor, EntityReference.ALL_MINIONS);
				int attackBonus = 0;
				for (Entity entity : allMinions) {
					Minion minion = (Minion) entity;
					// Old Murk-Eye is a Murloc himself, but should not count towards
					// attack
					if (minion == targetActor) {
						continue;
					}
					if (minion.getRace() == Race.MURLOC) {
						attackBonus++;
					}
				}
				return attackBonus;
			}
		});
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new BoardChangedTrigger(), buffSpell);
		oldMurkEye.setSpellTrigger(trigger);
		return oldMurkEye;
	}
	
}
