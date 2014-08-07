package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MultiTargetDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ForkedLightning extends SpellCard {

	public ForkedLightning() {
		super("Forked Lightning", Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Deal 2 damage to 2 random enemy minions. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);
		setSpell(MultiTargetDamageSpell.create(2, 2));
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return context.getOpponent(player).getMinions().size() >= 2;
	}

	@Override
	public int getTypeId() {
		return 321;
	}
}
