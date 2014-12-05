package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IValueProvider;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Lightbomb extends SpellCard {

	public Lightbomb() {
		super("Lightbomb", Rarity.EPIC, HeroClass.PRIEST, 6);
		setDescription("Deal damage to each minion equal to its Attack.");

		SpellDesc lightbombSpell = DamageSpell.create(new LightbombDamageModifier());
		lightbombSpell.setTarget(EntityReference.ALL_MINIONS);
		setSpell(lightbombSpell);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 559;
	}



	private class LightbombDamageModifier implements IValueProvider {

		@Override
		public int provideValue(GameContext context, Player player, Entity target) {
			Actor actor = (Actor) target;
			return actor.getAttack();
		}

	}
}
