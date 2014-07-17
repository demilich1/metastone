package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class GadgetzanAuctioneer extends MinionCard {

	public GadgetzanAuctioneer() {
		super("Gadgetzan Auctioneer", 4, 4, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Whenever you cast a spell, draw a card.");
	}

	@Override
	public int getTypeId() {
		return 132;
	}



	@Override
	public Minion summon() {
		Minion gadgetzanAuctioneer = createMinion();
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), new DrawCardSpell());
		gadgetzanAuctioneer.setSpellTrigger(trigger);
		return gadgetzanAuctioneer;
	}
}
