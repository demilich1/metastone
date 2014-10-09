package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells.ExcessManaCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EitherOrSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ModifyMaxManaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
