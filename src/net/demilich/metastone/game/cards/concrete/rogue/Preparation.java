package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.costmodifier.OneTurnCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddCostModifierSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.CardPlayedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Preparation extends SpellCard {

	public Preparation() {
		super("Preparation", Rarity.EPIC, HeroClass.ROGUE, 0);
		setDescription("The next spell you cast this turn costs (3) less.");

		OneTurnCostModifier costModifier = new OneTurnCostModifier(CardType.SPELL, -3, new CardPlayedTrigger(TargetPlayer.SELF,
				CardType.SPELL));
		SpellDesc spellCostReduce = AddCostModifierSpell.create(EntityReference.FRIENDLY_HERO, costModifier);
		setSpell(spellCostReduce);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 301;
	}
}
