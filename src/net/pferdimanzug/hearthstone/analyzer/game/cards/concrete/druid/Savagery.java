package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Savagery extends SpellCard {

	public Savagery() {
		super("Savagery", Rarity.RARE, HeroClass.DRUID, 1);
		setDescription("Deal damage equal to your hero's Attack to a minion.");
		
		setSpell(new DamageSpell((context, player, target) -> player.getHero().getAttack()));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (!super.canBeCast(context, player)) {
			return false;
		}
		
		return player.getHero().getAttack() > 0;
	}
	
	



	@Override
	public int getTypeId() {
		return 19;
	}
}
