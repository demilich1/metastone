package net.demilich.metastone.game.cards.concrete.tokens.spells;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class YseraAwakens extends SpellCard {

	public YseraAwakens() {
		super("Ysera Awakens", Rarity.FREE, HeroClass.ANY, 2);
		setDescription("Deal 5 damage to all characters except Ysera.");

		SpellDesc yseraAwakens = DamageSpell.create(EntityReference.ALL_CHARACTERS,
				(context, player, target) -> target.getTag(GameTag.UNIQUE_ENTITY) == UniqueEntity.YSERA ? 0 : 5);
		setSpell(yseraAwakens);
		setTargetRequirement(TargetSelection.NONE);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 469;
	}
}
