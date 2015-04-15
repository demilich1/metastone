package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HolyWrath extends SpellCard {

	public HolyWrath() {
		super("Holy Wrath", Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Draw a card and deal damage equal to its cost.");
		
//		SpellDesc holyWrathSpell = DamageSpell.create((context, player, target) -> {
//			Card drawnCard = context.getLogic().drawCard(player.getId());
//			if (drawnCard == null) {
//				return 0;
//			}
//			return drawnCard.getBaseManaCost();
//		});
		//setSpell(holyWrathSpell);
		setSpell(null);
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 249;
	}
	
}
