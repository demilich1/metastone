package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.MindGamesSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
