package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellSource;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ConeOfCold extends SpellCard {

	public ConeOfCold() {
		super("Cone of Cold", Rarity.COMMON, HeroClass.MAGE, 4);
		setDescription("Freeze a minion and the minions next to it, and deal $1 damage to them.");
		setSpell(new ConeOfColdSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 56;
	}

	private class ConeOfColdSpell extends Spell {

		public ConeOfColdSpell() {
			Spell damage = new DamageSpell(1);
			damage.setSource(SpellSource.SPELL_CARD);
			Spell freeze = new ApplyTagSpell(GameTag.FROZEN, new TurnStartTrigger());
			freeze.setSource(SpellSource.SPELL_CARD);
			setCloneableData(damage, freeze);
		}

		public Spell getDamage() {
			return (Spell) getCloneableData()[0];
		}

		public Spell getFreeze() {
			return (Spell) getCloneableData()[1];
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			List<Entity> affected = context.getAdjacentMinions(player, target.getReference());
			affected.add((Actor) target);

			for (Entity minion : affected) {
				getDamage().setTarget(minion.getReference());
				context.getLogic().castSpell(player.getId(), getDamage());
				getFreeze().setTarget(minion.getReference());
				context.getLogic().castSpell(player.getId(), getFreeze());
			}
		}

	}
}
