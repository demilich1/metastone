package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalEffectSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
