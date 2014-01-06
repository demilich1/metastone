package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWeaponSpell;

public class DeadlyPoison extends SpellCard {

	public DeadlyPoison() {
		super("Deadly Poison", Rarity.FREE, HeroClass.ROGUE, 1);
		setSpell(new BuffWeaponSpell(2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return player.getHero().getWeapon() != null;
	}
	
	

}
