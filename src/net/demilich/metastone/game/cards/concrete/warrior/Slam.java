package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ConditionalEffectSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Slam extends SpellCard {

	public Slam() {
		super("Slam", Rarity.COMMON, HeroClass.WARRIOR, 2);
		setDescription("Deal $2 damage to a minion. If it survives, draw a card.");
		
		SpellDesc damage = DamageSpell.create(2);
		SpellDesc drawCard = DrawCardSpell.create();
		drawCard.setTarget(EntityReference.NONE);
		SpellDesc slam = ConditionalEffectSpell.create(damage, drawCard, (context, player, entity) -> {
			Actor target = (Actor) entity;
			return !target.isDead();
		});
		setSpell(slam);
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 380;
	}
	
}
