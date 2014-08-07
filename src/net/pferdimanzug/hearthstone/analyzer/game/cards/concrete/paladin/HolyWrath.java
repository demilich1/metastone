package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HolyWrath extends SpellCard {

	public HolyWrath() {
		super("Holy Wrath", Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Draw a card and deal damage equal to its cost.");
		
		SpellDesc holyWrathSpell = DamageSpell.create((context, player, target) -> {
			Card drawnCard = context.getLogic().drawCard(player.getId());
			if (drawnCard == null) {
				return 0;
			}
			return drawnCard.getBaseManaCost();
		});
		setSpell(holyWrathSpell);
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 249;
	}
	
}
