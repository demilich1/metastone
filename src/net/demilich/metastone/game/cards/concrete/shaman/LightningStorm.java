package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.MinMaxDamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class LightningStorm extends SpellCard {

	public LightningStorm() {
		super("Lightning Storm", Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("Deal 2-3 damage to all enemy minions. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);

		SpellDesc lightningStorm = MinMaxDamageSpell.create(2, 3);
		lightningStorm.setTarget(EntityReference.ENEMY_MINIONS);
		setSpell(lightningStorm);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 326;
	}
}
