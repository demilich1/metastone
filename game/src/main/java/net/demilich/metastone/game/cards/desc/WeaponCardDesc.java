package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;

public class WeaponCardDesc extends ActorCardDesc {

	public int damage;
	public int durability;
	public SpellDesc onEquip;
	public SpellDesc onUnequip;
	public CardCostModifierDesc cardCostModifier;

	@Override
	public Card createInstance() {
		return new WeaponCard(this);
	}

}
