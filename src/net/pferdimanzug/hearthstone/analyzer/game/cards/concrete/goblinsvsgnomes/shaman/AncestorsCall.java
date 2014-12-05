package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.PutRandomMinionOnBoardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AncestorsCall extends SpellCard {

	public AncestorsCall() {
		super("Ancestor's Call", Rarity.EPIC, HeroClass.SHAMAN, 4);
		setDescription("Put a random minion from each player's hand into the battlefield.");
		
		setSpell(PutRandomMinionOnBoardSpell.create(TargetPlayer.BOTH));
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 575;
	}
}
