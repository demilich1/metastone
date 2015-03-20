package net.demilich.metastone.game.cards.concrete.neutral;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ConditionalAttackBonusSpell;
import net.demilich.metastone.game.spells.IValueProvider;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.BoardChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc buffSpell = ConditionalAttackBonusSpell.create(EntityReference.SELF, new IValueProvider() {
			
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
		SpellTrigger trigger = new SpellTrigger(new BoardChangedTrigger(), buffSpell);
		oldMurkEye.setSpellTrigger(trigger);
		return oldMurkEye;
	}
	
}
