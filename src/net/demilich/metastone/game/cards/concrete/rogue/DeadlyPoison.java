package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffWeaponSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DeadlyPoison extends SpellCard {

	public DeadlyPoison() {
		super("Deadly Poison", Rarity.FREE, HeroClass.ROGUE, 1);
		setDescription("Give your weapon +2 Attack.");
		setSpell(BuffWeaponSpell.create(2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return player.getHero().getWeapon() != null;
	}
	
	@Override
	public int getTypeId() {
		return 291;
	}
}
