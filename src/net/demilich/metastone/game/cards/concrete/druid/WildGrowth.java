package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.spells.ExcessManaCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.EitherOrSpell;
import net.demilich.metastone.game.spells.ModifyMaxManaSpell;
import net.demilich.metastone.game.spells.ReceiveCardSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class WildGrowth extends SpellCard {

	public WildGrowth() {
		super("Wild Growth", Rarity.FREE, HeroClass.DRUID, 2);
		setDescription("Gain an empty Mana Crystal.");
		SpellDesc gainMana = ModifyMaxManaSpell.create();
		SpellDesc receiveCard = ReceiveCardSpell.create(new ExcessManaCard());
		SpellDesc wildGrowth = EitherOrSpell.create(gainMana, receiveCard, (context, player, target) -> player.getMaxMana() < GameLogic.MAX_MANA);
		setSpell(wildGrowth);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 24;
	}
	
}
