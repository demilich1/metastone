package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DeadlyPoison extends SpellCard {

	public DeadlyPoison() {
		super("Deadly Poison", Rarity.FREE, HeroClass.ROGUE, 1);
		setDescription("Give your weapon +2 Attack.");
		setSpell(new BuffWeaponSpell(2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return player.getHero().getWeapon() != null;
	}
	
	

}
