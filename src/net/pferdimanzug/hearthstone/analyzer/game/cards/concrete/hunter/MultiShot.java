package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MultiTargetDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MultiShot extends SpellCard {

	public MultiShot() {
		super("Multi-Shot", Rarity.FREE, HeroClass.HUNTER, 4);
		setDescription("Deal $3 damage to two random enemy minions.");
		setSpell(new MultiTargetDamageSpell(3, 2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (!super.canBeCast(context, player)) {
			return false;
		}
		return context.getOpponent(player).getMinions().size() >= 2;
	}

}
