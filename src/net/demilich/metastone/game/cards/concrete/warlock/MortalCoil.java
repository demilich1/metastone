package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ConditionalEffectSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MortalCoil extends SpellCard {

	public MortalCoil() {
		super("Mortal Coil", Rarity.FREE, HeroClass.WARLOCK, 1);
		setDescription("Deal $1 damage to a minion. If that kills it, draw a card.");
		SpellDesc damage = DamageSpell.create(1);
		SpellDesc drawCard = DrawCardSpell.create();
		SpellDesc mortalCoil = ConditionalEffectSpell.create(damage, drawCard, (context, player, entity) -> {
			Actor target = (Actor) entity;
			return target.isDead();
		});
		setSpell(mortalCoil);
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 345;
	}

	
}
