package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Assassinate extends SpellCard {

	public Assassinate() {
		super("Assassinate", Rarity.FREE, HeroClass.ROGUE, 5);
		setDescription("Destroy an enemy minion.");
		setSpell(DestroySpell.create());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}
	


	@Override
	public int getTypeId() {
		return 284;
	}
}
