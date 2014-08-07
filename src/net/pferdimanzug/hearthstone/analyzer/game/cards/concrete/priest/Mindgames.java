package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.MindGamesSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Mindgames extends SpellCard {

	public Mindgames() {
		super("Mindgames", Rarity.EPIC, HeroClass.PRIEST, 4);
		setDescription("Put a copy of a random minion from your opponent's deck into the battlefield.");
		
		setSpell(MindGamesSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 272;
	}
	

	
}
