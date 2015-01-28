package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.PutRandomMinionOnBoardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AncestorsCall extends SpellCard {

	public AncestorsCall() {
		super("Ancestor's Call", Rarity.EPIC, HeroClass.SHAMAN, 4);
		setDescription("Put a random minion from each player's hand into the battlefield.");
		
		setSpell(PutRandomMinionOnBoardSpell.create(TargetPlayer.BOTH, null, CardLocation.HAND));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 575;
	}
}
