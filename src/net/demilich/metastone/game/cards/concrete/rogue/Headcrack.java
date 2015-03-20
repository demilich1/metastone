package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ConditionalEffectSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.ReceiveCardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Headcrack extends SpellCard {

	public Headcrack() {
		super("Headcrack", Rarity.RARE, HeroClass.ROGUE, 2);
		setDescription("Deal $2 damage to the enemy hero. Combo: Return this to your hand next turn.");

		SpellDesc noComboSpell = DamageSpell.create(EntityReference.ENEMY_HERO, 2);
		SpellDesc comboSpell = ReceiveCardSpell.create(this);
		SpellDesc headCrack = ConditionalEffectSpell.create(noComboSpell, comboSpell, (context, player, target) -> player.getHero().hasStatus(GameTag.COMBO));
		setSpell(headCrack);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 296;
	}
}
