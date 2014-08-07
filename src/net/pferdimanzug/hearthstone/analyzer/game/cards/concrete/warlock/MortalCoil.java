package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalEffectSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
