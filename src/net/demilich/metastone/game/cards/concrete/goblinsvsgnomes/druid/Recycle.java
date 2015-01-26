package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.RecycleSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Recycle extends SpellCard {

	public Recycle() {
		super("Recycle", Rarity.RARE, HeroClass.DRUID, 6);
		setDescription("Shuffle an enemy minion into your opponent's deck.");
		
		setSpell(RecycleSpell.create());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}



	@Override
	public int getTypeId() {
		return 482;
	}
}
