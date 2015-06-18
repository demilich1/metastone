package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Lightbomb extends SpellCard {

	public Lightbomb() {
		super("Lightbomb", Rarity.EPIC, HeroClass.PRIEST, 6);
		setDescription("Deal damage to each minion equal to its Attack.");

		SpellDesc lightbombSpell = DamageSpell.create(EntityReference.ALL_MINIONS, null);
		setSpell(lightbombSpell);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 559;
	}

//	private class LightbombDamageModifier extends ValueProvider {
//
//		@Override
//		public int provideValue(GameContext context, Player player, Entity target) {
//			Actor actor = (Actor) target;
//			return actor.getAttack();
//		}
//
//	}
}
