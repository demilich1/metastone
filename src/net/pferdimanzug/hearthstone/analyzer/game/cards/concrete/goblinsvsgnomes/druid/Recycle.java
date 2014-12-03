package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.RecycleSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Recycle extends SpellCard {

	public Recycle() {
		super("Recycle", Rarity.RARE, HeroClass.DRUID, 6);
		setDescription("Shuffle an enemy minion into your opponent's deck.");
		
		setSpell(RecycleSpell.create());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

}
