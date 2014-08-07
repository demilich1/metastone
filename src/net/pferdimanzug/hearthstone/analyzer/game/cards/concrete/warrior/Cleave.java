package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MultiTargetDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Cleave extends SpellCard {

	public Cleave() {
		super("Cleave", Rarity.FREE, HeroClass.WARRIOR, 2);
		setDescription("Deal $2 damage to two random enemy minions.");
		setSpell(MultiTargetDamageSpell.create(2, 2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return context.getOpponent(player).getMinions().size() >= 2;
	}
	
	



	@Override
	public int getTypeId() {
		return 365;
	}
}
