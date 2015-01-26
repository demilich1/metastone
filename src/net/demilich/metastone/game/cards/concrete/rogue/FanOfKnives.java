package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FanOfKnives extends SpellCard {

	public FanOfKnives() {
		super("Fan of Knives", Rarity.FREE, HeroClass.ROGUE, 3);
		setDescription("Deal $1 damage to all enemy minions. Draw a card.");
		setTargetRequirement(TargetSelection.NONE);
		SpellDesc damageSpell = DamageSpell.create(1);
		damageSpell.setTarget(EntityReference.ENEMY_MINIONS);
		SpellDesc drawCardSpell = DrawCardSpell.create();
		drawCardSpell.setTarget(EntityReference.NONE);
		setSpell(MetaSpell.create(damageSpell, drawCardSpell));
	}

	@Override
	public int getTypeId() {
		return 295;
	}
}
